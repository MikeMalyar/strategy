package com.strategy.solvingequations.methods;

import com.strategy.solvingequations.mathparser.MathParser;

public class NewtonMethod implements SolvingStrategy {

    private double dx = 0.0001;

    public NewtonMethod() { }

    @Override
    public double evaluate(String function, String variable, double x0, double x1, int n, double e)
    {
        double x = x0;

        try {
            int k = 0;

            while (k < n && Math.abs(x - findNext(function, variable, x)) > e) {
                x = findNext(function, variable, x);
                ++k;
            }
        } catch (Exception ex) {
            return 0;
        }

        return x;
    }

    private double findNext(String function, String variable, double x) throws Exception
    {
        MathParser parser = new MathParser();
        parser.setVariable(variable, x);

        return x - parser.Parse(function) / findDerivative(function, variable, x, dx);
    }

    private double findDerivative(String function, String variable, double x, double dx) throws Exception
    {
        if(dx == 0) throw new IllegalArgumentException();

        MathParser parser = new MathParser();
        parser.setVariable(variable, x + dx);

        double df = parser.Parse(function);

        parser.setVariable(variable, x);

        df -= parser.Parse(function);

        return df / dx;
    }
}
