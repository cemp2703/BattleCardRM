package Clases;

public class Mano implements Cloneable{
	public static final int MAXMANOCARDS = 5;
	
	public VectorCartas Cartas;
	
	public Mano(){
		Cartas=new VectorCartas(MAXMANOCARDS);
	}

	public Mano(VectorCartas pVectorCartas){
		Cartas=pVectorCartas;
	}
	
	public Mano clone(){
		VectorCartas vc= this.Cartas.clone();
		Mano clon=new Mano(vc);
		return clon;
	}
	
}
