package Clases;

import java.util.Stack;

public class Deck implements Cloneable {
	public static final int MAXDECK = 52;
	
	public Stack<Carta> Deck;

	public Deck(){
		Deck=new Stack<Carta>();
	}

	@SuppressWarnings (value="unchecked")
	public Object clone() throws CloneNotSupportedException{
		Deck clon= (Deck) super.clone();
		clon.Deck = (Stack<Carta>) Deck.clone();
		return clon;
	}

	public Carta sacarUnaCarta(){
		Carta r;
		if(!Deck.isEmpty()){	
			r= Deck.peek();
		    Deck.pop();
		    return r;
		}
		else
			return null;
	}
	
	public int obtenerNumeroElementos(){
		return Deck.size();
	}
	
	public boolean agregarUnaCarta(Carta c){
		if(Deck.size() < MAXDECK && c!= null) {
			Deck.add(c);
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Deck)) return false;

		Deck deck = (Deck) o;

		return Deck != null ? Deck.equals(deck.Deck) : deck.Deck == null;
	}

}

