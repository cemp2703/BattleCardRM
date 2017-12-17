public class Jugador implements Cloneable{
	private String Nombre;
	private int NTurnos;
	public Deck Deck;
	public Mano Mano;
	public Barrera Barrera;
	public ZonaBatalla ZonaBatalla;
	
	public Jugador(String pNombre,int pnturnos,Deck pDeck,Mano pMano,Barrera pBarrera, ZonaBatalla pZonaBatalla){
		NTurnos=pnturnos;
		Deck=pDeck;
		Mano=pMano;
		Barrera=pBarrera;
		ZonaBatalla=pZonaBatalla;
		Nombre=pNombre;
	}
	
	public Jugador clone(){
		Deck D=this.Deck.clone();
		Mano M=this.Mano.clone();
		Barrera B=this.Barrera.clone();
		ZonaBatalla ZB=this.ZonaBatalla.clone();
		Jugador clon=new Jugador(this.Nombre,this.NTurnos,D,M,B,ZB);
		return clon;
	}
	
	public Jugador(String pNombre){
		Nombre=pNombre;
		Deck=new Deck();
		Mano=new Mano();
		Barrera=new Barrera();
		ZonaBatalla=new ZonaBatalla();
		NTurnos=0;
	}
	
	void contarTurno(){
		NTurnos++;
	}

	public int getNTurnos() {
		return NTurnos;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	

}
