package yao.muyuer.library.http;

import yao.muyuer.library.http.provider.PicassoImageLoaderProvider;
import yao.muyuer.library.http.provider.base.IImageLoaderProvider;

/**
 * Created by MuYuer
 * Date:2016/5/13
 * Time:10:24
 */
public class ImageLoader {

    private static volatile IImageLoaderProvider mProvider;

    public static IImageLoaderProvider getProvider() {
        if (mProvider == null) {
            synchronized (ImageLoader.class) {
                if (mProvider == null) {
                    mProvider = new PicassoImageLoaderProvider();
                }
            }
        }
        return mProvider;
    }

}
