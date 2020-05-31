package com.xsrsys.model;

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

	public int accionIniciarTurno(){
		NTurnos++;
		ZBatalla.renovarPosibilidades();
		return accionCogerUnaCartaDelDeck();
	}
	
	public static class RESULTADOCOJERUNACARTA{
		public static final int MANOLLENA = -1; // No se puede cojer una carta porque la mano esta llena
		public static final int DECKVACIO = -2; // El deck esta vacio
		public static final int EXITO = 0; //Se cojio una carta
	}
	public int accionCogerUnaCartaDelDeck(){
		if(Mano.obtenerNumerodeCartas() >= Mano.getMaxNCartas())
			return RESULTADOCOJERUNACARTA.MANOLLENA;

		Carta ncarta= Deck.sacarUnaCarta();
		if(ncarta == null )
			return RESULTADOCOJERUNACARTA.DECKVACIO;
		else{
			Mano.agregarCartaEnEspacioVacio(ncarta);
			return RESULTADOCOJERUNACARTA.EXITO;
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
	public boolean accionColocarCarta(int idCartaZB,int idCartaMano,int posCarta){
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
		if(ZBatalla.posbatalla[idCartaAtacante] != ZonaBatalla.POSBATALLA.ATAQUE)
			return false;
		if(ZBatalla.dispataque[idCartaAtacante] != ZonaBatalla.DISPATAQUE.DISPONIBLE)
			return false;

		return true;
	}
	public static class RESULTADOATACARBARRERA{
		public static final int NOSECUMPLENCOND = -1; // No se cumplen las condiciones de ataque
		public static final int EXITO = 0; //Se atacó a la barrera
		public static final int SINBARRERAS = -2; //Termina el juego porque enemigo se quedo sin cartas de barrera (Termino 2)
	}
	public int accionAtacarBarrera(Jugador JugadorAtacado,int idCartaAtacante){
		int respuesta=RESULTADOATACARBARRERA.NOSECUMPLENCOND;
		if( posibilidadAtacarBarrera(JugadorAtacado,idCartaAtacante) ){
			JugadorAtacado.Barrera.quitarUltimaCartaDisponible();
			this.ZBatalla.nAtaquesDisponibles--;
			if(JugadorAtacado.Barrera.obtenerNumerodeCartas() >  0){
				respuesta=RESULTADOATACARBARRERA.EXITO;
			}
			else{
				respuesta= RESULTADOATACARBARRERA.SINBARRERAS;
			}
            this.ZBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
            this.ZBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
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
		if(ZBatalla.posbatalla[idCartaAtacante] != ZonaBatalla.POSBATALLA.ATAQUE)
			return false;
		if(ZBatalla.dispataque[idCartaAtacante] != ZonaBatalla.DISPATAQUE.DISPONIBLE)
			return false;

		return true;
	}
	public static class RESULTADOCARTA{
		public static final int UP = 0; // Carta activa
		public static final int DOWN = -1; // Carta destruida
	}
	public static class RESULTADOATACARCARTA{
		public static final int NOSECUMPLENCOND = -1; //-1: No se cumplen las condiciones de ataque
		public static final int EMPATE = 0;
		public static final int GANAATACANTE = 1; //Gana Atacante
		public static final int PIERDEATACANTE = 2; //Pierde Atacante
		public static final int ENEMIGOSINBARRERA = 3; //Termina el juego porque enemigo se quedo sin cartas de barrera (Termino 2)
	}
	public ResultadoAtaque accionAtacarCarta(Jugador JugadorAtacado, int idCartaAtacada, int idCartaAtacante){//Sistema de produccion
		ResultadoAtaque rs = new ResultadoAtaque();
		rs.resultado = RESULTADOATACARCARTA.NOSECUMPLENCOND;
		Carta cartaAtacante = null;
		Carta cartaAtacada = null;

		if( posibilidadAtacarCarta(JugadorAtacado,idCartaAtacada,idCartaAtacante) ){

			//Jugador Atacado en defensa cara abajo, se revela la carta.
			if(JugadorAtacado.ZBatalla.posbatalla[idCartaAtacada] == ZonaBatalla.POSBATALLA.DEFCARAABAJO){
				JugadorAtacado.ZBatalla.posbatalla[idCartaAtacada] = ZonaBatalla.POSBATALLA.DEFCARAARRIBA;
			}

			cartaAtacante = this.ZBatalla.obtenerCartaxId(idCartaAtacante);
			cartaAtacada = JugadorAtacado.ZBatalla.obtenerCartaxId(idCartaAtacada);
			rs.valorCartaAtacante = cartaAtacante.getValor();
			rs.elementoCartaAtacante = cartaAtacante.getElemento();
			rs.valorCartaAtacada = cartaAtacada.getValor();
			rs.elementoCartaAtacada = cartaAtacada.getElemento();

			if(rs.valorCartaAtacante > rs.valorCartaAtacada ){
				rs.resultado = RESULTADOATACARCARTA.GANAATACANTE;//gana
				rs.cartaAtacante = RESULTADOCARTA.UP;
				rs.cartaAtacada = RESULTADOCARTA.DOWN;
			}
			else if(rs.valorCartaAtacante  < rs.valorCartaAtacada){
				rs.resultado = RESULTADOATACARCARTA.PIERDEATACANTE;//pierde
				rs.cartaAtacante = RESULTADOCARTA.DOWN;
				rs.cartaAtacada = RESULTADOCARTA.UP;
			}
			else{
				rs.resultado = RESULTADOATACARCARTA.EMPATE;//empata
			}

			//Jugador Atacado al Ataque
			if(JugadorAtacado.ZBatalla.posbatalla[idCartaAtacada] == ZonaBatalla.POSBATALLA.ATAQUE){
				rs.posicionCartaAtacada = ZonaBatalla.POSBATALLA.ATAQUE;
				if(rs.resultado == RESULTADOATACARCARTA.GANAATACANTE){
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
					rs.idbarrera = JugadorAtacado.Barrera.quitarUltimaCartaDisponible();
					rs.barrera = RESULTADOCARTA.DOWN;
					if(JugadorAtacado.Barrera.obtenerNumerodeCartas() == 0){
						rs.resultado = RESULTADOATACARCARTA.ENEMIGOSINBARRERA;
					}
				}
				else if(rs.resultado == RESULTADOATACARCARTA.PIERDEATACANTE){//pierde atacante
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
				}
				else{//Empate
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
					rs.cartaAtacante = RESULTADOCARTA.DOWN;
					rs.cartaAtacada = RESULTADOCARTA.DOWN;
				}
			}
			else if(JugadorAtacado.ZBatalla.posbatalla[idCartaAtacada] == ZonaBatalla.POSBATALLA.DEFCARAARRIBA ){//Jugador Atacado a la Defensa
				rs.posicionCartaAtacada = ZonaBatalla.POSBATALLA.DEFCARAARRIBA;
				if(rs.resultado == RESULTADOATACARCARTA.GANAATACANTE){//gana atacante
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
				}
				else if(rs.resultado == RESULTADOATACARCARTA.PIERDEATACANTE){//pierde atacante
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
				}
				else{//Empate
					rs.cartaAtacante = RESULTADOCARTA.UP;
					rs.cartaAtacada = RESULTADOCARTA.UP;
				}
			}
			
			this.ZBatalla.nAtaquesDisponibles--;

			this.ZBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
            this.ZBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
		}
		return rs;
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
