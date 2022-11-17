package com.example.ponsgarcia_carlos_act_7_uf1_m6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ShareActivity extends Activity {

    private Intent intent;
    private ToggleButton toggleButton;
    private Switch switchButton;
    private ToggleButton toggleButtonPlay;
    private int defaultColor;
    private boolean[] isCheckedVector = new boolean[2];
    private TextView textView;
    private ImageView imageView;
    private View view;
    private RotateAnimation animation;
    private Button[] buttons = new Button[2];
    private ImageButton[] imageButtons = new ImageButton[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        textView = findViewById(R.id.txtShare);
        switchButton = findViewById(R.id.switchButtonShare);
        toggleButton = findViewById(R.id.toggleButtonShare);
        imageView = findViewById(R.id.imgShare);
        view = this.getWindow().getDecorView();
        defaultColor = getColor(R.color.cian);
        toggleButtonPlay = findViewById(R.id.toggleButtonSharePlay);
        buttons[0] = findViewById(R.id.buttonExit);
        buttons[1] = findViewById(R.id.buttonShare);
        imageButtons[0] = findViewById(R.id.buttonShareRotateLeft);
        imageButtons[1] = findViewById(R.id.buttonShareRotateRight);
        imageButtons[2] = findViewById(R.id.buttonShareLayoutBack);
        imageButtons[3] = findViewById(R.id.buttonShareLayoutForward);

        animation = new RotateAnimation(
                0,
                360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);

        int nButto = 0;
        for (boolean b : getButtonsStatus(getIntent())) {
            putButtonsStatus(b, nButto++);
        }

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (toggleButtonPlay.isChecked()){
                    imageView.startAnimation(animation);
                }
                imageView.setVisibility(View.VISIBLE);
                isCheckedVector[0] = true;
            } else {
                imageView.clearAnimation();
                imageView.setVisibility(View.INVISIBLE);
                isCheckedVector[0] = false;
            }
        });

        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                darkModeOn();
                isCheckedVector[1] = true;
            } else {
                darkModeOff();
                isCheckedVector[1] = false;
            }
        });

        toggleButtonPlay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (toggleButton.isChecked()){
                    imageView.startAnimation(animation);
                }
            } else {
                imageView.clearAnimation();
            }
        });
    }

    private void darkModeOff() {
        view.setBackgroundColor(defaultColor);
        toggleButton.setTextColor(defaultColor);
        toggleButton.setBackground(getDrawable(R.drawable.toggle_style));
        textView.setTextColor(getColor(R.color.blue));
        switchButton.setTextColor(getColor(R.color.blue));
        for (Button b : buttons
        ) {
            b.setTextColor(getColor(R.color.blue));
        }
        for (ImageButton ib : imageButtons
        ) {
            ib.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.blue),
                    PorterDuff.Mode.MULTIPLY);
        }
        toggleButtonPlay.setBackground(getDrawable(R.drawable.toggle_style_share));
    }

    private void darkModeOn() {
        view.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);
        switchButton.setTextColor(Color.WHITE);
        toggleButton.setTextColor(Color.BLACK);
        toggleButton.setBackground(getDrawable(R.drawable.toggle_style_dark));
        for (Button b : buttons
        ) {
            b.setTextColor(Color.WHITE);
        }
        for (ImageButton ib : imageButtons
        ) {
            ib.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cian),
                    PorterDuff.Mode.MULTIPLY);
        }
        toggleButtonPlay.setBackground(getDrawable(R.drawable.toggle_style_share_dark));
    }

    public void switchAct(View view){
        switch (view.getId()){
            case R.id.buttonShareLayoutBack:
                finish();
                break;
            case R.id.buttonShareLayoutForward:
                intent = new Intent(this, FrameLayoutActivity.class);
                intent.putExtra("isChecked", isCheckedVector);
                startActivity(intent);
                break;
            case R.id.buttonExit:
                finishAffinity();
                break;
            case R.id.buttonShare:
                intent = new Intent(Intent.ACTION_SEND);

                Uri imgUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ccximg);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imgUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, imgUri);
                startActivity(Intent.createChooser(intent, "Share with"));
                break;
        }
    }

    private boolean[] getButtonsStatus(Intent intent){
        return intent.getBooleanArrayExtra("isChecked");
    }
    private void putButtonsStatus(boolean status, int nButton){
        if (nButton==0){
            toggleButton.setChecked(status);
            if (status) imageView.setVisibility(View.VISIBLE);
            isCheckedVector[0] = status;
        }else {
            switchButton.setChecked(status);
            if (status){
                darkModeOn();
            }else {
                view.setBackgroundColor(defaultColor);
            }
            isCheckedVector[1] = status;
        }
    }

    public void changeRotation(View view){
        switch (view.getId()){
            case R.id.buttonShareRotateRight:
                animation = new RotateAnimation(
                        0,
                        360,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(2000);
                animation.setRepeatCount(Animation.INFINITE);
                if (isAnimated(imageView)) imageView.startAnimation(animation);
                break;
            case R.id.buttonShareRotateLeft:
                animation = new RotateAnimation(
                        360,
                        0,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(2000);
                animation.setRepeatCount(Animation.INFINITE);
                if (isAnimated(imageView)) imageView.startAnimation(animation);
                break;
        }
    }

    private boolean isAnimated(ImageView imageView) {
        return imageView.getAnimation()!=null;
    }

}