package yao.muyuer.dailyrecord.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import yao.muyuer.library.fragment.base.BaseFragment;
import yao.muyuer.dailyrecord.R;
import yao.muyuer.library.utils.FileUtils;
import yao.muyuer.skinlibrary.SkinLoaderListener;
import yao.muyuer.skinlibrary.loader.SkinManager;

/**
 * Created by MuYuer
 * Date:2016/4/14
 * Time:10:44
 */
public class ChangeSkinFragment extends BaseFragment {

    private static String TAG = "ChangeSkinFragment";


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_chang_skin;
    }

    @Override
    protected void setUpView() {
        $(R.id.ll_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().restoreDefaultTheme();

            }
        });
        $(R.id.ll_brown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().loadSkin("brown.skin",
                        new SkinLoaderListener() {
                            @Override
                            public void onStart() {
                                Log.i("SkinLoaderListener", "正在切换中");
                                //dialog.show();
                            }

                            @Override
                            public void onSuccess() {
                                Log.i("SkinLoaderListener", "切换成功");
                            }

                            @Override
                            public void onFailed(String errMsg) {
                                Log.i("SkinLoaderListener", "切换失败:" + errMsg);
                            }

                            @Override
                            public void onProgress(int progress) {
                                Log.i("SkinLoaderListener", "皮肤文件下载中:" + progress);

                            }
                        }

                );
            }
        });
        $(R.id.ll_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().loadSkin("black.skin",
                        new SkinLoaderListener() {
                            @Override
                            public void onStart() {
                                Log.i("SkinLoaderListener", "正在切换中");
                                //dialog.show();
                            }

                            @Override
                            public void onSuccess() {
                                Log.i("SkinLoaderListener", "切换成功");
                            }

                            @Override
                            public void onFailed(String errMsg) {
                                Log.i("SkinLoaderListener", "切换失败:" + errMsg);
                            }

                            @Override
                            public void onProgress(int progress) {
                                Log.i("SkinLoaderListener", "皮肤文件下载中:" + progress);

                            }
                        }

                );
            }
        });
    }

}
