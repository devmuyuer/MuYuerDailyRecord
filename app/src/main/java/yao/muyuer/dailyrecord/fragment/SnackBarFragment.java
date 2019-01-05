package yao.muyuer.dailyrecord.fragment;

import android.view.View;

import yao.muyuer.library.fragment.base.BaseFragment;
import yao.muyuer.library.utils.SnackBarUtils;
import yao.muyuer.dailyrecord.R;

/**
 * Created by MuYuer
 * Date:2016/5/9
 * Time:13:52
 */
public class SnackBarFragment extends BaseFragment {
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_snackbar;
    }

    @Override
    protected void setUpView() {
        $(R.id.btn_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.makeShort(v, "TEXT").show();
            }
        });
        $(R.id.btn_danger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.makeShort(v, "TEXT").danger();
            }
        });
        $(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.makeShort(v, "TEXT").success();
            }
        });
        $(R.id.btn_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.makeShort(v, "TEXT").info("action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackBarUtils.makeShort(v, "TEXT").show();
                    }
                });
            }
        });
        $(R.id.btn_warning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.makeShort(v, "TEXT").warning("action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackBarUtils.makeShort(v, "TEXT").show();
                    }
                });
            }
        });
    }
}
