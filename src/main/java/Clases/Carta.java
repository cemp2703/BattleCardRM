package Clases;

public class Carta implements Cloneable{
	private int valor;

	private int elemento;
	public static class ELEMENTO {
		public final static int CORAZON = 0; // Unicode 2665
		public final static int COCO = 1; // Unicode 2666
		public final static int TREBOL = 2; // Unicode 2663
		public final static int ESPADA = 3;  // Unicode 2660
	}

	private int uso;
	public static class USO {
		public final static char CEMENTERIO = 0;
		public final static char MANO = 1;
		public final static char BARRERA = 2;
		public final static char FIGHT = 3;
		public final static char DECK = 4;
	}

	private int CartaPos;//posicion  de ataque, defenza, etc  almacenado para la carta a colocar en la zona de batalla
	public static class CARTAPOS {
		public final static char NOSELECCION = 0;
		public final static char COLOCARENDEFENSA = 1;
		public final static char COLOCARENATAQUE = 3;
	}
	
	public Carta() {
		super();
		
	}
	
	public Carta(int pelemento,int pvalor,int puso){
		valor=pvalor;
		elemento=pelemento;
		uso=puso;
	}
	
	public Carta clone(){
		Carta clon=new Carta(this.elemento,this.valor,this.uso);
		return clon;
	}

	public static String devuelveUnicode(int n){
		switch(n){
			case 0: return "\u2665";
			case 1: return "\u2666";
			case 2: return "\u2663";
			case 3: return "\u2660";
		}
		return "";
	}
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getElemento() {
		return elemento;
	}

	public void setElemento(int elemento) {
		this.elemento = elemento;
	}
	
	public int getUsocarta() {
		return uso;
	}

	public void setUsocarta(int usocarta) {
		this.uso = usocarta;
	}

	public int getCartaPos(){
		return CartaPos;
	}

	public void setCartaPos(int pCartaPos){
		CartaPos=pCartaPos;
	}

}
