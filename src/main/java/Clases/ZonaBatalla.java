package Clases;

public class ZonaBatalla implements Cloneable{
	
	public static final int MAXZONABATALLACARDS = 3;
	
	public VectorCartas Cartas;

	public boolean cartacolocada;

	public int poscarta[];
	public static class POSCARTA {
		public final static char NOHAYCARTA = 0;  // No hay carta
		public final static char ATAQUE = 1;  // Ataque
		public final static char DEFCARAARRIBA = 2;  // Defensa boca arriba
		public final static char DEFCARAABAJO = 3;  // Defensa boca abajo
	}

	public int dispataque[];
	public static class DISPATAQUE {
		public final static char NODISPONIBLE = 0;  // No Disponible para atacar con esta carta
		public final static char DISPONIBLE = 1;  // Disponible para atacar con esta carta
	}

	public int dispcambio[];
	public static class DISPCAMBIO {
		public final static char NODISPONIBLE = 0;  // No Disponible para cambiar de posicion
		public final static char DISPONIBLE = 1;  // Disponible para cambiar de posicion
	}
	
	public ZonaBatalla() {
		super();
		
		Cartas=new VectorCartas(MAXZONABATALLACARDS);
		poscarta=new int[MAXZONABATALLACARDS];
		dispataque=new int[MAXZONABATALLACARDS];
		dispcambio=new int[MAXZONABATALLACARDS];
		
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			poscarta[i]=POSCARTA.NOHAYCARTA;
			dispataque[i]=DISPATAQUE.NODISPONIBLE;
			dispcambio[i]=DISPCAMBIO.NODISPONIBLE;
		}
		cartacolocada=false;
	}
	
	public ZonaBatalla(VectorCartas pVectorCartas,int pposcarta[],int pdispataque[],int pdispcambio[],boolean pcartacolocada){
		this.Cartas=pVectorCartas;
		this.poscarta=pposcarta;
		this.dispataque=pdispataque;
		this.dispcambio=pdispcambio;
		this.cartacolocada=pcartacolocada;
	}

	public ZonaBatalla clone(){
		VectorCartas vc=this.Cartas.clone();
		int posc[]=new int[MAXZONABATALLACARDS];
		int dataque[]=new int[MAXZONABATALLACARDS];
		int dcambio[]=new int[MAXZONABATALLACARDS];
		
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			posc[i]=poscarta[i];
			dataque[i]=dispataque[i];
			dcambio[i]=dispcambio[i];
		}
		
		ZonaBatalla clon=new ZonaBatalla(vc,posc,dataque,dcambio,this.cartacolocada);
		return clon;
	}   

	public void renovarDisponibilidades(){
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			if(Cartas.obtenerCartaxId(i) != null){
				dispataque[i]=1;
				dispcambio[i]=1;
			}
		}
		cartacolocada=false;
	}

	public boolean isCartacolocada() {
		return cartacolocada;
	}

	public void setCartacolocada(boolean cartacolocada) {
		this.cartacolocada = cartacolocada;
	}
	
	public int getPosCartaxId(int id){
		if(id>=0 && id < MAXZONABATALLACARDS ){
			return poscarta[id];			
		}
		else
			return 0;
	}

	public static String devuelvedispataque(int dispataque){
		switch(dispataque){
			case 0: return "N";
			case 1: return "Y";
		}
		return "";
	}

	public static String devuelvedispcambio(int dispcambio){
		switch(dispcambio){
			case 0: return "N";
			case 1: return "Y";
		}
		return "";
	}

	public static String devuelveposcarta(int poscarta){
		switch(poscarta){
			case 0: return "VACIO";
			case 1: return "ATQ";
			case 2: return "DBA"; //defensa boca abajo
			case 3: return "DBV"; //defensa boca arriba
		}
		return "";
	}
}


