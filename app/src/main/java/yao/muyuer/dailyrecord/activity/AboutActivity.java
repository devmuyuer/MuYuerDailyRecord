package yao.muyuer.dailyrecord.activity;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import yao.muyuer.library.activity.base.BaseActivity;
import yao.muyuer.dailyrecord.R;

/**
 * Created by MuYuer
 * Date:2016/5/5
 * Time:10:30
 */
public class AboutActivity extends BaseActivity {

        @Override
        protected int setLayoutResourceID() {
            return R.layout.about_layout;
        }

        @Override
        protected void setUpView() {
            TextView tv_content = $(R.id.tv_content);
            tv_content.setAutoLinkMask(Linkify.ALL);
            tv_content.setMovementMethod(LinkMovementMethod
                    .getInstance());
        }
    }
