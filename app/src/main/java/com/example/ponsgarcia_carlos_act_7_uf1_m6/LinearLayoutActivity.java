package com.example.ponsgarcia_carlos_act_7_uf1_m6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

public class LinearLayoutActivity extends Activity {

    private Intent intent;
    private ToggleButton toggleButton;
    private Switch switchButton;
    private int defaultColor;
    private boolean[] isCheckedVector = new boolean[2];
    private TextView textView;
    private ImageButton imageButton;
    private View view;
    private ImageButton[] imageButtons = new ImageButton[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);

        textView = findViewById(R.id.txtLinear);

        switchButton = findViewById(R.id.switchButtonLinear);
        toggleButton = findViewById(R.id.toggleButtonLinear);
        imageButton = findViewById(R.id.imgLinear);
        view = this.getWindow().getDecorView();
        defaultColor = getColor(R.color.cian);

        imageButtons[0] = findViewById(R.id.buttonLinearLayoutBack);
        imageButtons[1] = findViewById(R.id.buttonLinearLayoutForward);

        RotateAnimation animation = new RotateAnimation(
                0,
                360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

        int nButto = 0;
        for (boolean b : getButtonsStatus(getIntent())) {
            putButtonsStatus(b, nButto++);
        }

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                imageButton.setVisibility(View.VISIBLE);
                isCheckedVector[0] = true;
            } else {
                imageButton.clearAnimation();
                imageButton.setVisibility(View.INVISIBLE);
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

        imageButton.setOnClickListener(view -> {
            if (isAnimated(imageButton)) {
                view.clearAnimation();
            }else {
                view.startAnimation(animation);
            }
        });
    }

    private void darkModeOff() {
        view.setBackgroundColor(defaultColor);
        toggleButton.setTextColor(defaultColor);
        toggleButton.setBackground(getDrawable(R.drawable.toggle_style));
        textView.setTextColor(getColor(R.color.blue));
        switchButton.setTextColor(getColor(R.color.blue));
        for (ImageButton ib : imageButtons
        ) {
            ib.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.blue),
                    PorterDuff.Mode.MULTIPLY);
        }    }

    private void darkModeOn() {
        view.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);
        switchButton.setTextColor(Color.WHITE);
        toggleButton.setTextColor(Color.BLACK);
        toggleButton.setBackground(getDrawable(R.drawable.toggle_style_dark));
        for (ImageButton ib : imageButtons
        ) {
            ib.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cian),
                    PorterDuff.Mode.MULTIPLY);
        }
    }

    public void switchAct(View view){
        switch (view.getId()){
            case R.id.buttonLinearLayoutBack:
                finish();
                break;
            case R.id.buttonLinearLayoutForward:
                intent = new Intent(this, TableLayoutActivity.class);
                intent.putExtra("isChecked", isCheckedVector);
                startActivity(intent);
                break;
        }
    }
    private boolean[] getButtonsStatus(Intent intent){
        return intent.getBooleanArrayExtra("isChecked");
    }
    private void putButtonsStatus(boolean status, int nButton){
        if (nButton==0){
            toggleButton.setChecked(status);
            if (status) imageButton.setVisibility(View.VISIBLE);
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
    private boolean isAnimated(ImageButton imageButton) {
        return imageButton.getAnimation()!=null;
    }
}
