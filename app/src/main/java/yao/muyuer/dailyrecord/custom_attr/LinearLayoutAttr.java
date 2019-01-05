package yao.muyuer.dailyrecord.custom_attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import yao.muyuer.skinlibrary.attr.base.SkinAttr;
import yao.muyuer.skinlibrary.utils.SkinResourcesUtils;


/**
 * Created by MuYuer
 * Date:2017/6/26
 * Time:13:51
 * Desc:
 */

public class LinearLayoutAttr extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                linearLayout.setBackgroundColor(color);
            }
            if (isDrawable()) {
                Drawable drawable = SkinResourcesUtils.getDrawable(attrValueRefId);
                linearLayout.setBackground(drawable);
            }

        }
    }
}
