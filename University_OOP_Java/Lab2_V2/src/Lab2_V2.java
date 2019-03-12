import functions.*;
import javafx.scene.chart.Axis;

class Lab2_V2{
    public static void main(String[] s){
        TabulatedFunction bigPoint = new TabulatedFunction(0, 15, 7);

        System.out.println(bigPoint.getPointX(3));
        System.out.println(bigPoint.getPointY(3));

        bigPoint.deletePoint(2);
        bigPoint.addPoint(new FunctionPoint(7, 0));
    }
}