package yao.muyuer.library.fragment;

import android.widget.TextView;

import yao.muyuer.library.R;
import yao.muyuer.library.fragment.base.BaseFragment;

/**
 * Created by MuYuer
 * Date:2016/3/30
 * Time:21:43
 */
public class StringFragment extends BaseFragment {
    private String mText;
    private TextView mTvText;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_string;
    }

    @Override
    protected void setUpView() {
        mText = getArguments().getString("text");
        mTvText = (TextView) getContentView().findViewById(R.id.tv_text);
        if (!mText.equals(""))
            mTvText.setText(mText);
        else
            mTvText.setText("暂无信息");
    }
}
