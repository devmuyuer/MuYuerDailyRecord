package yao.muyuer.library.http;

import yao.muyuer.library.http.provider.OKHttpProvider;
import yao.muyuer.library.http.provider.base.IHttpProvider;

/**
 * Created by MuYuer
 * Date:2016/5/13
 * Time:11:19
 */
public class HttpHelper {

    private static volatile IHttpProvider mProvider;

    public static IHttpProvider getProvider() {
        if (mProvider == null) {
            synchronized (HttpHelper.class) {
                if (mProvider == null) {
                    mProvider = new OKHttpProvider();
                }
            }
        }
        return mProvider;
    }
}
