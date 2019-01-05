package yao.muyuer.skinlibrary;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import yao.muyuer.skinlibrary.attr.base.DynamicAttr;

/**
 * Created by MuYuer
 * Date:2016/4/14
 * Time:10:26
 */
public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);

    void dynamicAddView(View view, String attrName, int attrValueResId);

    /**
     * add the textview for font switch
     *
     * @param textView textview
     */
    void dynamicAddFontView(TextView textView);
}
