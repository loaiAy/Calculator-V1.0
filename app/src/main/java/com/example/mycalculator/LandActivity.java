package com.example.mycalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LandActivity extends MainActivity{

    /* onCreate() method when creating new land activity from main activity */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        Intent intent = getIntent();
        expression = intent.getStringExtra("exp");
        EditText e = (EditText) findViewById((R.id.texteditor));
        e.setText(expression);
        getSupportActionBar().hide();
    }

    /* over riding the onStart() method of the main activity to handle the new buttons on the landscape layout */
    @Override
    protected void onStart() {
        super.onStart();
        button = (Button) findViewById(R.id.secsin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                char c = expression.length() == 0 ? ' ' : expression.charAt(expression.length() - 1);
                boolean b = c == ' ' || c == '(' || c == '\u002b' || c == '\u2212' || c == '\u00f7' || c == '\u2217';
                if (b) {
                    expression += "sin(";
                    openParentheses++;
                } else {
                    expression += '\u2217' + "sin(";
                    openParentheses++;
                }
                e.setText(expression);
            }
        });

        button = (Button) findViewById(R.id.seccos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                char c = expression.length() == 0 ? ' ' : expression.charAt(expression.length() - 1);
                boolean b = c == ' ' || c == '(' || c == '\u002b' || c == '\u2212' || c == '\u00f7' || c == '\u2217';
                if (b) {
                    expression += "cos(";
                    openParentheses++;
                } else {
                    expression += '\u2217' + "cos(";
                    openParentheses++;
                }
                e.setText(expression);
            }
        });

        button = (Button) findViewById(R.id.sectan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                char c = expression.length() == 0 ? ' ' : expression.charAt(expression.length() - 1);
                boolean b = c == ' ' || c == '(' || c == '\u002b' || c == '\u2212' || c == '\u00f7' || c == '\u2217';
                if (b) {
                    expression += "tan(";
                    openParentheses++;
                } else {
                    expression += '\u2217' + "tan(";
                    openParentheses++;
                }
                e.setText(expression);
            }
        });

        button = (Button) findViewById(R.id.secpower);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                char c = expression.length() == 0 ? ' ' : expression.charAt(expression.length() - 1);
                if (c != ' ' && (Character.isDigit(c) || c == 41)) {
                    expression += "^(";
                    openParentheses++;
                    e.setText(expression);
                }
            }
        });

        button = (Button) findViewById(R.id.secroot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                sqrtCalc();
                e.setText(expression);
            }
        });

        button = (Button) findViewById(R.id.adjustColor);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                if (screenColor == 1) {
                    e.setBackground(getResources().getDrawable(R.drawable.widebackground1));
                    screenColor++;
                } else if (screenColor == 2) {
                    e.setBackground(getResources().getDrawable(R.drawable.widebackground2));
                    screenColor++;
                } else if (screenColor == 3) {
                    screenColor++;
                    e.setBackground(getResources().getDrawable(R.drawable.widebackground3));
                } else if (screenColor == 4) {
                    screenColor++;
                    e.setBackground(getResources().getDrawable(R.drawable.widebackground4));
                } else if (screenColor == 5) {
                    screenColor = 1;
                    e.setBackground(getResources().getDrawable(R.color.black));
                }
            }
        });
    }

    /* finishing the activity and returning to maim activity */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.finish();
    }
}
