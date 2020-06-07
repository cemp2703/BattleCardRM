package com.xsrsys.model;

import com.xsrsys.model.ZonaBatalla.PosBatalla;

public class Jugador implements Cloneable{
	private String Nombre;
	private int NTurnos;
	public Deck Deck;
	public Mano Mano;
	public Barrera Barrera;
	public ZonaBatalla ZBatalla;

	public Jugador(String pNombre){
		Nombre=pNombre;
		Deck=new Deck();
		Mano=new Mano();
		Barrera=new Barrera();
		ZBatalla=new ZonaBatalla();
		NTurnos=0;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	//region Operaciones (Reglas)

	public void accionIniciarTurno(){
		NTurnos++;
		ZBatalla.renovarPosibilidades();
	}
	/*
	public static class RESULTADOCOJERUNACARTA{
		public static final int MANOLLENA = -1; // No se puede cojer una carta porque la mano esta llena
		public static final int DECKVACIO = -2; // El deck esta vacio
		public static final int EXITO = 0; //Se cojio una carta
	}
	*/
	public enum ResultadoCojerUnaCarta{
		MANOLLENA, // No se puede cojer una carta porque la mano esta llena
		DECKVACIO, // El deck esta vacio
		EXITO 
	}
	
	public ResultadoCojerUnaCarta accionCogerUnaCartaDelDeck(){
		if(Mano.obtenerNumerodeCartas() >= Mano.getMaxNCartas())
			return ResultadoCojerUnaCarta.MANOLLENA;

		Carta ncarta= Deck.sacarUnaCarta();
		if(ncarta == null )
			return ResultadoCojerUnaCarta.DECKVACIO;
		else{
			Mano.agregarCartaEnEspacioVacio(ncarta);
			return ResultadoCojerUnaCarta.EXITO;
		}
	}
	
	public boolean puedeColocarCartaEnZB(){
		return !ZBatalla.cartaColocada;
	}
	public boolean posibilidadColocarCartaEnPosicion(int idCartaZB,int idCartaMano) {
		if( ! puedeColocarCartaEnZB())
			return false;
		if(this.Mano.obtenerCartaxId(idCartaMano) == null)
			return false;
		if(this.ZBatalla.obtenerCartaxId(idCartaZB) != null)
			return false;
		return true;
	}
	public boolean accionColocarCarta(int idCartaZB,int idCartaMano,PosBatalla posCarta){
		boolean respuesta = false;
		if(posibilidadColocarCartaEnPosicion(idCartaZB,idCartaMano)){
			Carta c=this.Mano.obtenerCartaxId(idCartaMano);
			this.Mano.quitarCartaenPos(idCartaMano);
			this.ZBatalla.agregarCartaEnPos(c,idCartaZB,posCarta);
			respuesta=true;
		}
		return respuesta;
	}

	public boolean puedeAtacarBarreras( Jugador pJugadorAtacado){
		if(ZBatalla.obtenerNumerodeCartas() == 0)
			return false;
		if(pJugadorAtacado.ZBatalla.obtenerNumerodeCartas() > 0)
			return false;
		if(ZBatalla.nAtaquesDisponibles == 0)
			return false;
		if(NTurnos == 1)
			return false;
		return true;
	}
	public boolean posibilidadAtacarBarrera(Jugador JugadorAtacado,int idCartaAtacante) {
		if(!puedeAtacarBarreras(JugadorAtacado))
			return false;
		if (ZBatalla.obtenerCartaxId(idCartaAtacante) == null)
			return false;
		if(ZBatalla.posBatalla[idCartaAtacante] != ZonaBatalla.PosBatalla.ATAQUE)
			return false;
		if(ZBatalla.dispAtaque[idCartaAtacante] != ZonaBatalla.DispAtaque.DISPONIBLE)
			return false;

		return true;
	}

	public VeredictoAtaque accionAtacarBarrera(Jugador JugadorAtacado,int idCartaAtacante){
		VeredictoAtaque respuesta=VeredictoAtaque.NOSECUMPLENCOND;
		if( posibilidadAtacarBarrera(JugadorAtacado,idCartaAtacante) ){
			JugadorAtacado.Barrera.quitarUltimaCartaDisponible();
			this.ZBatalla.nAtaquesDisponibles--;
			if(JugadorAtacado.Barrera.obtenerNumerodeCartas() >  0){
				respuesta=VeredictoAtaque.BARRERADESTRUIDA;
			}
			else{
				respuesta= VeredictoAtaque.ENEMIGOSINBARRERA;
			}
            this.ZBatalla.dispAtaque[idCartaAtacante] = ZonaBatalla.DispAtaque.NODISPONIBLE;
            this.ZBatalla.dispCambio[idCartaAtacante] = ZonaBatalla.DispCambio.NODISPONIBLE;
		}
		return respuesta;
	}
	
	public boolean puedeAtacarCartas( Jugador pJugadorAtacado) {
		if(ZBatalla.obtenerNumerodeCartas() == 0) 
			return false;
		if(pJugadorAtacado.ZBatalla.obtenerNumerodeCartas() == 0) 
			return false;
		if(ZBatalla.nAtaquesDisponibles == 0)
			return false;
		if(NTurnos == 1)
			return false;
		return true;
	}
	public boolean posibilidadAtacarCarta(Jugador JugadorAtacado,int idCartaAtacada,int idCartaAtacante){
		if(!puedeAtacarCartas(JugadorAtacado))
			return false;
		if (ZBatalla.obtenerCartaxId(idCartaAtacante) == null)
			return false;
		if (JugadorAtacado.ZBatalla.obtenerCartaxId(idCartaAtacada) == null)
			return false;
		if(ZBatalla.posBatalla[idCartaAtacante] != ZonaBatalla.PosBatalla.ATAQUE)
			return false;
		if(ZBatalla.dispAtaque[idCartaAtacante] != ZonaBatalla.DispAtaque.DISPONIBLE)
			return false;

		return true;
	}
	/*
	public static class RESULTADOCARTA{
		public static final int UP = 0; // Carta activa
		public static final int DOWN = -1; 
	}*/
	public enum EstadoCarta{
		ACTIVA, // Carta activa
		DESTRUIDA // Carta destruida
	}
	/*
	public static class RESULTADOATACARCARTA{
		public static final int NOSECUMPLENCOND = -1; //-1: No se cumplen las condiciones de ataque
		public static final int EMPATE = 0;
		public static final int GANAATACANTE = 1; //Gana Atacante
		public static final int PIERDEATACANTE = 2; //Pierde Atacante
		public static final int ENEMIGOSINBARRERA = 3; //Termina el juego porque enemigo se quedo sin cartas de barrera (Termino 2)
	}
	*/
	public enum VeredictoAtaque{
		NOSECUMPLENCOND, //-1: No se cumplen las condiciones de ataque
		EMPATE, 
		GANAATACANTE,  //Gana Atacante contra carta en Zona de Batalla
		PIERDEATACANTE, //Pierde Atacante
		BARRERADESTRUIDA, //Destruye una barrera
		ENEMIGOSINBARRERA //Termina el juego porque enemigo se quedo sin cartas de barrera (Termino 2)
	}
	public ResultadoAtaque accionAtacarCarta(Jugador JugadorAtacado, int idCartaAtacada, int idCartaAtacante){//Sistema de produccion
		ResultadoAtaque rsAtaque = new ResultadoAtaque();
		rsAtaque.veredicto = VeredictoAtaque.NOSECUMPLENCOND;
		rsAtaque.estadoBarrera = EstadoCarta.ACTIVA;
		Carta cartaAtacante = null;
		Carta cartaAtacada = null;

		if( posibilidadAtacarCarta(JugadorAtacado,idCartaAtacada,idCartaAtacante) ){

			//Jugador Atacado en defensa cara abajo, se revela la carta.
			if(JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] == ZonaBatalla.PosBatalla.DEFCARAABAJO){
				JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] = ZonaBatalla.PosBatalla.DEFCARAARRIBA;
			}

			cartaAtacante = this.ZBatalla.obtenerCartaxId(idCartaAtacante);
			cartaAtacada = JugadorAtacado.ZBatalla.obtenerCartaxId(idCartaAtacada);
			rsAtaque.cartaAtacante = cartaAtacante;
			rsAtaque.cartaAtacada = cartaAtacada;

			if(rsAtaque.cartaAtacante.getValor() > rsAtaque.cartaAtacada.getValor() ){
				rsAtaque.veredicto = VeredictoAtaque.GANAATACANTE;//gana
				rsAtaque.estadoCartaAtacante = EstadoCarta.ACTIVA;
				rsAtaque.estadoCartaAtacada = EstadoCarta.DESTRUIDA;
			}
			else if(rsAtaque.cartaAtacante.getValor()  < rsAtaque.cartaAtacada.getValor()){
				rsAtaque.veredicto = VeredictoAtaque.PIERDEATACANTE;//pierde
				rsAtaque.estadoCartaAtacante = EstadoCarta.DESTRUIDA;
				rsAtaque.estadoCartaAtacada = EstadoCarta.ACTIVA;
			}
			else{
				rsAtaque.veredicto = VeredictoAtaque.EMPATE;//empata
			}

			//Jugador Atacado al Ataque
			if(JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] == ZonaBatalla.PosBatalla.ATAQUE){
				rsAtaque.posicionCartaAtacada = ZonaBatalla.PosBatalla.ATAQUE;
				if(rsAtaque.veredicto == VeredictoAtaque.GANAATACANTE){
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
					JugadorAtacado.Barrera.quitarUltimaCartaDisponible();
					rsAtaque.estadoBarrera = EstadoCarta.DESTRUIDA;
					if(JugadorAtacado.Barrera.obtenerNumerodeCartas() == 0){
						rsAtaque.veredicto = VeredictoAtaque.ENEMIGOSINBARRERA;
					}
				}
				else if(rsAtaque.veredicto == VeredictoAtaque.PIERDEATACANTE){//pierde atacante
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
				}
				else{//Empate
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
					rsAtaque.estadoCartaAtacante = EstadoCarta.DESTRUIDA;
					rsAtaque.estadoCartaAtacada = EstadoCarta.DESTRUIDA;
				}
			}
			else if(JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] == ZonaBatalla.PosBatalla.DEFCARAARRIBA ){//Jugador Atacado a la Defensa
				rsAtaque.posicionCartaAtacada = ZonaBatalla.PosBatalla.DEFCARAARRIBA;
				if(rsAtaque.veredicto == VeredictoAtaque.GANAATACANTE){//gana atacante
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
				}
				else if(rsAtaque.veredicto == VeredictoAtaque.PIERDEATACANTE){//pierde atacante
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
				}
				else{//Empate
					rsAtaque.estadoCartaAtacante = EstadoCarta.ACTIVA;
					rsAtaque.estadoCartaAtacada = EstadoCarta.ACTIVA;
				}
			}
			
			this.ZBatalla.nAtaquesDisponibles--;

			this.ZBatalla.dispAtaque[idCartaAtacante] = ZonaBatalla.DispAtaque.NODISPONIBLE;
            this.ZBatalla.dispCambio[idCartaAtacante] = ZonaBatalla.DispCambio.NODISPONIBLE;
		}
		return rsAtaque;
	}
	
	public boolean puedeCambiarPosicion(){		
		return ZBatalla.puedeCambiarPosicion();
	}
	public boolean posibilidadCambiarPosicionBatallaEnCarta(int idCartaZBJAct) {
		return ZBatalla.posibilidadCambiarPosicionBatallaEnCarta(idCartaZBJAct);
	}
	public boolean accionCambiarPosicionBatalla(int idCartaZBJAct){
		return ZBatalla.cambiarPosicionBatalla(idCartaZBJAct);
	}

	//endregion


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Jugador)) return false;

		Jugador jugador = (Jugador) o;

		if (NTurnos != jugador.NTurnos) return false;
		if (Nombre != null ? !Nombre.equals(jugador.Nombre) : jugador.Nombre != null) return false;
		if (Deck != null ? !Deck.equals(jugador.Deck) : jugador.Deck != null) return false;
		if (Mano != null ? !Mano.equals(jugador.Mano) : jugador.Mano != null) return false;
		if (Barrera != null ? !Barrera.equals(jugador.Barrera) : jugador.Barrera != null) return false;
		return ZBatalla != null ? ZBatalla.equals(jugador.ZBatalla) : jugador.ZBatalla == null;
	}
	public Object clone() throws CloneNotSupportedException{
		Jugador clon= (Jugador) super.clone();
		clon.Deck=Deck;
		clon.Mano=Mano;
		clon.Barrera=Barrera;
		clon.ZBatalla=ZBatalla;
		clon.Nombre=Nombre;
		return clon;
	}
}
