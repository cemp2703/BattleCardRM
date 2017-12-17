import java.util.Stack;

public class Deck implements Cloneable {
	public static final int MAXDECK = 52;
	
	public Stack<Carta> Deck;

	public Deck(){
		Deck=new Stack<Carta>();
	}
	
	public Deck(Stack<Carta> pDeck){
		Deck=pDeck;
	}
	
	public Deck clone(){
		Stack<Carta> D;
		D=(Stack<Carta>) this.Deck.clone();
		Deck clon=new Deck(D);
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
		Deck.add(c);
		return true;
	}
	
}

