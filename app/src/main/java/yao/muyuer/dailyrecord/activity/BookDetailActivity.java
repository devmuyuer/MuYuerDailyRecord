package yao.muyuer.dailyrecord.activity;


import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Type;

import yao.muyuer.library.activity.base.BaseActivity;
import yao.muyuer.library.http.HttpClientManager;
import yao.muyuer.library.http.ImageLoader;
import yao.muyuer.library.http.callback.adapter.JsonHttpCallBack;
import yao.muyuer.library.http.request.ImageRequest;
import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.adapter.BookInfoPageAdapter;
import yao.muyuer.dailyrecord.entity.BookBean;

/**
 * Created by MuYuer
 * Date:2016/3/30
 * Time:20:16
 */
public class BookDetailActivity extends BaseActivity {

    private String mUrl;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mIvBook;

    private BookBean mBookBean;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvRating;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void setUpView() {
        //设置Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mCollapsingToolbarLayout = $(R.id.collapsing_toolbar_layout);
        mIvBook = $(R.id.iv_book_image);
        mTvTitle = $(R.id.tv_title);
        mTvMsg = $(R.id.tv_msg);
        mTvRating = $(R.id.tv_rating);
        mViewPager = $(R.id.viewpager);
        mTabLayout = $(R.id.sliding_tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("作者信息"));
        mTabLayout.addTab(mTabLayout.newTab().setText("章节"));
        mTabLayout.addTab(mTabLayout.newTab().setText("书籍简介"));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#6d4c41"));
        dynamicAddView(mTabLayout, "tabIndicatorColor", R.color.colorAccent);
        dynamicAddView(mCollapsingToolbarLayout, "contentScrimColor", R.color.colorPrimary);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void init() {
        mUrl = getIntent().getStringExtra("url");
    }

    @Override
    protected void setUpData() {

        HttpClientManager.getData(mUrl, new JsonHttpCallBack<BookBean>() {
            @Override
            public void onSuccess(BookBean result) {
                mBookBean = result;
                mCollapsingToolbarLayout.setTitle(result.getTitle());
                mTvTitle.setText(result.getTitle());
                mTvMsg.setText(result.getAuthor() + "/" + result.getPublisher() + "/" + result.getPubdate());
                mTvRating.setText(result.getRating().getAverage() + "分");

                ImageRequest imageRequest = new ImageRequest.Builder().imgView(mIvBook).url(result.getImages().getLarge()).create();
                ImageLoader.getProvider().loadImage(imageRequest);

                BookInfoPageAdapter adapter = new BookInfoPageAdapter(BookDetailActivity.this, result, getSupportFragmentManager());
                mViewPager.setAdapter(adapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public DataType getDataType() {
                return DataType.OBJECT;
            }

            @Override
            public Type getType() {
                return BookBean.class;
            }

        });
    }
}
