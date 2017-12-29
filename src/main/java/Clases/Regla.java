package Clases;

public class Regla {

	public boolean accionColocarCarta(Jugador JugadorActual,int idCartaZB,int idCartaMano,int posCarta){
		boolean respuesta = false;
		if(JugadorActual.Mano.obtenerCartaxId(idCartaMano) != null &&
				JugadorActual.ZonaBatalla.obtenerCartaxId(idCartaZB) == null){
			JugadorActual.ZonaBatalla.poscarta[idCartaZB] = posCarta;
			JugadorActual.ZonaBatalla.agregarCartaEnPos(JugadorActual.Mano.obtenerCartaxId(idCartaMano), idCartaZB);
			JugadorActual.Mano.quitarCartaenPos(idCartaMano);
			JugadorActual.ZonaBatalla.setCartacolocada(true);
			JugadorActual.ZonaBatalla.dispcambio[idCartaZB] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
			if(posCarta == ZonaBatalla.POSCARTA.DEFCARAABAJO)
				JugadorActual.ZonaBatalla.dispataque[idCartaZB] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			else
				JugadorActual.ZonaBatalla.dispataque[idCartaZB] = ZonaBatalla.DISPATAQUE.DISPONIBLE;
			respuesta=true;
		}
		return respuesta;
	}

	public boolean accionCambiarPosicionBatalla(Jugador JugadorActual,int idCartaZBJAct){
		boolean respuesta = false;
		if(JugadorActual.ZonaBatalla.obtenerCartaxId(idCartaZBJAct) != null
				&& JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct] == ZonaBatalla.DISPCAMBIO.DISPONIBLE ){
				if( JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct] == ZonaBatalla.POSCARTA.DEFCARAARRIBA ||
						JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct] == ZonaBatalla.POSCARTA.DEFCARAABAJO){
					JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct] = ZonaBatalla.POSCARTA.ATAQUE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					respuesta = true;
				}
				else if(JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct] == ZonaBatalla.POSCARTA.ATAQUE){
					JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct] = ZonaBatalla.POSCARTA.DEFCARAARRIBA;
					JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispataque[idCartaZBJAct] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					respuesta = true;
				}
		}
		return respuesta;
	}

	public static class RESULTADOATACARCARTA{
		public static final int NOSECUMPLENCOND = -1; //-1: No se cumplen las condiciones de ataque
		public static final int EMPATE = 0;
		public static final int GANAATACANTE = 1; //Gana Atacante
		public static final int PIERDEATACANTE = 2; //Pierde Atacante
		public static final int ENEMIGOSINCARTAS = 3; //Termina el juego porque enemigo se quedo sin cartas (Termino 2)
	}

	public int accionAtacarCarta(Jugador JugadorActual, Jugador JugadorAnterior,int idCartaAtacada,int idCartaAtacante){//Sistema de produccion
		int resultado = RESULTADOATACARCARTA.NOSECUMPLENCOND;
		int valorJugadorActual,valorJugadorAnterior;
		if(JugadorActual.ZonaBatalla.obtenerCartaxId(idCartaAtacante) != null &&
				JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] == ZonaBatalla.POSCARTA.ATAQUE &&
				JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] == ZonaBatalla.DISPATAQUE.DISPONIBLE &&
				JugadorActual.getNTurnos() > 1 &&
				JugadorAnterior.ZonaBatalla.obtenerCartaxId(idCartaAtacada) != null){

			//Jugador Atacado en defenza cara abajo, se revela la carta.
			if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] == ZonaBatalla.POSCARTA.DEFCARAABAJO){
				JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.DEFCARAARRIBA;
			}
			
			valorJugadorActual = JugadorActual.ZonaBatalla.obtenerCartaxId(idCartaAtacante).getValor();
			valorJugadorAnterior = JugadorAnterior.ZonaBatalla.obtenerCartaxId(idCartaAtacada).getValor();
			
			if(valorJugadorActual > valorJugadorAnterior ){
				resultado = RESULTADOATACARCARTA.GANAATACANTE;//gana
			}
			else if(valorJugadorActual < valorJugadorAnterior){
				resultado = RESULTADOATACARCARTA.PIERDEATACANTE;//pierde
			}
			else{
				resultado = RESULTADOATACARCARTA.EMPATE;//empata
			}

			//Jugador Atacado al Ataque
			if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] == ZonaBatalla.POSCARTA.ATAQUE){
				if(resultado == RESULTADOATACARCARTA.GANAATACANTE){
					JugadorAnterior.ZonaBatalla.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.NOHAYCARTA;
					resultado = JugadorAnterior.Barrera.quitarUltimaCartaDisponible();
					if(JugadorAnterior.Barrera.obtenerNumerodeCartas() == 0){
						resultado = RESULTADOATACARCARTA.ENEMIGOSINCARTAS;
					}
				}
				else if(resultado == RESULTADOATACARCARTA.PIERDEATACANTE){//pierde atacante
					JugadorActual.ZonaBatalla.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
				else{//Empate
					JugadorAnterior.ZonaBatalla.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.NOHAYCARTA;
					JugadorActual.ZonaBatalla.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
			}
			else if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] == ZonaBatalla.POSCARTA.DEFCARAARRIBA ){//Jugador Atacado a la Defenza
				if(resultado == RESULTADOATACARCARTA.GANAATACANTE){//gana atacante
					JugadorAnterior.ZonaBatalla.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
				else if(resultado == RESULTADOATACARCARTA.PIERDEATACANTE){//pierde atacante
					JugadorActual.ZonaBatalla.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
				else{//empate
					//No pasa nada
				}
	
			}
			else{//0
				
			}

			JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.DISPONIBLE;
		}
		return resultado;
	}

	public static class RESULTADOATACARBARRERA{
		public static final int NOSEPUEDE = -1; // No se cumplen las condiciones de ataque
		public static final int EXITO = 0; //Se atacó a la barrera
		public static final int SINBARRERAS = 3; //Termina el juego porque enemigo se quedo sin cartas (Termino 2)
	}

	public int accionAtacarBarrera(Jugador JugadorActual,Jugador JugadorAnterior,int idCartaAtacante){
		int respuesta=RESULTADOATACARBARRERA.NOSEPUEDE;
		if(JugadorActual.ZonaBatalla.obtenerCartaxId(idCartaAtacante) != null
				&& JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] == ZonaBatalla.POSCARTA.ATAQUE
				&& JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] == ZonaBatalla.DISPATAQUE.DISPONIBLE
				&& JugadorAnterior.ZonaBatalla.obtenerNumerodeCartas() == 0
				&& JugadorActual.getNTurnos() > 1){
			JugadorAnterior.Barrera.quitarUltimaCartaDisponible();
			JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
			if(JugadorAnterior.Barrera.obtenerNumerodeCartas() == 0){
				respuesta=RESULTADOATACARBARRERA.SINBARRERAS;
			}
			else{
				respuesta=RESULTADOATACARBARRERA.EXITO;
			}
		}
		return respuesta;
	}
}
