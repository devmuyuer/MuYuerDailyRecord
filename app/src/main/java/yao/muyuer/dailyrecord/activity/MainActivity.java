package yao.muyuer.dailyrecord.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.HashMap;

import yao.muyuer.library.activity.base.BaseActivity;
import yao.muyuer.library.fragment.WebViewFragment;
import yao.muyuer.library.utils.SnackBarUtils;
import yao.muyuer.library.utils.ViewUtils;
import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.fragment.AboutFragment;
import yao.muyuer.dailyrecord.fragment.BlogFragment;
import yao.muyuer.dailyrecord.fragment.ChangeSkinFragment;
import yao.muyuer.dailyrecord.fragment.CustomViewFragment;
import yao.muyuer.dailyrecord.fragment.GanHuoFragment;
import yao.muyuer.dailyrecord.fragment.MainFragment;
import yao.muyuer.dailyrecord.fragment.SnackBarFragment;
import yao.muyuer.skinlibrary.loader.SkinManager;

import static java.security.AccessController.getContext;

public class MainActivity extends BaseActivity {

    private static String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;//侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private MenuItem mPreMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            Log.i(TAG, "NULL");
        else {
            Log.i(TAG, "NOT NULL");
        }

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar);
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.navigation_view);


        mToolbar.setTitle(getString(R.string.navigation_item_home));

        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        setNavigationViewItemClickListener();
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);


        initDefaultFragment();
        dynamicAddView(mToolbar, "background", R.color.colorPrimary);
        dynamicAddView(mNavigationView.getHeaderView(0), "background", R.color.colorPrimary);
        dynamicAddView(mNavigationView, "navigationViewMenu", R.color.colorPrimary);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    //init the default checked fragment
    private void initDefaultFragment() {
        Log.i(TAG, "initDefaultFragment");
        mCurrentFragment = ViewUtils.createFragment(MainFragment.class);

        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        mPreMenuItem = mNavigationView.getMenu().getItem(0);
        mPreMenuItem.setChecked(true);
    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (null != mPreMenuItem) {
                    mPreMenuItem.setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle(getString(R.string.navigation_item_home));
                        switchFragment(MainFragment.class);
                        break;
                    case R.id.navigation_item_report:
                        mToolbar.setTitle(getString(R.string.navigation_item_report));
                        switchFragment(GanHuoFragment.class);
                        break;
                    case R.id.navigation_item_datail:
                        mToolbar.setTitle(getString(R.string.navigation_item_datail));
                        switchFragment(BlogFragment.class);
                        break;
                    case R.id.navigation_item_set:
                        mToolbar.setTitle(getString(R.string.navigation_item_set));
                        switchFragment(CustomViewFragment.class);
                        break;
                    case R.id.navigation_item_snackbar:
                        mToolbar.setTitle("Snackbar演示");
                        switchFragment(SnackBarFragment.class);
                        break;
                    case R.id.navigation_item_switch_theme:
                        mToolbar.setTitle(getString(R.string.navigation_item_skin));
                        switchFragment(ChangeSkinFragment.class);
                        break;
                    case R.id.navigation_item_switch_font:
                        mToolbar.setTitle(getString(R.string.navigation_item_skin));
                        switchFont();
                        break;
                    case R.id.navigation_item_about:
                        mToolbar.setTitle(getString(R.string.navigation_item_about));
                        switchFragment(AboutFragment.class);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mPreMenuItem = item;
                return false;
            }
        });
    }
    HashMap<String, String> map = new HashMap<>();

    private void switchFont() {
        map.put("默认", null);
        map.put("时尚细黑", "SSXHZT.ttf");
        map.put("大梁体", "DLTZT.ttf");
        map.put("微软雅黑", "WRYHZT.ttf");
        new MaterialDialog.Builder(this)
                .title("选择字体")
                .items(map.keySet())
                .itemsCallbackSingleChoice(1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        SkinManager.getInstance().loadFont(map.get(text));
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }
    //切换Fragment
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            Log.i(TAG, "Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            Log.i(TAG, "Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityWithoutExtras(Setting2Activity.class);
        } else if (id == R.id.action_about) {
            startActivityWithoutExtras(AboutActivity.class);
        }


        return super.onOptionsItemSelected(item);
    }


    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        if (mCurrentFragment instanceof WebViewFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
            if (webViewFragment.canGoBack()) {
                webViewFragment.goBack();
                return;
            }
        }

        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            SnackBarUtils.makeShort(mDrawerLayout, getString(R.string.sys_waring_quit_app)).success();
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
            System.exit(0);
        }
    }
}
