package com.oubowu.slideback.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.oubowu.slideback.SlideBackHelper;
import com.oubowu.slideback.widget.SlideBackLayout;

/**
 * Created by jiao.js on 17/11/2016.
 */

public class FrescoActivity extends AppCompatActivity {
    private SlideBackLayout mSlideBackLayout;
    private SimpleDraweeView mImageView;
    private final String imageUrl = "http://www.ipeeworld.com/wp-content/uploads/2016/10/android_n-128-1.png";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_fresco);
        mSlideBackLayout = SlideBackHelper.attach(this, MyApplication.getActivityHelper(), null,
                null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = (SimpleDraweeView) findViewById(R.id.image_view);
        mImageView.setImageURI(Uri.parse(imageUrl));

    }

    public void jumpAnotherFresco(View view) {
        startActivity(new Intent(this, FrescoActivity.class));
        overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_none);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSlideBackLayout.isComingToFinish();
        overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out);
    }
}
