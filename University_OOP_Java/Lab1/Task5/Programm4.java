import myfirstpackage.*;

class Programm4{
	public static void main(String[] s){
		SecondClass o = new SecondClass(0, 0);
		int i, j;
		for (i = 1; i <= 8; i++){
			for (j = 1; j <= 8; j++){
				o.setFirstValue(i);
				o.setSecondValue(j);
				System.out.print(o.calc());
				System.out.print(" ");				
			}		
			System.out.println();
		}	
	}
}
