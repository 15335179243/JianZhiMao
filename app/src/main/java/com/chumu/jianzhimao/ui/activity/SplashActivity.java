package com.chumu.jianzhimao.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.chumu.jianzhimao.R;
import com.example.common_base.SPConstant;
import com.example.common_base.base.BaseActivity;
import com.example.common_base.design.CommonTitle;

import butterknife.BindView;

/**
 * Create by SunnyDay on 2019/03/15
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_count_down)
    TextView countDownText;
    private CountDownTimer timer;

    @Override
    protected int onCreateTitleView() {
        return 0;
    }

    /**
     * 倒计时逻辑处理
     */
    private void initCountDown() {
        // 避免内存泄漏
        if (!isFinishing()) {
            timer = new CountDownTimer(1000 * 5, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    countDownText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkToJump();
                        }
                    });
                    int time = (int) millisUntilFinished;
                    countDownText.setText(time / 1000 + " 跳过");
                }

                @Override
                public void onFinish() {
                    checkToJump();
                }
            }.start();
        }
    }

    /**
     * 首次进入引导页判断
     */
    private void checkToJump() {
        boolean isFirstin = (boolean) mChuMuSharedPreferences.getValue(SPConstant.FIRST_IN, true);
        if (isFirstin) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            mChuMuSharedPreferences.putValue(SPConstant.FIRST_IN, false);
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.activity_countdown;
    }

    @Override
    public void initView() {
        initCountDown();
        setNoTitleBarAndFullScreen();
    }

    @Override
    public void initData() {

    }
}
