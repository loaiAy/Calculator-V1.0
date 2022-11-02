package com.example.mycalculator;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected String expression = "";
    protected String backUp = "";
    protected Button button;
    protected int screenColor = 1;
    protected int openParentheses = 0;
    protected int closeParentheses = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    /* starting new activity on screen orientation */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            System.out.println("land");
            Intent myIntent = new Intent(MainActivity.this, LandActivity.class);
            myIntent.putExtra("exp", expression);
            MainActivity.this.startActivity(myIntent);
        }
        expression = "";
        EditText e = (EditText) findViewById(R.id.texteditor);
        e.setText(expression);
    }

    protected void onStart() {
        super.onStart();
        button = (Button) findViewById(R.id.zero);
        lambda(button);

        button = (Button) findViewById(R.id.one);
        lambda(button);

        button = (Button) findViewById(R.id.two);
        lambda(button);

        button = (Button) findViewById(R.id.three);
        lambda(button);

        button = (Button) findViewById(R.id.four);
        lambda(button);

        button = (Button) findViewById(R.id.five);
        lambda(button);

        button = (Button) findViewById(R.id.six);
        lambda(button);

        button = (Button) findViewById(R.id.seven);
        lambda(button);

        button = (Button) findViewById(R.id.eight);
        lambda(button);

        button = (Button) findViewById(R.id.nine);
        lambda(button);

        button = (Button) findViewById(R.id.substract);
        operator(button);

        button = (Button) findViewById(R.id.plus);
        operator(button);

        button = (Button) findViewById(R.id.mult);
        operator(button);

        button = (Button) findViewById(R.id.div);
        operator(button);

        /* button for recovering the last answer*/
        button = (Button) findViewById(R.id.recover);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                expression = backUp;
                EditText e = (EditText) findViewById(R.id.texteditor);
                e.setText(expression);
            }
        });

        /* button for changing backgrounds */
        button = (Button) findViewById(R.id.adjustColor);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                if (screenColor == 1) {
                    e.setBackground(getResources().getDrawable(R.drawable.darkbackback));
                    screenColor++;
                } else if (screenColor == 2) {
                    e.setBackground(getResources().getDrawable(R.drawable.dark_grey_bac));
                    screenColor++;
                } else if (screenColor == 3) {
                    screenColor++;
                    e.setBackground(getResources().getDrawable(R.drawable.new1));
                } else if (screenColor == 4) {
                    screenColor++;
                    e.setBackground(getResources().getDrawable(R.drawable.new2));
                } else if (screenColor == 5) {
                    screenColor = 1;
                    e.setBackground(getResources().getDrawable(R.color.black));
                }
            }
        });

        /* button for resetting expression */
        button = (Button) findViewById(R.id.clear);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                expression = "";
                e.setText(expression);
            }
        });

        /* button for removing last character from the expression */
        ImageButton b = (ImageButton) findViewById(R.id.deleteButton);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (expression.length() != 0) {
                    EditText e = (EditText) findViewById(R.id.texteditor);
                    char c = expression.charAt(expression.length() - 1);
                    expression = expression.length() == 0 ? "" : expression.substring(0, expression.length() - 1);
                    if (c == '(') {
                        openParentheses--;
                    } else if (c == ')') {
                        closeParentheses--;
                    }
                    e.setText(expression);
                }
            }
        });

        /* this button popups a menu that include a couple math operation such as cos,sin, etc..*/
        /* the onClick method of this button figure which menu item have been clicked */
        button = (Button) findViewById(R.id.mathSym);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        char c = expression.length() == 0 ? ' ' : expression.charAt(expression.length() - 1);
                        boolean b = c == ' ' || c == '(' || c == '\u002b' || c == '\u2212' || c == '\u00f7' || c == '\u2217'; //unicode for math symbols
                        switch (menuItem.getItemId()) {
                            case R.id.sqrt:
                                sqrtCalc();
                                e.setText(expression);
                                return true;

                            case R.id.power:
                                if (c != ' ' && (Character.isDigit(c) || c == 41)) {
                                    expression += "^(";
                                    openParentheses++;
                                    e.setText(expression);
                                }
                                return true;

                            case R.id.sin:
                                if (b) {
                                    expression += "sin(";
                                    openParentheses++;
                                } else {
                                    expression += '\u2217' + "sin(";
                                    openParentheses++;
                                }
                                e.setText(expression);
                                return true;

                            case R.id.cos:
                                if (b) {
                                    expression += "cos(";
                                    openParentheses++;
                                } else {
                                    expression += '\u2217' + "cos(";
                                    openParentheses++;
                                }
                                e.setText(expression);
                                return true;

                            case R.id.tan:
                                if (b) {
                                    expression += "tan(";
                                    openParentheses++;
                                } else {
                                    expression += '\u2217' + "tan(";
                                    openParentheses++;
                                }
                                e.setText(expression);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        button = (Button) findViewById(R.id.dot);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                char c = expression.length() == 0 ? ' ' : expression.charAt(expression.length() - 1);
                if (Character.isDigit(c)) {
                    int i = expression.length() - 2;
                    boolean flag = true;
                    while (i > 0 && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        if (expression.charAt(i) == '.') {
                            flag = false;
                            break;
                        }
                        i--;
                    }
                    if (flag) {
                        expression += '.';
                    }
                }
                e.setText(expression);
            }
        });

        button = (Button) findViewById(R.id.parentheses);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                if (expression.isEmpty()) {
                    expression += '(';
                    openParentheses++;
                } else {
                    char c = expression.charAt(expression.length() - 1);
                    if (c == '(' || c == '\u00f7' || c == '\u2212' || c == '\u2217' || c == '\u002b') { //unicode for math symbols
                        expression += '(';
                        openParentheses++;
                    } else if (Character.isDigit(c)) {
                        if (expression.contains("" + '(')) {
                            expression += ')';
                            closeParentheses++;
                        }
                    } else if (c == ')' && closeParentheses < openParentheses) {
                        while (closeParentheses < openParentheses) {
                            expression += ')';
                            closeParentheses++;
                        }
                    }
                }
                e.setText(expression);
            }
        });

        button = (Button) findViewById(R.id.equal);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!expression.isEmpty()) {
                    char c = expression.charAt(expression.length() - 1);
                    if (c == '\u00f7' || c == '\u2212' || c == '\u2217' || c == '\u002b' || c == '(') { //unicode for math symbols
                        Toast.makeText(getApplicationContext(), "invalid syntax!", Toast.LENGTH_LONG).show();
                    } else {
                        if (openParentheses != closeParentheses) {
                            while (openParentheses != closeParentheses) {
                                expression = expression + ')';
                                closeParentheses++;
                            }
                        }
                        EditText e = (EditText) findViewById(R.id.texteditor);
                        evalActivity eva = new evalActivity(expression);
                        double ans = eva.eval(expression);
                        expression = Double.toString(ans);
                        System.out.println(expression);
                        if (expression.equals("Infinity") || expression.equals("-Infinity")) {
                            Toast.makeText(getApplicationContext(), "can't divide by zero!", Toast.LENGTH_LONG).show();
                            expression = "";
                        } else if (expression.equals("NaN")) {
                            Toast.makeText(getApplicationContext(), "can't support imaginary numbers!!", Toast.LENGTH_LONG).show();
                            expression = "";
                        } else if (expression.charAt(0) == '-') {
                            expression = expression.substring(1);
                            expression = '\u2212' + expression;
                        }
                        backUp = expression;
                        e.setText(expression);
                    }
                }
            }
        });
    }

    /* helper method to add sqrt root to the expression */
    protected void sqrtCalc(){
        EditText e = (EditText) findViewById(R.id.texteditor);
        if(expression.length() == 0 || expression.charAt(expression.length() - 1) == '('){
            expression += "sqrt(";
            openParentheses++;
        }
        else{
            char c = expression.charAt(expression.length()-1);
            if(c >= 48 && c <= 57 || c == ')'){
                expression += '\u2217' + "(sqrt("; // u2217 unicode for multiplication
                openParentheses += 2;
            }
            else if(c != '.'){
                expression += "sqrt(";
                openParentheses++;
            }
        }
        e.setText(expression);
    }

    /* helper method to build the math expression */
    protected void operator(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tmp = (String) button.getText();
                EditText e = (EditText) findViewById(R.id.texteditor);
                if(!expression.isEmpty()){
                    char c = expression.charAt(expression.length() - 1);
                    if(tmp.equals("\u2212")){ //unicode for subtraction
                        if((c >= 48 || c == 41 || c == 40) && c <= 57){ //ascii code to check if the is digit or parentheses
                            expression += (String) button.getText();
                        }
                    }
                    else{
                        if((c >= 48 || c == 41) && c <= 57){
                            expression += (String) button.getText();
                        }
                    }
                }
                e.setText(expression);
            }
        });
    }

    /* helper method to build the math expression */
    protected void lambda(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.texteditor);
                expression += button.getText();
                e.setText(expression);
            }
        });
    }
}