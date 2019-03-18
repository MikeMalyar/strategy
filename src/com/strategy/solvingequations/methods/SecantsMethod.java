package com.strategy.solvingequations.methods;

import com.strategy.solvingequations.mathparser.MathParser;

public class SecantsMethod implements SolvingStrategy {

    public SecantsMethod() { }

    @Override
    public double evaluate(String function, String variable, double x1, double x2, int n, double e)
    {
        double result;
        int k=0;
        double f1,f2;
        MathParser ob=new MathParser();

        try {
            do {
                ob.setVariable(variable, x1);
                f1 = ob.Parse(function);

                ob.setVariable(variable, x2);
                f2 = ob.Parse(function);

                result = x2 - (f2 * (x2 - x1)) / (f2 - f1);
                k++;
                x1 = x2;
                x2 = result;
            } while (Math.abs(x1 - x2) > e && k < n);
        }
        catch (Exception ex) {
            return 0;
        }

        return result;
    }

}