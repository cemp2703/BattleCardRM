package Clases;

public class Barrera extends VectorCartas implements Cloneable{
	public static final int MAXBARRERACARDS = 5;
	
	public Barrera(){
		super(MAXBARRERACARDS);
	}
	
	public Barrera clone(){
		Barrera clon=new Barrera();
		return clon;
	}
}
