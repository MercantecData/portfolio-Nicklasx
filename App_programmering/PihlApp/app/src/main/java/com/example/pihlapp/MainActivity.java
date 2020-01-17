package com.example.pihlapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch sw1 = findViewById(R.id.DarkMode);
        final TextView txt = findViewById(R.id.hello);
        final View main = findViewById(R.id.test);
        final TableLayout t1 = findViewById(R.id.table);
        final TableLayout t2 = findViewById(R.id.table2);
        final EditText user = findViewById(R.id.username);
        final EditText pass = findViewById(R.id.password);
        final Button login = findViewById(R.id.login);
        final Button logout = findViewById(R.id.logout);
        final TextView userTxt = findViewById(R.id.usernameTxt);

        t2.setVisibility(t2.INVISIBLE);
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
                    user.setTextColor(getColor(R.color.Light));
                    pass.setTextColor(getColor(R.color.Light));
                    userTxt.setTextColor(getColor(R.color.Light));
                } else {
                    // The toggle is disabled
                    sw1.setChecked(false);
                    txt.setText(R.string.light_mode);
                    txt.setTextColor(getColor(R.color.Dark));
                    main.setBackgroundColor(getColor(R.color.Light));
                    sw1.setTextColor(getColor(R.color.Dark));
                    sw1.setText("Dark mode off");
                    user.setTextColor(getColor(R.color.Dark));
                    pass.setTextColor(getColor(R.color.Dark));
                    userTxt.setTextColor(getColor(R.color.Dark));
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("admin") &&
                        pass.getText().toString().equals("admin")) {
                    userTxt.setText(user.getText());
                    t1.setVisibility(t1.INVISIBLE);
                    t2.setVisibility(t2.VISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setVisibility(t2.INVISIBLE);
                t1.setVisibility(t1.VISIBLE);
            }
        });
    }
}
