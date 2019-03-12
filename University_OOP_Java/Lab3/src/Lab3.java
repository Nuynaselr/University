import functions.*;
import javafx.scene.chart.Axis;

class Lab3 {
    public static void main(String[] s) throws InappropriateFunctionPointException {
        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(1, 9, 3);
        System.out.println(list.getPointX(1) + "    " + list.getPointsCount());
        list.addNodeToTail(656);
        System.out.println(list.getPointsCount() + "    " + list.getPointX(3));
    }
}