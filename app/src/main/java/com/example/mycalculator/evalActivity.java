package com.example.mycalculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class evalActivity {
    private String expression = "";

    public evalActivity(String exp){
        this.expression = exp;
    }

    /* method to evaluate the math expression we receive from the text editor*/
    public double eval(final String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('\u002b')) { // unicode for plus sign
                        x += parseTerm();
                    } else if (eat('\u2212')) { // unicode for minus sign
                        x -= parseTerm();
                    } else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('\u2217')) { // unicode for multiplication
                        x *= parseFactor();
                    } else if (eat('\u00f7')) { // unicode for division
                        x /= parseFactor();
                    } else return x;
                }
            }

            double parseFactor() {
                if (eat('\u002b')) return +parseFactor(); // unary plus
                if (eat('\u2212')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // Parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                }
                else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                }
                else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') {
                        nextChar();
                    }
                    String func = expression.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')'))
                            throw new RuntimeException("Missing ')' after argument to " + func);
                    }
                    else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) {
                        x = Math.sqrt(x);
                    }
                    else if (func.equals("sin")) {
                        x = Math.sin(Math.toRadians(x));
                    }
                    else if (func.equals("cos")) {
                        x = Math.cos(Math.toRadians(x));
                    }
                    else if (func.equals("tan")) {
                        x = Math.tan(Math.toRadians(x));
                    }
                    else throw new RuntimeException("Unknown function: " + func);
                }
                else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) { // power
                    x = Math.pow(x, parseFactor());
                }
                return x;
            }
        }.parse();
    }
}
