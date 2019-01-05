package yao.muyuer.library.http.callback.adapter;

import yao.muyuer.library.http.callback.HttpCallBack;

/**
 * Created by MuYuer
 * Date:2016/5/14
 * Time:10:09
 */
public abstract class FileHttpCallBack extends HttpCallBack<String> {
    @Override
    public void onSuccess(String filePath) {

    }

    @Override
    public String parseData(String result) {
        return result;
    }

    public abstract void onProgress(long totalBytes, long downloadedBytes, int progress);
}
