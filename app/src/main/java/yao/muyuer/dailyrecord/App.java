package yao.muyuer.dailyrecord;

//import com.squareup.leakcanary.LeakCanary;

import yao.muyuer.dailyrecord.custom_attr.LinearLayoutAttr;
import yao.muyuer.dailyrecord.custom_attr.RadioButtonAttr;
import yao.muyuer.dailyrecord.custom_attr.RelativeLayoutAttr;
import yao.muyuer.dailyrecord.custom_attr.TabLayoutIndicatorAttr;
import yao.muyuer.dailyrecord.custom_attr.ViewAttr;
import yao.muyuer.skinlibrary.SkinConfig;
import yao.muyuer.skinlibrary.base.SkinBaseApplication;


/**
 * Created by MuYuer
 * Date:2016/7/5
 * Time:10:06
 */
public class App extends SkinBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(true);
        SkinConfig.addSupportAttr("tabLayoutIndicator", new TabLayoutIndicatorAttr());
        SkinConfig.addSupportAttr("button", new RadioButtonAttr());
        SkinConfig.addSupportAttr("relativeLayoutAttr", new RelativeLayoutAttr());
        SkinConfig.addSupportAttr("linearLayoutAttr", new LinearLayoutAttr());
        SkinConfig.addSupportAttr("viewAttr", new ViewAttr());
        SkinConfig.enableGlobalSkinApply();

        //LeakCanary.install(this);
    }
}
