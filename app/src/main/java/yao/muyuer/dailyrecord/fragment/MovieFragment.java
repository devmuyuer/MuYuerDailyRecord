package yao.muyuer.dailyrecord.fragment;

import android.content.Intent;
import android.view.View;

import yao.muyuer.library.fragment.base.BaseFragment;
import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.activity.Setting2Activity;

/**
 * Created by MuYuer
 * Date:2016/3/30
 * Time:11:54
 */
public class MovieFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        $(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getMContext(), Setting2Activity.class));
            }
        });
    }
}
