package com.example.pihlapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Switch sw1 = (Switch) findViewById(R.id.DarkMode);
        final TextView txt = (TextView) findViewById(R.id.textView3);
        final View main = (View) findViewById(R.id.test);
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton SwitchView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sw1.setChecked(true);
                    txt.setText(R.string.dark_mode);
                    txt.setTextColor(getColor(R.color.Light));
                    main.setBackgroundColor(getColor(R.color.Dark));
                    sw1.setTextColor(getColor(R.color.Light));
                    sw1.setText("Dark mode on");
                } else {
                    // The toggle is disabled
                    sw1.setChecked(false);
                    txt.setText(R.string.light_mode);
                    txt.setTextColor(getColor(R.color.Dark));
                    main.setBackgroundColor(getColor(R.color.Light));
                    sw1.setTextColor(getColor(R.color.Dark));
                    sw1.setText("Dark mode off");
                }
            }
        });
    }
}
