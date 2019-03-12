package functions;

import functions.meta.*;

public class TransformFunctions {

    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power){
        return new Power(f, power);
    }

    public static Function sum(Function firstFunc, Function secondFunc){
        return new Sum(firstFunc, secondFunc);
    }

    public static Function mult(Function firstFunc, Function secondFunc){
        return new Multi(firstFunc, secondFunc);
    }

    public static Function composition(Function firstFunc, Function secondFunc){
        return new Composition(firstFunc, secondFunc);
    }

    public static double integral(Function func, double leftValue, double rightValue, double n){
        int h = (int) ((rightValue - leftValue) / n);
        double x = leftValue;
        double answer = 0;
        for (int i = 0; i < n; i++, x += h){
            answer += (func.getFunctionValue(x) + func.getFunctionValue(x + h))/2;
        }
        return answer * h;
    }
}
