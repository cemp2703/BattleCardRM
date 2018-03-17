package Clases;

import java.util.Arrays;

public class ZonaBatalla extends VectorCartas implements Cloneable{
	
	public static final int MAXZONABATALLACARDS = 3;

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
		super(MAXZONABATALLACARDS);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ZonaBatalla)) return false;
		if (!super.equals(o)) return false;

		ZonaBatalla that = (ZonaBatalla) o;

		if (cartacolocada != that.cartacolocada) return false;
		if (!Arrays.equals(poscarta, that.poscarta)) return false;
		if (!Arrays.equals(dispataque, that.dispataque)) return false;
		return Arrays.equals(dispcambio, that.dispcambio);
	}


	public Object clone() throws CloneNotSupportedException{
		ZonaBatalla clon=(ZonaBatalla) super.clone();

		int posc[]=new int[MAXZONABATALLACARDS];
		int dataque[]=new int[MAXZONABATALLACARDS];
		int dcambio[]=new int[MAXZONABATALLACARDS];

		for(int i=0;i<MAXZONABATALLACARDS;i++){
			posc[i]=poscarta[i];
			dataque[i]=dispataque[i];
			dcambio[i]=dispcambio[i];
		}

		clon.dispcambio=dcambio;
		clon.poscarta=posc;
		clon.dispataque=dataque;
		return clon;
	}

	public void renovarPosibilidades(){
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			if(this.obtenerCartaxId(i) != null){
				if(poscarta[i] == POSCARTA.ATAQUE) {
					dispataque[i] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
					dispcambio[i] = DISPCAMBIO.DISPONIBLE;
				}
				else {
					dispataque[i] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					dispcambio[i] = DISPCAMBIO.DISPONIBLE;
				}
			}
		}
		cartacolocada=false;
	}

	
	public int getPosCartaxId(int id){
		if(id>=0 && id < MAXZONABATALLACARDS ){
			return poscarta[id];			
		}
		else
			return POSCARTA.NOHAYCARTA;
	}

	public boolean agregarCartaEnPos(Carta pCarta,int idCartaZB,int posCarta ){
		boolean respuesta = false;
		if(super.agregarCartaEnPos(pCarta, idCartaZB) ){
			poscarta[idCartaZB] = posCarta;
			setCartacolocada(true);
			if(posCarta == POSCARTA.ATAQUE) {
				dispataque[idCartaZB] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
				dispcambio[idCartaZB] = DISPCAMBIO.NODISPONIBLE;
			}
			else {
				dispataque[idCartaZB] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
				dispcambio[idCartaZB] = DISPCAMBIO.NODISPONIBLE;
			}
			respuesta=true;
		}
		return respuesta;
	}


	public int agregarCartaEnEspacioVacio(Carta pCarta, int posCarta){
		for(int i=0;i< this.getMaxNCartas();i++){
			if(agregarCartaEnPos(pCarta,i,i))
				return i;
		}
		return NOSEPUEDEAGREGARCARTAS;
	}

	public boolean posibilidadCambiarPosicionBatalla(int idCartaZBJAct){
		if(obtenerCartaxId(idCartaZBJAct) == null)
			return false;
		if(dispcambio[idCartaZBJAct] == DISPCAMBIO.NODISPONIBLE)
			return false;
		return true;
	}

	public boolean cambiarPosicionBatalla(int idCartaZBJAct){
		boolean respuesta = false;
		if(posibilidadCambiarPosicionBatalla(idCartaZBJAct)){
			if( poscarta[idCartaZBJAct] == ZonaBatalla.POSCARTA.DEFCARAARRIBA ||
					poscarta[idCartaZBJAct] == ZonaBatalla.POSCARTA.DEFCARAABAJO){
				poscarta[idCartaZBJAct] = ZonaBatalla.POSCARTA.ATAQUE;
				dispcambio[idCartaZBJAct] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
				dispataque[idCartaZBJAct] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
				respuesta = true;
			}
			else if(poscarta[idCartaZBJAct] == ZonaBatalla.POSCARTA.ATAQUE){
				poscarta[idCartaZBJAct] = ZonaBatalla.POSCARTA.DEFCARAARRIBA;
				dispcambio[idCartaZBJAct] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
				dispataque[idCartaZBJAct] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
				respuesta = true;
			}
		}
		return respuesta;
	}

	@Override
	public boolean quitarCartaenPos(int id){
		dispataque[id] = DISPATAQUE.NODISPONIBLE;
		dispcambio[id] = DISPCAMBIO.NODISPONIBLE;
		poscarta[id] = POSCARTA.NOHAYCARTA;
		return super.quitarCartaenPos(id);
	}

	public static String devuelveposcarta(int poscarta){
		switch(poscarta){
			case 0: return "VACIO";
			case 1: return "ATQ";
			case 2: return "DBA"; //defensa boca abajo
			case 3: return "DBV"; //defensa boca arriba
			default: return "";
		}
	}

	public boolean isCartacolocada() {
		return cartacolocada;
	}

	public void setCartacolocada(boolean cartacolocada) {
		this.cartacolocada = cartacolocada;
	}
}


