package yao.muyuer.dailyrecord.custom_attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import yao.muyuer.skinlibrary.attr.base.SkinAttr;
import yao.muyuer.skinlibrary.utils.SkinResourcesUtils;


/**
 * Created by MuYuer
 * Date:2017/6/26
 * Time:13:51
 * Desc:
 */

public class ViewAttr extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof View) {
            View view2 = (View) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                view2.setBackgroundColor(color);
            }
            if (isDrawable()) {
                Drawable drawable = SkinResourcesUtils.getDrawable(attrValueRefId);
                view2.setBackground(drawable);
            }

        }
    }
}
