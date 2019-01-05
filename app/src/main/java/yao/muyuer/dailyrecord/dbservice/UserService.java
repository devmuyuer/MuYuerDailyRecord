package yao.muyuer.dailyrecord.dbservice;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.database.DBHelper;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.dailyrecord.entity.SystemEntity;

import static yao.muyuer.library.utils.BitmapUtils.bitmapToByte;


public class UserService {

	public static final String SYSTEM_USER_ID = "UserId";
	public static final String SYSTEM_USER_NAME = "UserName";
	public static final String SYSTEM_USER_PASSWORD = "UserPassWord";
	public static final String SYSTEM_USER_LOGO = "UserLogo";

	public static final String TOTE_TITLE = "title";
	public static final String TOTE_CONTENT = "content";
	public static final String TOTE_DTIMES = "Addtimes";

	public static final String BILL_BILL_ID = "BillId";
	public static final String BILL_BOOK_ID = "BookId";
	public static final String BILL_AMOUNT_TYPE = "AmountType";
	public static final String BILL_AMOUNT_ID = "AmountId";
	public static final String BILL_AMOUNT_TILE = "AmountTile";
	public static final String BILL_ACCOUNT_ID = "AccountId";
	public static final String BILL_MONEY = "Money";
	public static final String BILL_ICON = "Icon";
	public static final String BILL_IMAGE = "Image";
	public static final String BILL_CONTENT = "Content";
	public static final String BILL_ADDTIME = "AddTime";

    public static final String ACCOUNT_ID = "AccountId";
	public static final String ACCOUNT_NAME = "AccountName";
	public static final String ACCOUNT_MEMORY = "Money";

	private String accountmoney;
	DBHelper.DatabaseHelper dbHelper;
	private Context parentContext;
	public UserService(Context context) {
		parentContext = context;
		dbHelper = new DBHelper.DatabaseHelper(context);
	}

	/**
	 * 更新bill表中数据
	 *
	 * @param id     消费记录对应的id,
	 * @param values 更新的数据
	 * @return
	 */
	public long updateSystem(ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long e = db.update(Config.DB_SYSTEM_TABLE, values, null, null);
		db.close();
		return e;

	}
	public SystemEntity findSystem() throws ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_SYSTEM_TABLE, null, null, null, null, null, null);
		SystemEntity entity = null;
		if (cursor.moveToFirst()) {
			entity = new SystemEntity();
			entity.SetUserId(cursor.getString(cursor.getColumnIndex(SYSTEM_USER_ID)));
			entity.SetUserName(cursor.getString(cursor.getColumnIndex(SYSTEM_USER_NAME)));
			entity.SetUserPassWord(cursor.getString(cursor.getColumnIndex(SYSTEM_USER_PASSWORD)));
			entity.SetUserLogo(cursor.getBlob(cursor.getColumnIndex(SYSTEM_USER_LOGO)));
		}
		return entity;
	}
	/**
	 * bill表中插入账单记录
	 *
	 * @param gve 账单记录对象（账户，消费类型，收支，消费金额，消费对应的图片索引，）
	 * @return
	 */
	public Long insertBill(GridViewEntity gve) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BILL_BOOK_ID, gve.getBookId());
		values.put(BILL_AMOUNT_TYPE, gve.getAmountType());
		values.put(BILL_AMOUNT_ID, gve.getAmountId());
		values.put(BILL_AMOUNT_TILE, gve.getAmountTile());
		values.put(BILL_ACCOUNT_ID, gve.getAccountId());
		values.put(BILL_MONEY, gve.getMoney());
		values.put(BILL_ICON, gve.getIcon());
		values.put(BILL_CONTENT, gve.getContent());
		values.put(BILL_IMAGE, gve.getImage());
		values.put(BILL_ADDTIME, new SimpleDateFormat(Config.DATA_FORMAT).format(gve.getAddTime()));
		long e = db.insert(Config.DB_BILL_TABLE, null, values);
		db.close();
		return e;
	}
	/**
	 * bill表中插入账单记录
	 *
	 * @param gve 账单记录对象（账户，消费类型，收支，消费金额，消费对应的图片索引，）
	 * @return
	 */
	public Long insertBillImg(int billId, Bitmap img) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		long e = db.delete(Config.DB_BILL_IMAGE_TABLE,  BILL_BILL_ID + " = ? ", new String[]{Integer.toString(billId)});

		byte[] imgBytes = bitmapToByte(img);
		ContentValues values = new ContentValues();
		values.put("BillId", billId);
		values.put("Image", imgBytes);
		e = db.insert(Config.DB_BILL_IMAGE_TABLE, null, values);
		db.close();
		return e;
	}



	/**
	 * 更新bill表中数据
	 *
	 * @param id     消费记录对应的id,
	 * @param values 更新的数据
	 * @return
	 */
	public long updateBill(GridViewEntity ent) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String id = Integer.toString(ent.getBillId());

		ContentValues values = new ContentValues();
		values.put(BILL_AMOUNT_TYPE, ent.getAmountType());
		values.put(BILL_AMOUNT_ID, ent.getAmountId());
		values.put(BILL_AMOUNT_TILE, ent.getAmountTile());
		values.put(BILL_ACCOUNT_ID, ent.getAccountId());
		values.put(BILL_MONEY, ent.getMoney());
		values.put(BILL_ICON, ent.getIcon());
		values.put(BILL_CONTENT, ent.getContent());
		values.put(BILL_IMAGE, ent.getImage());
		values.put(BILL_ADDTIME, new SimpleDateFormat(Config.DATA_FORMAT).format(ent.getAddTime()));

		long e = db.update(Config.DB_BILL_TABLE, values, BILL_BILL_ID + " = ? ", new String[]{id});
		db.close();
		return e;

	}
	/**
	 * 更新bill表中数据
	 *
	 * @param id     消费记录对应的id,
	 * @param values 更新的数据
	 * @return
	 */
	public long updateBillMoney(String id, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long e = db.update(Config.DB_BILL_TABLE, values, BILL_BILL_ID + " = ? ", new String[]{id});
		db.close();
		return e;

	}
	/**
	 * 删除bill表格中的数据
	 *
	 * @param id 表格信息id
	 * @return
	 */
	public Long delBill(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long e = db.delete(Config.DB_BILL_TABLE, BILL_BILL_ID + " = ?", new String[]{String.valueOf(id)});
		db.close();
		return e;

	}

	public long updateAccount(GridViewEntity gve) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Double oldmoney = findAccount(gve.getAccountId()).getMoney();
		Double newmoney = gve.getMoney();
		int amountType = gve.getAmountType();

		if (amountType == 0) {
			accountmoney = Double.toString(oldmoney - newmoney);
		} else if (amountType == 1) {
			accountmoney = Double.toString(oldmoney + newmoney);

		}
		ContentValues values = new ContentValues();
		values.put("Money", accountmoney);
		long e = db.update(Config.DB_ACCOUNT_TABLE, values, " AccountId = ? ", new String[]{Integer.toString(gve.getAccountId())});
		db.close();
		return e;

	}



	public GridViewEntity findBill(int id) throws ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, null, "BillId = ? ", new String[]{Integer.toString(id)}, null, null, null);
		GridViewEntity entity = null;
		if (cursor.moveToFirst()) {
			entity = new GridViewEntity();
			entity.setBillId(cursor.getInt(cursor.getColumnIndex(BILL_BILL_ID)));
			entity.setBookId(cursor.getInt(cursor.getColumnIndex(BILL_BOOK_ID)));
			entity.setAccountId(cursor.getInt(cursor.getColumnIndex(BILL_ACCOUNT_ID)));
			entity.setAmountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountTile(cursor.getString(cursor.getColumnIndex(BILL_AMOUNT_TILE)));
			entity.setAmountType(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_TYPE)));
			entity.setMoney(cursor.getDouble(cursor.getColumnIndex(BILL_MONEY)));
			entity.setIcon(cursor.getInt(cursor.getColumnIndex(BILL_ICON)));
			entity.setContent(cursor.getString(cursor.getColumnIndex(BILL_CONTENT)));
			entity.setImage(cursor.getBlob(cursor.getColumnIndex(BILL_IMAGE)));
			String str = cursor.getString(cursor.getColumnIndex(BILL_ADDTIME));
			SimpleDateFormat format2 = new SimpleDateFormat(Config.DATA_FORMAT);
			Date date = (Date) format2.parse(str);
			entity.setAddTime(date);
		}
		return entity;
	}

	public double findBillMoney(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"Sum(Money) hj"}, "AmountType = ? And strftime('%m', AddTime)=strftime('%m',date('now'))", new String[]{Integer.toString(amountType)}, null, null, null);
		GridViewEntity entity = null;
		double dbill = 0;
		if (cursor.moveToFirst()) {
			dbill = cursor.getDouble(cursor.getColumnIndex("hj"));
		}
		return dbill;
	}

	public double findBillAllMoney(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"Sum(Money) hj"}, "AmountType = ?", new String[]{Integer.toString(amountType)}, null, null, null);
		GridViewEntity entity = null;
		double dbill = 0;
		if (cursor.moveToFirst()) {
			dbill = cursor.getDouble(cursor.getColumnIndex("hj"));
		}
		return dbill;
	}

	public float[] findBillListAllMoney(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"strftime('%m',AddTime) Date,Sum(Money) hj"}, "AmountType = ?", new String[]{Integer.toString(amountType)}, "strftime('%m',AddTime)", null, null);
		GridViewEntity entity = null;
		float dbill = 0;
		int i = 0;
		float[] dbillList = new float[cursor.getCount()];
		while (cursor.moveToNext()) {
			dbill = cursor.getFloat(cursor.getColumnIndex("hj"));
			dbillList[i] = dbill;
			i++;
		}
		return dbillList;
	}

	public String[] findBillListAllMoneyMc(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"strftime('%Y年%m月',AddTime) Date,Sum(Money) hj"}, "AmountType = ?", new String[]{Integer.toString(amountType)}, "strftime('%m',AddTime)", null, null);
		GridViewEntity entity = null;
		String dbill = "";
		int i = 0;
		String[] dbillList = new String[cursor.getCount()];
		while (cursor.moveToNext()) {
			dbill = cursor.getString(cursor.getColumnIndex("Date"));
			dbillList[i] = dbill;
			i++;
		}
		return dbillList;
	}

	public float[] findBillListMoney(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"AmountTile,Sum(Money) hj"}, "AmountType = ? And strftime('%m', AddTime)=strftime('%m','Now')", new String[]{Integer.toString(amountType)}, "AmountTile", null, null);
		GridViewEntity entity = null;
		float dbill = 0;
		int i = 0;
		float[] dbillList = new float[cursor.getCount()];
		while (cursor.moveToNext()) {
			dbill = cursor.getFloat(cursor.getColumnIndex("hj"));
			dbillList[i] = dbill;
			i++;
		}
		return dbillList;
	}

	public String[] findBillListMoneyMc(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"AmountTile,Sum(Money) hj"}, "AmountType = ? And strftime('%m', AddTime)=strftime('%m','Now')", new String[]{Integer.toString(amountType)}, "AmountTile", null, null);
		GridViewEntity entity = null;
		String dbill = "";
		int i = 0;
		String[] dbillList = new String[cursor.getCount()];
		while (cursor.moveToNext()) {
			dbill = cursor.getString(cursor.getColumnIndex("AmountTile"));
			dbillList[i] = dbill;
			i++;
		}
		return dbillList;
	}

	public List<GridViewEntity> findAllBill() throws ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, null, null, null, null, null, "AddTime desc,BillId desc");
		List<GridViewEntity> list = new ArrayList<GridViewEntity>();
		GridViewEntity entity = null;
		while (cursor.moveToNext()) {
			entity = new GridViewEntity();
			entity.setBillId(cursor.getInt(cursor.getColumnIndex(BILL_BILL_ID)));
			entity.setBookId(cursor.getInt(cursor.getColumnIndex(BILL_BOOK_ID)));
			entity.setAccountId(cursor.getInt(cursor.getColumnIndex(BILL_ACCOUNT_ID)));
			entity.setAmountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountTile(cursor.getString(cursor.getColumnIndex(BILL_AMOUNT_TILE)));
			entity.setAmountType(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_TYPE)));
			entity.setMoney(cursor.getDouble(cursor.getColumnIndex(BILL_MONEY)));
			entity.setIcon(cursor.getInt(cursor.getColumnIndex(BILL_ICON)));
			entity.setContent(cursor.getString(cursor.getColumnIndex(BILL_CONTENT)));
			entity.setImage(cursor.getBlob(cursor.getColumnIndex(BILL_IMAGE)));
			String str = cursor.getString(cursor.getColumnIndex(BILL_ADDTIME));
			SimpleDateFormat format2 = new SimpleDateFormat(Config.DATA_FORMAT);
			Date date = (Date) format2.parse(str);
			entity.setAddTime(date);
			list.add(entity);
		}
		Bitmap bmp = BitmapFactory.decodeResource(parentContext.getResources(), R.drawable.type_big_0);
		entity = new GridViewEntity();
		entity.setAmountId(-1);
		entity.setAmountType(-1);
		entity.setMoney(-1);
		entity.setImage(bitmapToByte(bmp));
		entity.setIcon(0);
		list.add(entity);

		return list;
	}
	public GridViewEntity findBill(String id) throws ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, null, BILL_BILL_ID + "=?", new String[]{id}, null, null, null);
		GridViewEntity entity = null;
		while (cursor.moveToNext()) {
			entity = new GridViewEntity();
			entity.setBillId(cursor.getInt(cursor.getColumnIndex(BILL_BILL_ID)));
			entity.setBookId(cursor.getInt(cursor.getColumnIndex(BILL_BOOK_ID)));
			entity.setAccountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountTile(cursor.getString(cursor.getColumnIndex(BILL_AMOUNT_TILE)));
			entity.setAmountType(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_TYPE)));
			entity.setMoney(cursor.getDouble(cursor.getColumnIndex(BILL_MONEY)));
			entity.setIcon(cursor.getInt(cursor.getColumnIndex(BILL_ICON)));
			entity.setContent(cursor.getString(cursor.getColumnIndex(BILL_CONTENT)));
			entity.setImage(cursor.getBlob(cursor.getColumnIndex(BILL_IMAGE)));
			String str = cursor.getString(cursor.getColumnIndex(BILL_ADDTIME));
			SimpleDateFormat format2 = new SimpleDateFormat(Config.DATA_FORMAT);
			Date date = (Date) format2.parse(str);
			entity.setAddTime(date);
		}
		return entity;

	}
	public float[] findBillMoneyByTime(String time) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, new String[]{"Sum(Money) hj"}, "strftime('%Y-%m-%d',AddTime) =? And AmountType=0 ", new String[]{time}, null, null, null);
		float dbill = 0;
		float[]  dbillList = new float[2];
		while (cursor.moveToNext()) {
			dbillList[0] = cursor.getFloat(cursor.getColumnIndex("hj"));
		}

		cursor = db.query(Config.DB_BILL_TABLE, new String[]{"Sum(Money) hj"}, "strftime('%Y-%m-%d',AddTime) =?  And AmountType=1", new String[]{time}, null, null, null);
		dbill = 0;
		while (cursor.moveToNext()) {
			dbillList[1] = cursor.getFloat(cursor.getColumnIndex("hj"));
		}
		return dbillList;
	}
	public List<GridViewEntity> findBillByTime(String time) throws ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, null, "strftime('%Y-%m-%d',AddTime) =? ", new String[]{time}, null, null, null);
		List<GridViewEntity> list = new ArrayList<GridViewEntity>();
		GridViewEntity entity = null;
		while (cursor.moveToNext()) {
			entity = new GridViewEntity();
			entity.setBillId(cursor.getInt(cursor.getColumnIndex(BILL_BILL_ID)));
			entity.setBookId(cursor.getInt(cursor.getColumnIndex(BILL_BOOK_ID)));
			entity.setAccountId(cursor.getInt(cursor.getColumnIndex(BILL_ACCOUNT_ID)));
			entity.setAmountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountTile(cursor.getString(cursor.getColumnIndex(BILL_AMOUNT_TILE)));
			entity.setAmountType(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_TYPE)));
			entity.setMoney(cursor.getDouble(cursor.getColumnIndex(BILL_MONEY)));
			entity.setIcon(cursor.getInt(cursor.getColumnIndex(BILL_ICON)));
			entity.setContent(cursor.getString(cursor.getColumnIndex(BILL_CONTENT)));
			entity.setImage(cursor.getBlob(cursor.getColumnIndex(BILL_IMAGE)));
			String str = cursor.getString(cursor.getColumnIndex(BILL_ADDTIME));
			SimpleDateFormat format2 = new SimpleDateFormat(Config.DATA_FORMAT);
			Date date = (Date) format2.parse(str);
			entity.setAddTime(date);
			list.add(entity);
		}
		return list;

	}
	public GridViewEntity findBillByType(String amountId, String time) throws ParseException {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_BILL_TABLE, null, "AmountId=? and strftime('%Y-%m-%d',AddTime) =? ", new String[]{amountId, time}, null, null, null);
		GridViewEntity entity = null;
		while (cursor.moveToNext()) {
			entity = new GridViewEntity();
			entity.setBillId(cursor.getInt(cursor.getColumnIndex(BILL_BILL_ID)));
			entity.setBookId(cursor.getInt(cursor.getColumnIndex(BILL_BOOK_ID)));
			entity.setAccountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountId(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_ID)));
			entity.setAmountTile(cursor.getString(cursor.getColumnIndex(BILL_AMOUNT_TILE)));
			entity.setAmountType(cursor.getInt(cursor.getColumnIndex(BILL_AMOUNT_TYPE)));
			entity.setMoney(cursor.getDouble(cursor.getColumnIndex(BILL_MONEY)));
			entity.setIcon(cursor.getInt(cursor.getColumnIndex(BILL_ICON)));
			entity.setContent(cursor.getString(cursor.getColumnIndex(BILL_CONTENT)));
			entity.setImage(cursor.getBlob(cursor.getColumnIndex(BILL_IMAGE)));
			String str = cursor.getString(cursor.getColumnIndex(BILL_ADDTIME));
			SimpleDateFormat format2 = new SimpleDateFormat(Config.DATA_FORMAT);
			Date date = (Date) format2.parse(str);
			entity.setAddTime(date);
		}
		return entity;

	}



	public List<GridViewEntity> findAllAccount() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_ACCOUNT_TABLE, null, null, null, null, null, "AccountId asc");
		List<GridViewEntity> list = new ArrayList<GridViewEntity>();
		while (cursor.moveToNext()) {
			double dbmoney = cursor.getDouble(cursor.getColumnIndex(ACCOUNT_MEMORY));
			int accountId = cursor.getInt(cursor.getColumnIndex(ACCOUNT_ID));
			String accountName = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME));

			GridViewEntity entity = new GridViewEntity();
			entity.setAccountId(accountId);
			entity.setAccountName(accountName);
			entity.setMoney(dbmoney);
			list.add(entity);
		}
		return list;

	}


	public GridViewEntity findAccount(int accountId) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_ACCOUNT_TABLE, null, "accountId = ? ", new String[]{String.valueOf(accountId)}, null, null, null);
		GridViewEntity entity = null;
		if (cursor.moveToFirst()) {
			double dbmoney = cursor.getDouble(cursor.getColumnIndex(ACCOUNT_MEMORY));
			entity = new GridViewEntity();
			entity.setMoney(dbmoney);
		}
		return entity;

	}

	public Integer[] GetAmountTypeIcon(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_AMOUNT_TYPE_TABLE, new String[]{"Title,Icon"}, "AmountType = ?", new String[]{Integer.toString(amountType)},
				null, null, "DispOrder");
		int icon;
		int i = 0;
		Integer[] icons = new Integer[cursor.getCount()];
		while (cursor.moveToNext()) {
			icon = cursor.getInt(cursor.getColumnIndex("Icon"));
			icons[i] = icon;
			i++;
		}
		return icons;
	}
	public String[] GetAmountTypeTitle(int amountType) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(Config.DB_AMOUNT_TYPE_TABLE, new String[]{"Title,Icon"}, "AmountType = ?", new String[]{Integer.toString(amountType)}, null, null, null);
		String title;
		int i = 0;
		String[] titles = new String[cursor.getCount()];
		while (cursor.moveToNext()) {
			title = cursor.getString(cursor.getColumnIndex("Title"));
			titles[i] = title;
			i++;
		}
		return titles;
	}


	/**
	 * 更改note表中的数据
	 *
	 * @param id     表格信息id
	 * @param values 要更改的数据
	 * @return
	 */
	public long updateNote(String id, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long e = db.update("note", values, " id = ? ", new String[]{id});
		db.close();
		return e;

	}


}
