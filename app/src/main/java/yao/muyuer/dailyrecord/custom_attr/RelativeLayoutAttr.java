package yao.muyuer.dailyrecord.custom_attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import yao.muyuer.skinlibrary.attr.base.SkinAttr;
import yao.muyuer.skinlibrary.utils.SkinResourcesUtils;


/**
 * Created by MuYuer
 * Date:2017/6/26
 * Time:13:51
 * Desc:
 */

public class RelativeLayoutAttr extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                relativeLayout.setBackgroundColor(color);
            }
            if (isDrawable()) {
                Drawable drawable = SkinResourcesUtils.getDrawable(attrValueRefId);
                relativeLayout.setBackground(drawable);
            }

        }
    }
}
