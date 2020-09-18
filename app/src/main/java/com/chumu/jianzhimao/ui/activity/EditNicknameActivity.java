package com.chumu.jianzhimao.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chumu.jianzhimao.R;
import com.chumu.jianzhimao.ui.mvp.UserModle;
import com.example.common_base.ConstantConfig;
import com.example.common_base.base.BaseMvpActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNicknameActivity extends BaseMvpActivity<UserModle> {


    @BindView(R.id.edit_nickname)
    EditText mEditNickname;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.input_num_tv)
    Button mInputNumTv;
    private String mNicknameText;

    @Override
    protected int onCreateContentView() {
        return R.layout.activity_edit_nickname;
    }

    @Override
    public void initView() {
        mNicknameText = getIntent().getStringExtra(ConstantConfig.User.NICKNAME);
        mEditNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                mNicknameText = str;
                mInputNumTv.setText(String.format(Locale.CHINA, "%d/30", str.length()));
            }
        });
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(mNicknameText)) {
            mEditNickname.setText(mNicknameText);
        }
    }

    @Override
    public UserModle getModel() {
        return new UserModle();
    }

    @Override
    public void onError(int whichApi, Throwable e) {

    }


    @OnClick({R.id.edit_nickname, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }

    private void submit() {
        Intent intent = new Intent();
        intent.putExtra(ConstantConfig.User.NICKNAME, mNicknameText);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
