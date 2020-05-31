package com.xsrsys.model;

import java.util.Arrays;

public class ZonaBatalla extends VectorCartas implements Cloneable{
	
	public static final int MAXZONABATALLACARDS = 3;

	public boolean cartaColocada=false;
	public int nAtaquesDisponibles = 0;
	public int nCambiosPosicionesDisponibles = 0;

	public int posbatalla[];//POSICION DE BATALLA
	public static class POSBATALLA {
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
		posbatalla =new int[MAXZONABATALLACARDS];
		dispataque=new int[MAXZONABATALLACARDS];
		dispcambio=new int[MAXZONABATALLACARDS];

		for(int i=0;i<MAXZONABATALLACARDS;i++){
			posbatalla[i]= POSBATALLA.NOHAYCARTA;
			dispataque[i]=DISPATAQUE.NODISPONIBLE;
			dispcambio[i]=DISPCAMBIO.NODISPONIBLE;
		}
		cartaColocada=false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ZonaBatalla)) return false;
		if (!super.equals(o)) return false;

		ZonaBatalla that = (ZonaBatalla) o;

		if (cartaColocada != that.cartaColocada) return false;
		if (!Arrays.equals(posbatalla, that.posbatalla)) return false;
		if (!Arrays.equals(dispataque, that.dispataque)) return false;
		return Arrays.equals(dispcambio, that.dispcambio);
	}

	public Object clone() throws CloneNotSupportedException{
		ZonaBatalla clon=(ZonaBatalla) super.clone();

		int posc[]=new int[MAXZONABATALLACARDS];
		int dataque[]=new int[MAXZONABATALLACARDS];
		int dcambio[]=new int[MAXZONABATALLACARDS];

		for(int i=0;i<MAXZONABATALLACARDS;i++){
			posc[i]= posbatalla[i];
			dataque[i]=dispataque[i];
			dcambio[i]=dispcambio[i];
		}

		clon.dispcambio=dcambio;
		clon.posbatalla =posc;
		clon.dispataque=dataque;
		return clon;
	}

	public void renovarPosibilidades(){
		nAtaquesDisponibles=0;
		nCambiosPosicionesDisponibles=0;
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			if(this.obtenerCartaxId(i) != null){
				if(posbatalla[i] == POSBATALLA.ATAQUE) {
					dispataque[i] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
					dispcambio[i] = DISPCAMBIO.DISPONIBLE;
				}
				else {
					dispataque[i] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					dispcambio[i] = DISPCAMBIO.DISPONIBLE;
				}
				nAtaquesDisponibles++;
				nCambiosPosicionesDisponibles++;
			}
		}
		cartaColocada=false;
	}

	public int getPosBatallaxId(int id){
		if(id>=0 && id < MAXZONABATALLACARDS ){
			return posbatalla[id];
		}
		else
			return POSBATALLA.NOHAYCARTA;
	}

	public boolean agregarCartaEnPos(Carta pCarta,int idCartaZB,int posCarta ){
		boolean respuesta = false;
		if(super.agregarCartaEnPos(pCarta, idCartaZB) ){
			posbatalla[idCartaZB] = posCarta;
			cartaColocada=true;
			if(posCarta == POSBATALLA.ATAQUE) {
				dispataque[idCartaZB] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
				dispcambio[idCartaZB] = DISPCAMBIO.NODISPONIBLE;
				nAtaquesDisponibles++;
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

	@Override
	public boolean quitarCartaenPos(int id){
		dispataque[id] = DISPATAQUE.NODISPONIBLE;
		dispcambio[id] = DISPCAMBIO.NODISPONIBLE;
		posbatalla[id] = POSBATALLA.NOHAYCARTA;
		return super.quitarCartaenPos(id);
	}
	
	public boolean puedeCambiarPosicion(){
		if( this.obtenerNumerodeCartas() == 0)
			return false;
		if(nCambiosPosicionesDisponibles == 0)
			return false;
		return true;
	}
	public boolean posibilidadCambiarPosicionBatallaEnCarta(int idCartaZBJAct){
		if(! puedeCambiarPosicion())
			return false;
		if(obtenerCartaxId(idCartaZBJAct) == null)
			return false;
		if(dispcambio[idCartaZBJAct] == DISPCAMBIO.NODISPONIBLE)
			return false;
		return true;
	}
	public boolean cambiarPosicionBatalla(int idCartaZBJAct){
		boolean respuesta = false;
		if(posibilidadCambiarPosicionBatallaEnCarta(idCartaZBJAct)){
			if( posbatalla[idCartaZBJAct] == POSBATALLA.DEFCARAARRIBA ||
					posbatalla[idCartaZBJAct] == POSBATALLA.DEFCARAABAJO){
				posbatalla[idCartaZBJAct] = POSBATALLA.ATAQUE;
				dispcambio[idCartaZBJAct] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
				dispataque[idCartaZBJAct] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
				respuesta = true;
			}
			else if(posbatalla[idCartaZBJAct] == POSBATALLA.ATAQUE){
				posbatalla[idCartaZBJAct] = POSBATALLA.DEFCARAARRIBA;
				dispcambio[idCartaZBJAct] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
				dispataque[idCartaZBJAct] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
				respuesta = true;
			}
		}
		return respuesta;
	}


}


