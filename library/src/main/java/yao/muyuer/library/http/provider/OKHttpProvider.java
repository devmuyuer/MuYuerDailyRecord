package yao.muyuer.library.http.provider;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import yao.muyuer.library.http.callback.HttpCallBack;
import yao.muyuer.library.http.callback.adapter.FileHttpCallBack;
import yao.muyuer.library.http.provider.base.IHttpProvider;
import yao.muyuer.library.http.request.HttpRequest;
import yao.muyuer.library.utils.Logger;

/**
 * Created by MuYuer
 * Date:2016/5/13
 * Time:11:20
 */
public class OKHttpProvider implements IHttpProvider {

    @Override
    public void loadString(final HttpRequest request, final HttpCallBack callBack) {
        if (request.getMethod() == HttpRequest.Method.GET) {
            OkHttpUtils
                    .get()
                    .url(request.getUrl())
                    .params(request.getParams())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            callBack.onError(e);
                        }

                        @Override
                        public void onResponse(String response) {
                            callBack.onSuccess(callBack.parseData(response));
                        }
                    });
        } else {
            OkHttpUtils
                    .post()
                    .url(request.getUrl())
                    .params(request.getParams())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            callBack.onError(e);
                        }

                        @Override
                        public void onResponse(String response) {
                            callBack.onSuccess(callBack.parseData(response));
                        }
                    });
        }
    }

    @Override
    public void download(String downloadUrl, String savePath, final FileHttpCallBack callBack) {

        String saveDir = savePath.substring(0, savePath.lastIndexOf("/"));
        String fileName = savePath.substring(savePath.lastIndexOf("/") + 1);
        Logger.i(this, saveDir);
        Logger.i(this, fileName);
        OkHttpUtils
                .get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(saveDir, fileName) {

                    @Override
                    public void onError(Call call, Exception e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onResponse(File response) {
                        callBack.onSuccess(response.getAbsolutePath());
                    }

                    @Override
                    public void inProgress(float progress, long total) {
                        callBack.onProgress(total, (long) (progress * total), (int) (progress * 100));
                    }
                });
    }
}
