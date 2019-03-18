package com.strategy.solvingequations;

import com.strategy.solvingequations.methods.SolvingStrategy;

public class SolvingContext {

    private String function;
    private String variable;
    private SolvingStrategy strategy;

    public SolvingContext() { }

    public double evaluate(int x0, int x1, int n, double e) {
        return strategy.evaluate(function, variable, x0, x1, n, e);
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public SolvingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SolvingStrategy strategy) {
        this.strategy = strategy;
    }
}
