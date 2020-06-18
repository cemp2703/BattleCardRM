package com.xsrsys.service;

public class Carta implements Cloneable{

	private int valor;
	private Elemento elemento;
	
    public static final int MAXVALORCARTA = 13;
	
	public enum Elemento {
		CORAZON,// Unicode 2665
		COCO, // Unicode 2666
		TREBOL, // Unicode 2663
		ESPADA // Unicode 2660
    }
	
	public Carta(int pvalor,Elemento pelemento){
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
		if (!(o instanceof Carta)) 
			return false;
		Carta carta = (Carta) o;
		if (valor != carta.valor || elemento!=carta.elemento)  
			return false;
		return true;
	}

	public int getValor() {
		return valor;
	}

	public Elemento getElemento() {
		return elemento;
	}

	public static int getNumeroElementosCartas() {
		return Carta.Elemento.values().length;
	} 

	static String obtenerStringElementoUnicode(Elemento e){
        String elemento = "";
        switch(e){
            case CORAZON: elemento =  "\u2665"; break;
            case COCO: elemento = "\u2666"; break;
            case TREBOL: elemento = "\u2663"; break;
            case ESPADA: elemento = "\u2660"; break;
        }
        return elemento;
    }
}
