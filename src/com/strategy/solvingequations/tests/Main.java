package com.strategy.solvingequations.tests;

import com.strategy.solvingequations.SolvingContext;
import com.strategy.solvingequations.methods.BinaryDivideMethod;
import com.strategy.solvingequations.methods.NewtonMethod;
import com.strategy.solvingequations.methods.SecantsMethod;

public class Main {

    public static void main(String[] args) {
        SolvingContext solvingContext = new SolvingContext();

        solvingContext.setFunction("x*x - 2");
        solvingContext.setVariable("x");

        solvingContext.setStrategy(new BinaryDivideMethod());
        System.out.print("Solved by Binary divide method >> ");
        System.out.println(solvingContext.evaluate(0, 10, 100, 0.0001));

        solvingContext.setStrategy(new SecantsMethod());
        System.out.print("Solved by Secants method >> ");
        System.out.println(solvingContext.evaluate(0, 10, 100, 0.0001));

        solvingContext.setStrategy(new NewtonMethod());
        System.out.print("Solved by Newton method >> ");
        System.out.println(solvingContext.evaluate(0, 10, 100, 0.0001));
    }

}
