package yao.muyuer.library.http.callback.adapter;


import yao.muyuer.library.http.callback.HttpCallBack;

/**
 * Created by MuYuer
 * Date:2016/5/13
 * Time:11:43
 */
public abstract class StringHttpCallBack extends HttpCallBack<String> {
    @Override
    public String parseData(String result) {
        return result;
    }
}
