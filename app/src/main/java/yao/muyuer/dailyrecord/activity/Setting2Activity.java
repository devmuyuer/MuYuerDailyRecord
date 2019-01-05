package yao.muyuer.dailyrecord.activity;

import android.support.v4.app.Fragment;

import yao.muyuer.library.activity.ToolbarActivity;
import yao.muyuer.dailyrecord.fragment.AboutFragment;

public class Setting2Activity extends ToolbarActivity {


    @Override
    protected String getToolbarTitle() {
        return "设置";
    }

    @Override
    protected Fragment setFragment() {
        return new AboutFragment();
    }
}
