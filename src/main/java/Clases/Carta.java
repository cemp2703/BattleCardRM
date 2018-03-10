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
	
	public Carta(int pelemento,int pvalor){
		valor=pvalor;
		elemento=pelemento;
	}
	
	public Object clone() throws CloneNotSupportedException{
		Object clon=super.clone();
		return clon;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Carta)) return false;

		Carta carta = (Carta) o;

		if (valor != carta.valor) return false;
		return elemento == carta.elemento;
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

}
