package com.example.common_base.design;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.example.common_base.R;


/**
 * Created by ChuMu on 2019/4/8.
 */

public class CommonTitle extends RelativeLayout {

    private final String mTitle;
    private final boolean mCanBack;
    private final int mMoreImg, mBackImage;
    private final String mMoreText;
    public ImageView mMoreImage;
    public TextView mMoreText1;
    public TextView mTvTitle;
    public ImageView mBackBtn;
    private RelativeLayout mTitleRl;
    private final int mBackColor;
    private onCommonTitleListener mListener;

    public CommonTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_title, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTitle, 0, 0);
        try {
            mTitle = ta.getString(R.styleable.CommonTitle_titleText);
            mCanBack = ta.getBoolean(R.styleable.CommonTitle_canBack, true);
            mMoreImg = ta.getResourceId(R.styleable.CommonTitle_moreImg, 0);
            mBackImage = ta.getResourceId(R.styleable.CommonTitle_backImage, R.drawable.common_base_icon_black_back);
            mMoreText = ta.getString(R.styleable.CommonTitle_moreText);
            mBackColor = ta.getColor(R.styleable.CommonTitle_backColor, ContextCompat.getColor(context, R.color.app_theme_color));
            setUpView(context);

        } finally {
            ta.recycle();
        }
    }


    private void setUpView(Context context) {
        mTitleRl = findViewById(R.id.title_rl);
        mTitleRl.setBackgroundColor(mBackColor);
        mTvTitle = findViewById(R.id.title_content);
        mTvTitle.setText(mTitle);
        mTvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onTitleClick(v);
                }
            }
        });
        mBackBtn = findViewById(R.id.back_image);
        mBackBtn.setImageResource(mBackImage);
        mBackBtn.setVisibility(mCanBack ? VISIBLE : INVISIBLE);
        if (mCanBack) {
            mBackBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).finish();
                }
            });
        }
        mMoreImage = findViewById(R.id.img_more);
        mMoreImage.setImageResource(mMoreImg);
        mMoreImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onMoreClick(v);
            }
        });
        mMoreText1 = findViewById(R.id.txt_more);
        mMoreText1.setText(mMoreText);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setMoreImg(@DrawableRes int src) {
        mMoreImage.setImageResource(src);
    }

    public void setCommonTitleListener(onCommonTitleListener listener) {

        mListener = listener;
    }

    public interface onCommonTitleListener {
        void onTitleClick(View view);

        void onMoreClick(View view);

    }
}
