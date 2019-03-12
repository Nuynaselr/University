package functions;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class TabFunctions {
    private TabFunctions(){}

    public static TabulatedFunction tabulate(Function func, double leftX, double rigthX, int pointCounts) throws InappropriateFunctionPointException {
        if (leftX < func.getLeftDomainBorder() || rigthX > func.getRightDomainBorder() ){
            throw new InappropriateFunctionPointException("incorrect arguments");
        }
        FunctionPoint[] arrayPoint = new FunctionPoint[pointCounts];
        double length = (rigthX - leftX)/pointCounts;
        for (int i = 0; i < pointCounts; i++){
            arrayPoint[i] = new FunctionPoint(leftX + i * length, func.getFunctionValue(leftX + i * length));
        }
        return new ArrayTabulatedFunction(arrayPoint);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out){
        try( DataOutputStream sOut = new DataOutputStream(out) ){
            int pointCount = function.getPointsCount();
            sOut.writeInt(pointCount);
            for(int i = 0; i < pointCount; i++){
                sOut.writeDouble(function.getPointX(i));
                sOut.writeDouble(function.getPointY(i));
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer wout){
        try( BufferedWriter sOut = new BufferedWriter(wout) ){
            int pointCount = function.getPointsCount();
            sOut.write(pointCount + "\n");
            for(int i = 0; i < pointCount; i++){
                sOut.write(function.getPointX(i) + " " + function.getPointY(i) + "\n");
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println();
    }

    public static ArrayTabulatedFunction inputTabulatedFunction(InputStream in){
        try (DataInputStream sIn = new DataInputStream(in)){
            int pointCount = sIn.readInt();
            FunctionPoint[] funcPoint = new FunctionPoint[pointCount];
            for (int i = 0; i < pointCount; i++){
                funcPoint[i] = new FunctionPoint();
                funcPoint[i].setX(sIn.readDouble());
                funcPoint[i].setY(sIn.readDouble());
            }
            return new ArrayTabulatedFunction(funcPoint);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static ArrayTabulatedFunction readTabulatedFunction(Reader rin){
        try {
            StreamTokenizer sIn = new StreamTokenizer(rin);
            sIn.nextToken();
            int pointCount = (int) sIn.nval;
            sIn.nextToken();
            FunctionPoint[] funcPoint = new FunctionPoint[pointCount];
            for (int i = 0; i < pointCount; i++){
                funcPoint[i] = new FunctionPoint();
                funcPoint[i].setX(sIn.nval);
                sIn.nextToken();
                funcPoint[i].setY(sIn.nval);
                sIn.nextToken();
            }
            return new ArrayTabulatedFunction(funcPoint);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static TabulatedFunctionFactory tfFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory tabulatedFunctionFactory) {
        tfFactory = tabulatedFunctionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        return tfFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        return tfFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) throws InappropriateFunctionPointException {
        return tfFactory.createTabulatedFunction(points);
    }


    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cl,
                                                            double leftX, double rightX, int pointsCount)
            throws IllegalArgumentException {
        try {
            return cl.getConstructor(double.class, double.class, int.class).newInstance(leftX, rightX, pointsCount);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cl,
                                                            double leftX, double rightX, double[] values)
            throws IllegalArgumentException {
        try {
            return cl.getConstructor(double.class, double.class, double[].class).newInstance(leftX, rightX, values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cl,
                                                            FunctionPoint[] points) throws IllegalArgumentException {
        try {
            return cl.getConstructor(FunctionPoint[].class).newInstance((Object) points);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> cl,
                                             Function function, double leftX, double rightX, int pointsCount){
        if (leftX >= rightX)
            throw new IllegalArgumentException("Invalid bounds");
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
            throw new IllegalArgumentException("Tabulation bounds are out of function's domain");
        if (pointsCount < 2)
            throw new IllegalArgumentException("Points number is less than 2");

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double delta = (rightX - leftX) / (pointsCount - 1.0);

        for (int i = 0; i < pointsCount; ++i){
            double x = leftX + delta * i;
            points[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return createTabulatedFunction(cl, points);
    }

}