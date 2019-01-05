package yao.muyuer.library;

import yao.muyuer.library.utils.ToastUtils;
import yao.muyuer.skinlibrary.base.SkinBaseApplication;

/**
 * Created by MuYuer
 * Date:2016/3/30
 * Time:20:59
 */
public class MuYuerApplication extends SkinBaseApplication {
    private static MuYuerApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ToastUtils.init(this);
    }

    public static MuYuerApplication getInstance() {
        return mInstance;
    }
}
