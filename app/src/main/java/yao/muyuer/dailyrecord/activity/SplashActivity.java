package yao.muyuer.dailyrecord.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.constants.FileAccess;
import yao.muyuer.dailyrecord.entity.AppEntity;
import yao.muyuer.library.other.WaveProgressView;
import yao.muyuer.library.utils.AppUtil;

/**
 * 闪屏
 * @author muyuer
 * @date:2011-12
 *
 */
public class SplashActivity extends Activity {
	
	private TextView txtDate;
	static Resources res;// 资源
	ProgressDialog pd;// 更新版本时进度框.
	String dataLocalUrl, dataVersion;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		res = this.getResources();
		SetViewInfo();

		//更新程序
		UpdateApp();

		//设置延迟，播放登陆界面
		new Handler().postDelayed(new Runnable(){
			public void run() {
				RedirectMainActivity();
			}
		},3000);
	}
	/**
	 * 计算两个日期型的时间相差多少时间
	 * @param startDate  开始日期
	 * @param endDate    结束日期
	 * @return
	 */
	public String twoDateDistance(Date startDate, Date endDate){

		if(startDate == null ||endDate == null){
			return null;
		}

		long timeLong = endDate.getTime() - startDate.getTime();
		timeLong = timeLong/1000/ 60 / 60 / 24;
		return timeLong + "天";
		/*
		if (timeLong<60*1000)
			return timeLong/1000 + "秒";
		else if (timeLong<60*60*1000){
			timeLong = timeLong/1000 /60;
			return timeLong + "分钟";
		}
		else if (timeLong<60*60*24*1000){
			timeLong = timeLong/60/60/1000;
			return timeLong+"小时";
		}
		else if (timeLong<60*60*24*1000*7){
			timeLong = timeLong/1000/ 60 / 60 / 24;
			return timeLong + "天";
		}
		else if (timeLong<60*60*24*1000*7*4){
			timeLong = timeLong/1000/ 60 / 60 / 24/7;
			return timeLong + "周";
		}
		else if (timeLong<60*60*24*1000*365){

			timeLong = timeLong/1000/ 60 / 60 / 24/365;
			return timeLong + "年";
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			return sdf.format(startDate);
		}*/
	}

	private void SetViewInfo(){
		txtDate = (TextView)findViewById(R.id.txtDate);
		Format format = new SimpleDateFormat("yyyy年MM月dd日");

		try {
			PackageManager packageManager = getApplicationContext().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
			//应用装时间
			long firstInstallTime = packageInfo.firstInstallTime;
			//应用最后一次更新时间
			long lastUpdateTime = packageInfo.lastUpdateTime;
			txtDate.setText(format.format(new Date(firstInstallTime)));
			Config.FirstInstallTime = format.format(new Date(firstInstallTime));
			Config.FoundingTime = AppUtil.DateToChineseString(new Date(firstInstallTime));// twoDateDistance(new Date(firstInstallTime), new Date(System.currentTimeMillis()));
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转
	 */
	private void RedirectMainActivity(){
		Intent i = new Intent();
		i.setClass(SplashActivity.this,IndexActivity.class);
		startActivity(i);
		SplashActivity.this.finish();
	}

	public void UpdateApp() {
		/*boolean isNetworkAvailable = NetHelper.networkIsAvailable(getApplicationContext());
		if (isNetworkAvailable) {
			ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cManager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				//更新程序
				GetVersionThread gvt = new GetVersionThread();
				gvt.execute();
			} else {
				Toast.makeText(SplashActivity.this,
						R.string.sys_network_error, Toast.LENGTH_SHORT).show();
			}
		}*/
	}
	public void InstallApk(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public File downLoadFile(String fileUrl, String apkPath, String location_url) {
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			int length = (int) conn.getContentLength();
			InputStream is = conn.getInputStream();
			if (length != -1) {
				pd.setMax(length);
				FileAccess.MakeDir(apkPath);// 创建文件夹
				FileOutputStream out = new FileOutputStream(location_url);
				byte[] buffer = new byte[1024];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(buffer)) != -1) {
					out.write(buffer, 0, readLen);
					destPos += readLen;
					int p = destPos * 100 / length;
					pd.setProgress((int) destPos);
					pd.incrementProgressBy(p);
				}
				out.flush();
				out.close();
			}
			is.close();
			pd.dismiss();
			return new File(res.getString(R.string.app_update_location_url));// 生成本地文件名
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.sys_error,
					Toast.LENGTH_LONG);
		}
		return null;
	}

	/**
	 * 获取服务器端程序版本号，然后和本地版本号比较，判断是否需要更新
	 */
	/*public class GetVersionThread extends AsyncTask<Void, Void, AppEntity> {
		@Override
		protected AppEntity doInBackground(Void... params) {
			String url = Config.APP_UPDATE_URL;
			try {
				String dataString = NetHelper.GetContentFromUrl(url);

				// 解析json
				if (!dataString.equals("")) {
					try {
						//List<AppEntity> list = new ArrayList<AppEntity>();

						//JSONArray jsonArray = new JSONArray(dataString);
						//for (int i = 0, len = jsonArray.length(); i < len; i++) {
							JSONObject jsonObject = new JSONObject(URLDecoder.decode(dataString,"UTF-8"));//  jsonArray.getJSONObject(i);

							String appTitle = jsonObject.getString("appTitle") ;
							String version = jsonObject.getString("version");
							String innerVersion = jsonObject
									.getString("innerVersion");
							String downLoadUrl = jsonObject
									.getString("fileLocalUrl");
							String updateRemark = jsonObject
									.getString("updateRemark");
							String summary = "";//jsonObject.getString("summary");
							String link = "";//jsonObject.getString("link");
							dataLocalUrl = jsonObject
									.getString("dataLocalUrl");
							dataVersion = jsonObject
									.getString("dataVersion");
							// String
							// feedbackUrl=jsonObject.getString("feedbackUrl");

							AppEntity entity = new AppEntity();
							entity.SetAppTitle(appTitle);
							entity.SetInnerVersion(innerVersion);
							entity.SetVersion(version);
							entity.SetFileLocalUrl(downLoadUrl);
							entity.SetUpdateRemark(updateRemark);
							entity.SetSummary(summary);
							entity.SetLink(link);
							entity.SetDataLocalUrl(dataLocalUrl);
							entity.SetDataVersion(dataVersion);
							// entity.SetFeedbackUrl(feedbackUrl);

							//list.add(entity);
						//}

						return entity;//list.get(0);
					} catch (Exception e) {
						Log.e("setting_update_parseJson", e.toString());
					}
				}
			} catch (Exception e) {
				Log.e("setting_update", e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(AppEntity entity) {
			super.onPostExecute(entity);
			if (entity == null) {
				Toast.makeText(SplashActivity.this,
						R.string.sys_network_error, 1000).show();
				return;
			}
			final String downLoadUrl = entity.GetFileLocalUrl();
			//String newVersion = entity.GetVersion();
			String dataNewVersoin = entity.GetDataVersion();
			String updateRemark = entity.GetUpdateRemark();
			String newVersion = entity.GetInnerVersion();
			String version = AppUtil.GetVersionName(SplashActivity.this);
			if (newVersion.compareTo(version) < 1)  {
				Toast.makeText(SplashActivity.this,
						res.getString(R.string.config_update_newest_version),
						1000).show();
			} else {
				String message = res
						.getString(R.string.config_update_dialog_new_version)
						.replace("{version}", newVersion);
				//Looper.prepare();
				new AlertDialog.Builder(SplashActivity.this)
						.setTitle(R.string.config_update_dialog_title)
						.setMessage(message)
						.setNegativeButton(R.string.com_btn_cancel, null)
						.setPositiveButton(R.string.com_btn_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
														int which) {

										pd = new ProgressDialog(SplashActivity.this);
										pd.setTitle("更新应用程序");
										pd.setMessage("正在下载APK,请稍候...");
										pd.setIndeterminate(false);
										pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
										pd.setMax(100);
										pd.incrementProgressBy(0);
										pd.setCancelable(false);
										pd.show();
										new Thread(new Runnable() {
											@Override
											public void run() {
												// TODO Auto-generated method stub
												InstallApk(downLoadFile(downLoadUrl, res.getString(R.string.app_apk_location_path), res.getString(R.string.app_update_location_url)));
											}
										}).start();
									}
								}).show();
			}

			//更新数据
			*//*systemDbHelper = new SystemDalHelper(LoginActivity.this);
			String dataVersion = systemDbHelper.getDBVersion();
			if (dataNewVersoin.compareTo(dataVersion) < 1)  {
				Toast.makeText(SplashActivity.this,
						res.getString(R.string.config_update_data_newest_version),
						1000).show();
			} else {
				String message = res
						.getString(R.string.config_update_dialog_new_data_version)
						.replace("{version}", dataNewVersoin);
				//Looper.prepare();
				new AlertDialog.Builder(SplashActivity.this)
						.setTitle(R.string.config_update_dialog_title)
						.setMessage(message)
						.setNegativeButton(R.string.com_btn_cancel, null)
						.setPositiveButton(R.string.com_btn_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
														int which) {

										pd = new ProgressDialog(SplashActivity.this);
										pd.setTitle("更新数据");
										pd.setMessage("正在下载数据包,请稍候...");
										pd.setIndeterminate(false);
										pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
										pd.setMax(100);
										pd.incrementProgressBy(0);
										pd.setCancelable(false);
										pd.show();
										new Thread(new Runnable() {
											@Override
											public void run() {
												String location_url = res.getString(R.string.app_db_update_location_url);
												downLoadFile(dataLocalUrl, res.getString(R.string.app_db_location_path), location_url);
												new ImportDatabase(LoginActivity.this).CopyDatabase(location_url);
											}
										}).start();
									}
								}).show();
			}*//*
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}*/

}
