package yao.muyuer.library.http.provider.base;

import android.content.Context;

import yao.muyuer.library.http.request.ImageRequest;

/**
 * Created by MuYuer
 * Date:2016/5/13
 * Time:10:25
 */
public interface IImageLoaderProvider {

    void loadImage(ImageRequest request);

    void loadImage(Context context, ImageRequest request);
}
