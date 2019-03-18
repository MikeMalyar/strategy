package com.strategy.solvingequations.methods;

import com.strategy.solvingequations.mathparser.MathParser;

public class BinaryDivideMethod implements SolvingStrategy {

    private double delta = 0;

    public BinaryDivideMethod() { }

    @Override
    public double evaluate(String function, String variable, double a, double b, int n, double e) {
        MathParser parser = new MathParser();

        double c;
        int k=1;

        double f1, f2;

        try {
            do {
                c = (a + b) / 2;

                parser.setVariable(variable, a);
                f1 = parser.Parse(function);
                parser.setVariable(variable, c);
                f2 = parser.Parse(function);

                if (f1 * f2 <= 0) {
                    b = c;
                } else {
                    a = c;
                }
                k++;

                parser.setVariable(variable, c);
                f2 = parser.Parse(function);
            } while (Math.abs(a - b) > e && Math.abs(f2) > delta && k < n);
        }
        catch (Exception ex) {
            return 0;
        }
        return c;
    }
}
