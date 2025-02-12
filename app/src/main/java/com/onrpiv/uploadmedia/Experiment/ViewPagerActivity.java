package com.onrpiv.uploadmedia.Experiment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.onrpiv.uploadmedia.R;
import com.onrpiv.uploadmedia.Utilities.LightBulb;

/**
 * author: sarbajit mukherjee
 * Created by sarbajit mukherjee on 09/07/2020.
 */

public class ViewPagerActivity extends AppCompatActivity {
    Button startAnimation, stopAnimation;
    ImageView imageAnim;
    String [] urls;
    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startAnimation = (Button) findViewById(R.id.startAnim);
        stopAnimation = (Button) findViewById(R.id.stopAnim);
        stopAnimation.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        urls = intent.getStringArrayExtra("string-array-urls");

        Context context = getApplicationContext();

        new LightBulb(context, startAnimation).setLightBulbOnClick("Start Animation",
                "View the animation and consider if you can tell where the particles move between the first and second frame. If you can't correlate the images with your eyes, the PIV algorithm is less likely to be able to do so.",
                getWindow());

        imageAnim =  (ImageView) findViewById(R.id.reviewView);
        animation = new AnimationDrawable();
        Drawable d1 = Drawable.createFromPath(urls[0]);
        Drawable d2 = Drawable.createFromPath(urls[1]);

        Bitmap bitmap1 = ((BitmapDrawable) d1).getBitmap();
        Drawable newDrawable1 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap1, 3000, 2750, true));

        Bitmap bitmap2 = ((BitmapDrawable) d2).getBitmap();
        Drawable newDrawable2 = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap2, 3000, 2750, true));

        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        animation.addFrame(newDrawable1,500);
        animation.addFrame(newDrawable2,500);
        animation.addFrame(transparentDrawable, 1);
        animation.addFrame(newDrawable1,1);
        animation.setOneShot(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAnimation(View view) {
        startAnimation.setEnabled(false);
        stopAnimation.setEnabled(true);

        animation.start();
        imageAnim.setImageDrawable(animation);
    }

    public void stopAnimation(View view) {
        stopAnimation.setEnabled(false);
        startAnimation.setEnabled(true);

        view.clearAnimation();
        animation.stop();
    }

    // ensuring the screen is locked to vertical position
    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
