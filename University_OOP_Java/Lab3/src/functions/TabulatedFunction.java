package functions;

public interface TabulatedFunction{
    double getLeftDomainBorder();
    double getRightDomainBorder();

    double getFunctionValue(double x);
    int getPointsCount();


    void setPoint(int index, FunctionPoint point);
    void setPointX(int index, double x);
    void setPointY(int index, double y);

    FunctionPoint getPoint(int index);
    double getPointX(int index);
    double getPointY(int index);


    void deletePoint(int index) throws InappropriateFunctionPointException;
    void addPoint(FunctionPoint point);
}