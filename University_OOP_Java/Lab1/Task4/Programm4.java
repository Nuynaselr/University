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

class SecondClass{
	private int  firstValue, secondValue;

	SecondClass(int firstValue, int seconValue){
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}
	public int getFisrtValue(){
		return firstValue;
	}
	public int getSecondValue(){
		return secondValue;
	}
	public void setFirstValue(int value){
		this.firstValue = value;
	}
	public void setSecondValue(int value){
		this.secondValue = value;
	}
	public int calc(){
		return firstValue - secondValue;
	}
}
