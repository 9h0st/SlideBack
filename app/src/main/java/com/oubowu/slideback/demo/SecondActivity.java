package com.oubowu.slideback.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.SlideConfig;
import com.oubowu.slideback.callbak.OnSlideListenerAdapter;
import com.oubowu.slideback.widget.SlideBackLayout;

public class SecondActivity extends AppCompatActivity {

    private SlideBackLayout mSlideBackLayout;
    private TextView mTvEdgeRange;
    private SeekBar mSbEdgeRange;
    private TextView mTvSlideOutRange;
    private SeekBar mSbSlideOutRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mSlideBackLayout = SlideBackHelper.attach(
                // 当前Activity
                this, MyApplication.getActivityHelper(),
                // 参数的配置
                new SlideConfig.Builder()
                        // 屏幕是否旋转
                        .rotateScreen(true)
                        // 是否侧滑
                        .edgeOnly(false)
                        // 是否禁止侧滑
                        .lock(false)
                        // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                        .edgePercent(0.1f)
                        // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                        .slideOutPercent(0.5f).create(),
                // 滑动的监听
                new OnSlideListenerAdapter() {
                    @Override
                    public void onSlide(@FloatRange(from = 0.0,
                            to = 1.0) float percent) {
                        super.onSlide(percent);
                    }
                });

        // MyApplication.getActivityHelper().printAllActivity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTvEdgeRange = (TextView) findViewById(R.id.tv_edge_range);
        mSbEdgeRange = (SeekBar) findViewById(R.id.sb_edge_range);
        mTvSlideOutRange = (TextView) findViewById(R.id.tv_slide_out_range);
        mSbSlideOutRange = (SeekBar) findViewById(R.id.sb_slide_out_range);

        if (mSlideBackLayout == null) {
            return;
        }

        mSbEdgeRange.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvEdgeRange.setText(getString(R.string.edge_response_percent) + progress + "%");
                if (fromUser) {
                    mSlideBackLayout.setEdgeRangePercent(progress * 1.0f / mSbEdgeRange.getMax());
                }
            }
        });

        mSbSlideOutRange.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvSlideOutRange.setText(getString(R.string.close_page_percent) + progress + "%");
                if (fromUser) {
                    mSlideBackLayout.setSlideOutRangePercent(progress * 1.0f / mSbSlideOutRange.getMax());
                }
            }
        });

        mSbEdgeRange.setProgress((int) (mSlideBackLayout.getEdgeRangePercent() * 100));

        mSbSlideOutRange.setProgress((int) (mSlideBackLayout.getSlideOutRangePercent() * 100));

    }

    public void jump(View view) {
        startActivity(new Intent(this, SecondActivity.class));
        overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_none);
    }

    public void enableEdgeSlide(View view) {
        mSlideBackLayout.edgeOnly(!mSlideBackLayout.isEdgeOnly());
        ((Button) view).setText(mSlideBackLayout.isEdgeOnly() ? getString(R.string.slide_full_toggle)
                : getString(R.string.slide_edge_toggle));
    }

    public void disableSlide(View view) {
        mSlideBackLayout.lock(!mSlideBackLayout.isLock());
        ((Button) view).setText(mSlideBackLayout.isLock() ? getString(R.string.slide_enable_toggle)
                : getString(R.string.slide_forbid_toggle));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSlideBackLayout.isComingToFinish();
        overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out);
    }

}
