package com.strategy.solvingequations.mathparser;

import java.util.HashMap;

public class MathParser
{
    private HashMap<String, Double> variables;

    public MathParser()
    {
        variables = new HashMap<>();

        variables.put("e", Math.E);
        variables.put("pi", Math.PI);
    }

    public void setVariable(String variableName, Double variableValue)
    {
        variables.put(variableName, variableValue);
    }

    public Double getVariable(String variableName) throws Exception
    {
        if (!variables.containsKey(variableName)) {
            throw new Exception("Error: Try get unexists variable '"+variableName+"'");
        }
        return variables.get(variableName);
    }

    public double Parse(String s) throws Exception
    {
        s = s.toLowerCase();
        s = s.replace(" ", "");

        Result result = PlusMinus(s);
        if (!result.rest.isEmpty()) {
            throw new Exception("Error: can't full parse" + "\n" + "rest: " + result.rest);
        }
        return result.acc;
    }

    private Result PlusMinus(String s) throws Exception
    {
        Result current = MulDiv(s);
        double acc = current.acc;

        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = MulDiv(next);
            if (sign == '+') {
                acc += current.acc;
            } else {
                acc -= current.acc;
            }
        }
        return new Result(acc, current.rest);
    }

    private Result Bracket(String s) throws Exception
    {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = PlusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                throw new Exception("Error: not close bracket");
            }
            return r;
        }
        return FunctionVariable(s);
    }

    private Result FunctionVariable(String s) throws Exception
    {
        String f = "";
        int i = 0;
        // ищем название функции или переменной
        // имя обязательно должна начинаться с буквы
        while (i < s.length() && (Character.isLetter(s.charAt(i)) || (Character.isDigit(s.charAt(i)) && i > 0))) {
            f += s.charAt(i);
            i++;
        }
        if (!f.isEmpty()) { // если что-нибудь нашли
            if (s.length() > i && s.charAt(i) == '(') { // и следующий символ скобка значит - это функция
                Result r = Bracket(s.substring(f.length()));
                return processFunction(f, r);
            } else { // иначе - это переменная
                return new Result(getVariable(f), s.substring(f.length()));
            }
        }
        return Num(s);
    }

    private Result MulDiv(String s) throws Exception
    {
        Result current = Bracket(s);

        double acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;

            String next = current.rest.substring(1);
            Result right = Bracket(next);

            if (sign == '*') {
                acc *= right.acc;
            } else {
                acc /= right.acc;
            }

            current = new Result(acc, right.rest);
        }
    }

    private Result Num(String s) throws Exception
    {
        int i = 0;
        int dot_cnt = 0;
        boolean negative = false;
        // число также может начинаться с минуса
        if( s.charAt(0) == '-' ){
            negative = true;
            s = s.substring( 1 );
        }
        // разрешаем только цифры и точку
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            // но также проверям, что в числе может быть только одна точка!
            if (s.charAt(i) == '.' && ++dot_cnt > 1) {
                throw new Exception("not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if( i == 0 ){ // что-либо похожее на число мы не нашли
            throw new Exception( "can't get valid number in '" + s + "'" );
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if( negative ) dPart = -dPart;
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

    // Тут определяем все нашие функции, которыми мы можем пользоватся в формулах
    private Result processFunction(String func, Result r)
    {
        if (func.equals("sin"))
            return new Result(Math.sin(r.acc), r.rest);
        else if (func.equals("cos"))
            return new Result(Math.cos(r.acc), r.rest);
        else if (func.equals("tan"))
            return new Result(Math.tan(r.acc), r.rest);
        else if (func.equals("sqr"))
            return new Result(Math.pow(r.acc, 2), r.rest);
        else if (func.equals("cube"))
            return new Result(Math.pow(r.acc, 3), r.rest);
        else if (func.equals("ln"))
            return new Result(Math.log(r.acc), r.rest);
        else if (func.equals("sqrt"))
            return new Result(Math.sqrt(r.acc), r.rest);
        else if(func.equals("exp"))
            return new Result(Math.exp(r.acc), r.rest);
        else
            System.err.println("function '" + func + "' is not defined");

        return r;
    }
}