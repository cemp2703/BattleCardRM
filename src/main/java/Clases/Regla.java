package Clases;

public class Regla {

	boolean accionColocarCartaEnEspacioVacio(Jugador JugadorActual,int idCartaMano,int posCarta){
		boolean respuesta=false;
		if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano)!=null){
			int idcarta=JugadorActual.ZonaBatalla.Cartas.agregarCartaEnEspacioVacio(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano));
			JugadorActual.ZonaBatalla.poscarta[idcarta]=posCarta;
			JugadorActual.Mano.Cartas.quitarCartaenPos(idCartaMano);
			JugadorActual.ZonaBatalla.setCartacolocada(true);
			JugadorActual.ZonaBatalla.dispcambio[idcarta]=ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
			if(posCarta==ZonaBatalla.POSCARTA.ATAQUE){
				JugadorActual.ZonaBatalla.dispataque[idcarta]=ZonaBatalla.DISPATAQUE.DISPONIBLE;
			}
			else{
				JugadorActual.ZonaBatalla.dispataque[idcarta]=ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			}
			respuesta=true;
		}
		return respuesta;
	}

	public boolean accionColocarCarta(Jugador JugadorActual,int idCartaZB,int idCartaMano,int posCarta){
		boolean respuesta=false;
		if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano)!=null && JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZB)==null){
			JugadorActual.ZonaBatalla.poscarta[idCartaZB]=posCarta;
			JugadorActual.ZonaBatalla.Cartas.agregarCartaEnPos(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano), idCartaZB);
			JugadorActual.Mano.Cartas.quitarCartaenPos(idCartaMano);
			JugadorActual.ZonaBatalla.setCartacolocada(true);
			JugadorActual.ZonaBatalla.dispcambio[idCartaZB]=ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
			if(posCarta==Carta.CARTAPOS.COLOCARENDEFENSA){
				JugadorActual.ZonaBatalla.dispataque[idCartaZB]=ZonaBatalla.DISPATAQUE.DISPONIBLE;
			}
			else{
				JugadorActual.ZonaBatalla.dispataque[idCartaZB]=ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			}
			respuesta=true;
		}
		return respuesta;
	}

	public boolean accionCambiarPosicionBatalla(Jugador JugadorActual,int idCartaZBJAct){
		boolean respuesta=false;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZBJAct)!=null
				&& JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct]==ZonaBatalla.DISPCAMBIO.DISPONIBLE ){
				if( JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]==ZonaBatalla.POSCARTA.DEFCARAARRIBA ||
						JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]==ZonaBatalla.POSCARTA.DEFCARAABAJO){
					JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]=ZonaBatalla.POSCARTA.ATAQUE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct]=ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					respuesta=true;
				}
				else if(JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]==ZonaBatalla.POSCARTA.ATAQUE){
					JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]=ZonaBatalla.POSCARTA.DEFCARAARRIBA;
					JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct]=ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispataque[idCartaZBJAct]=ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					respuesta=true;
				}
		}
		return respuesta;
	}
	
	boolean posibilidadAtacarCarta(Jugador JugadorActual, Jugador JugadorAnterior,int idCartaAtacada,int idCartaAtacante){
		boolean respuesta=false;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!=null &&
				JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]== ZonaBatalla.POSCARTA.ATAQUE &&
				JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]==ZonaBatalla.DISPATAQUE.DISPONIBLE &&
				JugadorActual.getNTurnos()>1 &&
				JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacada)!=null){
			respuesta=true;
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
		int resultado=RESULTADOATACARCARTA.NOSECUMPLENCOND;
		int valorJugadorActual,valorJugadorAnterior;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!=null &&
				JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] == ZonaBatalla.POSCARTA.ATAQUE &&
				JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] == ZonaBatalla.DISPATAQUE.DISPONIBLE &&
				JugadorActual.getNTurnos() > 1 &&
				JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacada) != null){

			//Jugador Atacado En defenza cara abajo
			if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]==ZonaBatalla.POSCARTA.DEFCARAABAJO){
				JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]=ZonaBatalla.POSCARTA.DEFCARAARRIBA;
			}
			
			valorJugadorActual=JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante).getValor();
			valorJugadorAnterior=JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacada).getValor();
			
			if(valorJugadorActual  > valorJugadorAnterior ){
				resultado = RESULTADOATACARCARTA.GANAATACANTE;//gana
			}
			else if(valorJugadorActual  < valorJugadorAnterior){
				resultado = RESULTADOATACARCARTA.PIERDEATACANTE;//pierde
			}
			else{
				resultado = RESULTADOATACARCARTA.EMPATE;//empata
			}

			//Jugador Atacado al Ataque
			if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]== ZonaBatalla.POSCARTA.ATAQUE){
				if(resultado == RESULTADOATACARCARTA.GANAATACANTE){
					JugadorAnterior.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.NOHAYCARTA;
					JugadorAnterior.Barrera.Cartas.quitarUltimaCartaDisponible();
					if(JugadorAnterior.Barrera.Cartas.obtenerNumerodeCartas() == 0){
						resultado = RESULTADOATACARCARTA.ENEMIGOSINCARTAS;
					}
				}
				else if(resultado == RESULTADOATACARCARTA.PIERDEATACANTE){//pierde atacante
					JugadorActual.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
				else{//Empate
					JugadorAnterior.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.NOHAYCARTA;
					JugadorActual.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
			}
			else if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] == ZonaBatalla.POSCARTA.DEFCARAARRIBA ||
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] == ZonaBatalla.POSCARTA.DEFCARAABAJO){//Jugador Atacado a la Defenza
				if(resultado == RESULTADOATACARCARTA.GANAATACANTE){//gana atacante
					JugadorAnterior.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada] = ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada] = ZonaBatalla.POSCARTA.NOHAYCARTA;
				}
				else if(resultado == RESULTADOATACARCARTA.PIERDEATACANTE){//pierde atacante
					JugadorActual.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacante);
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

			JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]= ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
		}
		return resultado;
	}
	
	boolean posibilidadAtacarBarrera(Jugador JugadorActual,Jugador JugadorAnterior,int idCartaAtacante){
		boolean respuesta=false;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!=null && JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]==1
				&& JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]==1 && JugadorActual.getNTurnos()>1
				&& JugadorAnterior.ZonaBatalla.Cartas.obtenerNumerodeCartas()==0){
			
			respuesta=true;
		}
		return respuesta;
	}

	public static class RESULTADOATACARBARRERA{
		public static final int NOSEPUEDE = -1; // No se cumplen las condiciones de ataque
		public static final int EXITO = 0; //Se atacó a la barrera
		public static final int SINBARRERAS = 3; //Termina el juego porque enemigo se quedo sin cartas (Termino 2)
	}

	public int  accionAtacarBarrera(Jugador JugadorActual,Jugador JugadorAnterior,int idCartaAtacante){
		int respuesta=-RESULTADOATACARBARRERA.NOSEPUEDE;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!= null
				&& JugadorActual.ZonaBatalla.poscarta[idCartaAtacante] == ZonaBatalla.POSCARTA.ATAQUE
				&& JugadorActual.ZonaBatalla.dispataque[idCartaAtacante] == ZonaBatalla.DISPATAQUE.DISPONIBLE
				&& JugadorAnterior.ZonaBatalla.Cartas.obtenerNumerodeCartas()== 0
				&& JugadorActual.getNTurnos() > 1){
			JugadorAnterior.Barrera.Cartas.quitarUltimaCartaDisponible();
			JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]=ZonaBatalla.DISPATAQUE.NODISPONIBLE;
			JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=ZonaBatalla.DISPCAMBIO.NODISPONIBLE;
			if(JugadorAnterior.Barrera.Cartas.obtenerNumerodeCartas()==0){
				respuesta=RESULTADOATACARBARRERA.SINBARRERAS;
			}
			else{
				respuesta=RESULTADOATACARBARRERA.EXITO;
			}
		}
		return respuesta;
	}
}
