package yao.muyuer.dailyrecord.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.constants.Config;

public class DBHelper {

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	public final static byte[] _writeLock = new byte[0];
	// 打开数据库
	public void OpenDB(Context context) {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	// 关闭数据库
	public void Close() {
		dbHelper.close();
		if(db!=null){
			db.close();
		}
	}
	/**
	 * 插入
	 *
	 * @param list
	 * @param table
	 *            表名
	 */
	public void Insert(List<ContentValues> list, String tableName) {
		synchronized (_writeLock) {
			db.beginTransaction();
			try {
				db.delete(tableName, null, null);
				for (int i = 0, len = list.size(); i < len; i++)
					db.insert(tableName, null, list.get(i));
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
	}
	public DBHelper(Context context) {
		this.dbHelper = new DatabaseHelper(context);
	}
	/**
	 * 用于初始化数据库
	 *
	 * @author Administrator
	 *
	 */
	public static class DatabaseHelper extends SQLiteOpenHelper {
		// 定义数据库文件
		private static final String DB_NAME = Config.DB_FILE_NAME;
		// 定义数据库版本
		private static final int DB_VERSION = 1;
		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			CreateAcountBookDb(db);
			Log.i("DBHelper", "创建"+Config.DB_ACCOUNT_BOOK_TABLE+"表成功");
			CreateAmountTypeDb(db);
			Log.i("DBHelper", "创建"+Config.DB_AMOUNT_TYPE_TABLE+"表成功");
			CreateBillDb(db);
			Log.i("DBHelper", "创建"+Config.DB_BILL_TABLE+"表成功");
			CreateBillImageDb(db);
			Log.i("DBHelper", "创建"+Config.DB_BILL_IMAGE_TABLE+"表成功");
			CreateAccountDb(db);
			Log.i("DBHelper", "创建"+Config.DB_ACCOUNT_TABLE+"表成功");
			CreateSystemDb(db);
			Log.i("DBHelper", "创建"+Config.DB_SYSTEM_TABLE+"表成功");
		}
		/**
		 * 创建AccountBook表
		 *
		 * @param db
		 */
		private void CreateSystemDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Config.DB_SYSTEM_TABLE+"] (");
			sb.append("[Id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("[UserId] NVARCHAR(50) DEFAULT (''), ");
			sb.append("[UserName] NVARCHAR(50) DEFAULT (''), ");
			sb.append("[UserPassWord] NVARCHAR(200) DEFAULT (0), ");
			sb.append("[UserLogo] BLOB);");
			db.execSQL(sb.toString());

			ContentValues values=new ContentValues();
			values.put("UserName", "天天记");
			values.put("UserPassWord", "123");
			db.insert(Config.DB_SYSTEM_TABLE, null, values);
		}
		/**
		 * 创建AccountBook表
		 *
		 * @param db
		 */
		private void CreateAcountBookDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Config.DB_ACCOUNT_BOOK_TABLE+"] (");
			sb.append("[BookId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("[Title] NVARCHAR(50) NOT NULL DEFAULT (''), ");
			sb.append("[BookColor] INTEGER DEFAULT (0), "); //帐本颜色
			sb.append("[Currency] INTEGER DEFAULT (0), ");  //币种 默认人民币
			sb.append("[AddTime] DATETIME DEFAULT (date('now')) );");
			db.execSQL(sb.toString());

			ContentValues values=new ContentValues();
			values.put("Title", "默认账本");
			values.put("BookColor", 1);
			values.put("Currency", 1);
			db.insert(Config.DB_ACCOUNT_BOOK_TABLE, null, values);
		}
		/**
		 * 创建AmountType表
		 *
		 * @param db
		 */
		private void CreateAmountTypeDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Config.DB_AMOUNT_TYPE_TABLE+"] (");
			sb.append("[AmountId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("[Title] NVARCHAR(50) NOT NULL DEFAULT (''), ");
			sb.append("[AmountType] INTEGER DEFAULT (0), "); //收入 支出
			sb.append("[Icon] INTEGER DEFAULT (0), ");  //图标
			sb.append("[DispOrder] INTEGER DEFAULT (0), ");  //排序
			sb.append("[AddTime] DATETIME DEFAULT (date('now')) );");
			db.execSQL(sb.toString());

			Integer[] img1 = { R.drawable.type_big_1, R.drawable.type_big_2, R.drawable.type_big_3,
					R.drawable.type_big_4, R.drawable.type_big_5, R.drawable.type_big_6, R.drawable.type_big_7,
					R.drawable.type_big_8, R.drawable.type_big_9, R.drawable.type_big_10, R.drawable.type_big_11,
					R.drawable.type_big_12 };
			Integer[] img2 = { R.drawable.type_big_13, R.drawable.type_big_14, R.drawable.type_big_15,
					R.drawable.type_big_16, R.drawable.type_big_17, R.drawable.type_big_18, R.drawable.type_big_19 };
			String[] st1 = new String[] { "一般", "用餐", "零食", "交通", "充值", "购物", "娱乐", "住房", "约会", "网购", "日用品", "香烟" };
			String[] st2 = new String[] { "工资", "外快兼职", "奖金", "借入", "零花钱", "投资收入", "礼物红包" };
			for(int i=0;i<st1.length;i++){
				ContentValues values=new ContentValues();
				values.put("Title", st1[i]);
				values.put("AmountType",1);
				values.put("DispOrder", i + 1);
				values.put("Icon", img1[i]);
				db.insert(Config.DB_AMOUNT_TYPE_TABLE, null, values);
			}
			for(int i=0;i<st2.length;i++){
				ContentValues values=new ContentValues();
				values.put("Title", st2[i]);
				values.put("AmountType",2);
				values.put("DispOrder", i + 1);
				values.put("Icon", img2[i]);
				db.insert(Config.DB_AMOUNT_TYPE_TABLE, null, values);
			}
		}

		/**
		 * 创建Bill表
		 *
		 * @param db
		 */
		private void CreateBillDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Config.DB_BILL_TABLE+"] (");
			sb.append("[BillId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("[BookId] INTEGER NOT NULL DEFAULT (0),");       //账本
			sb.append("[AmountType] INTEGER NOT NULL DEFAULT (0), "); //收入 支出
			sb.append("[AmountId] INTEGER NOT NULL,");        //收支类别
			sb.append("[AmountTile] NVARCHAR(50) NOT NULL DEFAULT (''), ");//收支类别中文名称
			sb.append("[AccountId] INTEGER NOT NULL DEFAULT (0),");       //账户
			sb.append("[Money] NUMERIC(12,2), ");
			sb.append("[Icon] INTEGER DEFAULT (0), ");
			sb.append("[Content] NTEXT NOT NULL DEFAULT (''), ");
			sb.append("[Image] BLOB, ");
			sb.append("[AddTime] DATETIME DEFAULT (date('now')) );");
			db.execSQL(sb.toString());
		}
		/**
		 * 创建NewsList表
		 *
		 * @param db
		 */
		private void CreateBillImageDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Config.DB_BILL_IMAGE_TABLE+"] (");
			sb.append("[BillId] INTEGER NOT NULL,");
			sb.append("[Image] BLOB, ");
			sb.append("[AddTime] DATETIME DEFAULT (date('now')) );");
			db.execSQL(sb.toString());
		}

		/**
		 * 创建AmountType表
		 *
		 * @param db
		 */
		private void CreateAccountDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Config.DB_ACCOUNT_TABLE+"] (");
			sb.append("[AccountId] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb.append("[AccountName] NVARCHAR(50) NOT NULL DEFAULT (''), ");
			sb.append("[Money] NUMERIC(12,2), ");
			sb.append("[AddTime] DATETIME DEFAULT (date('now')) );");
			db.execSQL(sb.toString());

			List<String> accountname=new ArrayList<String>();
			accountname.add("现金");
			accountname.add("储蓄卡");
			accountname.add("信用卡");
			accountname.add("支付宝");
			accountname.add("微信");
			List<String> money=new ArrayList<String>();
			money.add("0");
			money.add("0");
			money.add("0");
			money.add("0");
			money.add("0");
			for(int i=0;i<accountname.size();i++){
				ContentValues values=new ContentValues();
				values.put("AccountName", accountname.get(i));
				values.put("Money", money.get(i));
				db.insert(Config.DB_ACCOUNT_TABLE, null, values);
			}
		}

		/**
		 * 更新版本时更新表
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			DropTable(db);
			onCreate(db);
			Log.e("User", "onUpgrade");
		}
		/**
		 * 删除表
		 *
		 * @param db
		 */
		private void DropTable(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE IF EXISTS " + Config.DB_ACCOUNT_BOOK_TABLE + ";");
			sb.append("DROP TABLE IF EXISTS " + Config.DB_AMOUNT_TYPE_TABLE + ";");
			sb.append("DROP TABLE IF EXISTS " + Config.DB_BILL_TABLE + ";");
			sb.append("DROP TABLE IF EXISTS " + Config.DB_BILL_IMAGE_TABLE + ";");
			sb.append("DROP TABLE IF EXISTS " + Config.DB_ACCOUNT_TABLE + ";");
			db.execSQL(sb.toString());
		}
		/**
		 * 清空数据表（仅清空无用数据）
		 * @param db
		 */
		public static void ClearData(Context context){
			DatabaseHelper dbHelper = new DBHelper.DatabaseHelper(context);
			SQLiteDatabase db=dbHelper.getWritableDatabase();
			StringBuilder sb=new StringBuilder();
			sb.append("DELETE FROM BlogList WHERE IsFull=0 AND BlogId NOT IN(SELECT ContentId FROM FavList WHERE ContentType=0);");//清空博客表
			sb.append("DELETE FROM NewsList WHERE IsFull=0;");//清空新闻表
			sb.append("DELETE FROM DB_Bill_TABLE;");//清空评论表
			sb.append("DELETE FROM DB_BILL_IMAGE_TABLE;");//清空订阅文章表
			db.execSQL(sb.toString());
		}
	}
}
