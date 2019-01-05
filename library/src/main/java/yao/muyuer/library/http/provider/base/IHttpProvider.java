package yao.muyuer.library.http.provider.base;

import yao.muyuer.library.http.callback.HttpCallBack;
import yao.muyuer.library.http.callback.adapter.FileHttpCallBack;
import yao.muyuer.library.http.request.HttpRequest;

/**
 * Created by MuYuer
 * Date:2016/5/13
 * Time:9:49
 */
public interface IHttpProvider {

    void loadString(HttpRequest request, HttpCallBack callBack);

    void download(String downloadUrl,String savePath, FileHttpCallBack callBack);
}
