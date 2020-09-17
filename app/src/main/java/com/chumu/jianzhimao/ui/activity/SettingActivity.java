package com.chumu.jianzhimao.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chumu.jianzhimao.R;
import com.chumu.jianzhimao.ui.mvp.UserModle;
import com.example.common_base.base.BaseMvpActivity;
import com.example.common_base.utils.GlideCacheUtil;

import butterknife.BindView;

public class SettingActivity extends BaseMvpActivity<UserModle> {


    ImageView mImgToolbarWeixin;
    @BindView(R.id.ll_about)
    LinearLayout mLlAbout;
    @BindView(R.id.ll_setpassword)
    LinearLayout mLlSetpassword;
    @BindView(R.id.tv_age)
    TextView mTvCache;
    @BindView(R.id.ll_clear_cache)
    LinearLayout mLlClearCache;
    @BindView(R.id.ll_follow_wechat)
    LinearLayout mLlFollowWechat;
    @BindView(R.id.ll_log_out)
    LinearLayout mLlLogOut;
    private GlideCacheUtil mCacheUtil;

    private String mTeacherWechat;
    private int mTeacherId;
    private String mTeacherImg;

    @Override
    protected int onCreateContentView() {
         return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setNoTitleBarAndFullScreen();
        Intent intent = getIntent();
        mTeacherWechat = intent.getStringExtra("mTeacherWechat");
        mTeacherImg = intent.getStringExtra("mTeacherImg");
        mTeacherId = intent.getIntExtra("mTeacherId", -1);
        mImgToolbarWeixin.setVisibility(View.INVISIBLE);
        getTitleView().setTitle("设置");
        mCacheUtil = GlideCacheUtil.getInstance();
        String cacheSize = mCacheUtil.getCacheSize(this);

        if (cacheSize != null) {
            mTvCache.setText(cacheSize);
        }

    }


    @Override
    public void initData() {

    }

    @Override
    public UserModle getModel() {
        return new UserModle();
    }

    @Override
    public void onError(int whichApi, Throwable e) {

    }

//    @Override
//    public void onResponse(int whichApi, Object[] t) {
//        hide();
//        switch (whichApi) {
//            default:
//                break;
//            case ApiConfig.GET_TEACHER_COPY_WECHAT:
//                ResponseBody responseBody = (ResponseBody) t[0];
//                try {
//                    JSONObject jsonObject = new JSONObject(responseBody.string());
//
//                    if (jsonObject.optInt("code") == 10000) {
//                        showToast("复制成功");
//                        super.SetmButtonaddweixinClick();
//                        hideConsultTeacher();
//                    } else {
//                        showToast(jsonObject.optJSONObject("res").optString("message"));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//        }
//    }
//
//
//    @OnClick({R.id.img_back, R.id.ll_about, R.id.ll_setpassword, R.id.ll_clear_cache, R.id.ll_follow_wechat, R.id.ll_log_out})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            default:
//                break;
//            case R.id.img_back:
//                finish();
//                break;
//            case R.id.ll_about:
//                startActivity(new Intent(this, AboutUsActivity.class));
//                break;
//            case R.id.ll_setpassword:
//                startActivity(new Intent(this, SetPasswordActivity.class));
//                break;
//            case R.id.ll_clear_cache:
//                showToast("清除缓存");
//                mCacheUtil.clearImageAllCache(SettingActivity.this);
//                mTvCache.setText("缓存大小：" + "0kb");
//                break;
//            case R.id.ll_follow_wechat:
//                if (mTeacherWechat != null && mTeacherImg != null) {
//                    showConsultTeacher(mTeacherImg, mTeacherWechat);
//                } else {
//                    showToast("未获取微信请检测网络状态");
//
//                }
//
//                break;
//            case R.id.ll_log_out:
//                SharedPrefrenceUtils.saveString(this, SpConfig.ACCESSTOKEN, null);
//                showToast("退出成功");
//
//                break;
//        }
//    }
//
//    @Override
//    public void SetmButtonaddweixinClick() {
//        show();
//        // 将文本内容放到系统剪贴板里。
//        getMClipboardManager().setPrimaryClip(ClipData.newPlainText("text", mTeacherWechat));
//        HashMap<String, String> map = new HashMap<>();
//        map.put("teacherId",String.valueOf(mTeacherId));
//        mPresenter.getData(ApiConfig.GET_TEACHER_COPY_WECHAT, mTeacherId, Encryption.formatUrlParam(map));
//
//        show();
//    }

}