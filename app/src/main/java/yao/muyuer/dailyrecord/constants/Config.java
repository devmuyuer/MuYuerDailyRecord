package yao.muyuer.dailyrecord.constants;

/**
 * 配置内信息
 *
 * @author muyuer
 *
 */
public class Config {
	public static String FoundingTime;
	public static String FirstInstallTime;
	public static final String TEMP_IMAGES_LOCATION = "/sdcard/dailyrecord/images/";// 临时图片文件

	public static final String DB_FILE_NAME="dailyrecord_db";//数据库文件名
	public static final String APP_PACKAGE_NAME="com.muyuer.dailyrecord";//程序包名

	public static final String ENCODE_TYPE = "utf-8";// 全局编码方式

	public static final String APP_UPDATE_URL = "http://www.615000.net/app/update/dailyrecord/update_app.php";

	public static final String LOCAL_PATH = "file:///android_asset/";// 本地html

	// 新浪微博api
	public static final String consumerKey = "4216444778";
	public static final String consumerSecret = "1f6960b6dfe01c1ab71c417d29b439a8";
	public static final String callBackUrl = "myapp://AboutActivity";

	public static final String AuthorWeiboUserId = "1240794802";// 自己的新浪微博用户编号
	public static final String AuthorWeiboUserName = "walkingp";// 作者的新浪微博用户昵称

	public static final String DB_ACCOUNT_BOOK_TABLE = "AccountBook";// 账本表名
	public static final String DB_AMOUNT_TYPE_TABLE = "AmountType";  // 资金类型表名
	public static final String DB_BILL_TABLE = "Bill";  //记账明细表名
	public static final String DB_BILL_IMAGE_TABLE = "BillImage";// 记账照片
	public static final String DB_ACCOUNT_TABLE = "Account";// 账户
	public static final String DB_SYSTEM_TABLE = "System";  //系统设置

	public static final String DATA_FORMAT ="yyyy-MM-dd HH:mm";
	public static final String DATA_FORMAT2 ="yyyy-MM-dd";

	public static final boolean IS_SYNCH2DB_AFTER_READ = true;// 阅读时是否同步到数据库

	public static final String URL_RSS_CATE_URL = "http://m.walkingp.com/api/xml/cnblogs_rsscate.xml";// 备选RSS文件地址
	public static final String URL_RSS_LIST_URL = "http://m.walkingp.com/api/xml/cnblogs_rss_item_{0}.xml";// 备选RSS文件地址
}
