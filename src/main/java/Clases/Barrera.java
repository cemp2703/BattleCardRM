package Clases;

public class Barrera implements Cloneable{
	public static final int MAXBARRERACARDS = 5;
	
	public VectorCartas Cartas;
	
	public Barrera(){
		Cartas=new VectorCartas(MAXBARRERACARDS);
	}
	
	public Barrera(VectorCartas pCartas){
		Cartas=pCartas;
	}
	
	public Barrera clone(){
		VectorCartas vc=this.Cartas.clone();
		Barrera clon=new Barrera(vc);
		return clon;
	}
	
	
}
