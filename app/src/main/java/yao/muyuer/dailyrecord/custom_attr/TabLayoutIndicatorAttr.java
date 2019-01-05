package yao.muyuer.dailyrecord.custom_attr;

import android.support.design.widget.TabLayout;
import android.view.View;

import yao.muyuer.skinlibrary.attr.base.SkinAttr;
import yao.muyuer.skinlibrary.utils.SkinResourcesUtils;

public class TabLayoutIndicatorAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if (view instanceof TabLayout) {
            TabLayout tl = (TabLayout) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                tl.setSelectedTabIndicatorColor(color);
            }
        }
    }
}
