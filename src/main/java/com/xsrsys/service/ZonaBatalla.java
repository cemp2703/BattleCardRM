package com.xsrsys.model;

import java.util.Arrays;

public class ZonaBatalla extends VectorCartas implements Cloneable{
	
	public static final int MAXZONABATALLACARDS = 3;

	public boolean cartaColocada=false;
	public int nAtaquesDisponibles = 0;
	public int nCambiosPosicionesDisponibles = 0;

	public PosBatalla posBatalla[];//POSICION DE BATALLA
	public enum PosBatalla{
		NOHAYCARTA, // No hay carta
		ATAQUE, // Ataque
		DEFCARAARRIBA, // Defensa boca arriba
		DEFCARAABAJO // Defensa boca abajo
	}
	
	public DispAtaque dispAtaque[];
	public enum DispAtaque{
		NODISPONIBLE, // No Disponible para atacar con esta carta
		DISPONIBLE // Disponible para atacar con esta carta
	}

	public DispCambio dispCambio[];
	public enum DispCambio{
		NODISPONIBLE, // No Disponible para cambiar de posicion
		DISPONIBLE // Disponible para cambiar de posicion
	}

	public ZonaBatalla() {
		super(MAXZONABATALLACARDS);
		posBatalla=new PosBatalla[MAXZONABATALLACARDS];
		dispAtaque=new DispAtaque[MAXZONABATALLACARDS];
		dispCambio=new DispCambio[MAXZONABATALLACARDS];

		for(int i=0;i<MAXZONABATALLACARDS;i++){
			posBatalla[i]=PosBatalla.NOHAYCARTA;
			dispAtaque[i]=DispAtaque.NODISPONIBLE;
			dispCambio[i]=DispCambio.NODISPONIBLE;
		}
		cartaColocada=false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ZonaBatalla)) return false;
		ZonaBatalla that = (ZonaBatalla) o;
		if ( cartaColocada != that.cartaColocada ) return false;
		if ( nAtaquesDisponibles!=that.nAtaquesDisponibles ) return false;
		if ( nCambiosPosicionesDisponibles != that.nCambiosPosicionesDisponibles ) return false;
		if ( !Arrays.equals(posBatalla, that.posBatalla )) return false;
		if ( !Arrays.equals(dispAtaque, that.dispAtaque )) return false;
		if ( !Arrays.equals(dispCambio, that.dispCambio )) return false;
		return super.equals(o);
	}

	public Object clone() throws CloneNotSupportedException{
		ZonaBatalla clon=(ZonaBatalla) super.clone();

		PosBatalla pBatalla[]=new PosBatalla[MAXZONABATALLACARDS];
		DispAtaque dAtaque[]=new DispAtaque[MAXZONABATALLACARDS];
		DispCambio dCambio[]=new DispCambio[MAXZONABATALLACARDS];

		for(int i=0;i<MAXZONABATALLACARDS;i++){
			pBatalla[i]=posBatalla[i];
			dAtaque[i]=dispAtaque[i];
			dCambio[i]=dispCambio[i];
		}

		clon.dispCambio=dCambio;
		clon.posBatalla =pBatalla;
		clon.dispAtaque=dAtaque;
		return clon;
	}

	public void renovarPosibilidades(){
		nAtaquesDisponibles=0;
		nCambiosPosicionesDisponibles=0;
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			if(this.obtenerCartaxId(i) != null){
				if(posBatalla[i] == PosBatalla.ATAQUE) {
					dispAtaque[i] = ZonaBatalla.DispAtaque.DISPONIBLE;
					dispCambio[i] = DispCambio.DISPONIBLE;
				}
				else {
					dispAtaque[i] = ZonaBatalla.DispAtaque.NODISPONIBLE;
					dispCambio[i] = DispCambio.DISPONIBLE;
				}
				nAtaquesDisponibles++;
				nCambiosPosicionesDisponibles++;
			}
		}
		cartaColocada=false;
	}

	public PosBatalla getPosBatallaxId(int id){
		if(id>=0 && id < MAXZONABATALLACARDS )
			return posBatalla[id];
		else
			return PosBatalla.NOHAYCARTA;
	}

	public boolean agregarCartaEnPos(Carta pCarta,int idCartaZB,PosBatalla posCarta ){
		boolean respuesta = false;
		if(super.agregarCartaEnPos(pCarta, idCartaZB) ){
			posBatalla[idCartaZB] = posCarta;
			cartaColocada=true;
			if(posCarta == PosBatalla.ATAQUE) {
				dispAtaque[idCartaZB] = ZonaBatalla.DispAtaque.DISPONIBLE;
				dispCambio[idCartaZB] = DispCambio.NODISPONIBLE;
				nAtaquesDisponibles++;
			}
			else {
				dispAtaque[idCartaZB] = ZonaBatalla.DispAtaque.NODISPONIBLE;
				dispCambio[idCartaZB] = DispCambio.NODISPONIBLE;
			}
			respuesta=true;
		}
		return respuesta;
	}

	public int agregarCartaEnEspacioVacio(Carta pCarta, PosBatalla posCarta){
		for(int i=0;i< this.getMaxNCartas();i++){
			if(agregarCartaEnPos(pCarta,i,posCarta))
				return i;
		}
		return NOSEPUEDEAGREGARCARTAS;
	}

	@Override
	public boolean quitarCartaenPos(int id){
		dispAtaque[id] = DispAtaque.NODISPONIBLE;
		dispCambio[id] = DispCambio.NODISPONIBLE;
		posBatalla[id] = PosBatalla.NOHAYCARTA;
		return super.quitarCartaenPos(id);
	}
	
	public boolean puedeCambiarPosicion(){
		if( this.obtenerNumerodeCartas() == 0)
			return false;
		if(nCambiosPosicionesDisponibles == 0)
			return false;
		if(nAtaquesDisponibles == 0)
			return false;
		return true;
	}
	public boolean posibilidadCambiarPosicionBatallaEnCarta(int idCartaZBJAct){
		if(! puedeCambiarPosicion())
			return false;
		if(obtenerCartaxId(idCartaZBJAct) == null)
			return false;
		if(dispCambio[idCartaZBJAct] == DispCambio.NODISPONIBLE)
			return false;
		return true;
	}
	public boolean cambiarPosicionBatalla(int idCartaZBJAct){
		boolean respuesta = false;
		if(posibilidadCambiarPosicionBatallaEnCarta(idCartaZBJAct)){
			if( posBatalla[idCartaZBJAct] == PosBatalla.DEFCARAARRIBA ||
					posBatalla[idCartaZBJAct] == PosBatalla.DEFCARAABAJO){
				posBatalla[idCartaZBJAct] = PosBatalla.ATAQUE;
				dispCambio[idCartaZBJAct] = ZonaBatalla.DispCambio.NODISPONIBLE;
				dispAtaque[idCartaZBJAct] = ZonaBatalla.DispAtaque.DISPONIBLE;
				nCambiosPosicionesDisponibles--;
				respuesta = true;
			}
			else if(posBatalla[idCartaZBJAct] == PosBatalla.ATAQUE){
				posBatalla[idCartaZBJAct] = PosBatalla.DEFCARAARRIBA;
				dispCambio[idCartaZBJAct] = ZonaBatalla.DispCambio.NODISPONIBLE;
				dispAtaque[idCartaZBJAct] = ZonaBatalla.DispAtaque.NODISPONIBLE;
				nCambiosPosicionesDisponibles--;
				respuesta = true;
			}
		}
		return respuesta;
	}


}


