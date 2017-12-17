public class Regla {

	boolean accionColocarCartaEnEspacioVacio(Jugador JugadorActual,int idCartaMano,int posCarta){
		boolean respuesta=false;
		if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano)!=null){
			int idcarta=JugadorActual.ZonaBatalla.Cartas.agregarCartaEnEspacioVacio(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano));
			JugadorActual.ZonaBatalla.poscarta[idcarta]=posCarta;
			JugadorActual.Mano.Cartas.quitarCartaenPos(idCartaMano);
			JugadorActual.ZonaBatalla.setCartacolocada(true);
			JugadorActual.ZonaBatalla.dispcambio[idcarta]=0;
			if(posCarta==1){
				JugadorActual.ZonaBatalla.dispataque[idcarta]=1;
			}
			else{
				JugadorActual.ZonaBatalla.dispataque[idcarta]=0;
			}
			respuesta=true;
		}
		return respuesta;
	}
	
	boolean accionColocarCarta(Jugador JugadorActual,int idCartaZB,int idCartaMano,int posCarta){
		boolean respuesta=false;
		if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano)!=null && JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZB)==null){
			JugadorActual.ZonaBatalla.poscarta[idCartaZB]=posCarta;
			JugadorActual.ZonaBatalla.Cartas.agregarCartaEnPos(JugadorActual.Mano.Cartas.obtenerCartaxId(idCartaMano), idCartaZB);
			JugadorActual.Mano.Cartas.quitarCartaenPos(idCartaMano);
			JugadorActual.ZonaBatalla.setCartacolocada(true);
			JugadorActual.ZonaBatalla.dispcambio[idCartaZB]=0;
			if(posCarta==1){
				JugadorActual.ZonaBatalla.dispataque[idCartaZB]=1;
			}
			else{
				JugadorActual.ZonaBatalla.dispataque[idCartaZB]=0;
			}
			respuesta=true;
		}
		return respuesta;
	}
	
	boolean accionCambiarPosicionBatalla(Jugador JugadorActual,int idCartaZBJAct){//pos 0: Defenza, 1: Ataque
		boolean respuesta=false;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZBJAct)!=null
				&& JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct]==1 ){
				if( JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]==2 || JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]==3 ){
					JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]=1;
					JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct]=0;
					respuesta=true;
				}
				else if(JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]==1){
					JugadorActual.ZonaBatalla.poscarta[idCartaZBJAct]=2;
					JugadorActual.ZonaBatalla.dispcambio[idCartaZBJAct]=0;
					JugadorActual.ZonaBatalla.dispataque[idCartaZBJAct]=0;
					respuesta=true;
				}
		}
		return respuesta;
	}
	
	boolean posibilidadAtacarCarta(Jugador JugadorActual, Jugador JugadorAnterior,int idCartaAtacada,int idCartaAtacante){
		boolean respuesta=false;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!=null && JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]==1
				&& JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]==1 && JugadorActual.getNTurnos()>1
				&& JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacada)!=null){
			respuesta=true;
		}
		return respuesta;
	}
	
	int accionAtacarCarta(Jugador JugadorActual, Jugador JugadorAnterior,int idCartaAtacada,int idCartaAtacante){//Sistema de produccion
		int resultado=-1;
		/*-1: No se cumplen las condiciones,
		 *  0 empate, 1: Gana Atacante, 2: Pierde Atacante,3: Termina el juego porque enemigo se quedo sin cartas (Termino 2)
		 */
		int valorJugadorActual,valorJugadorAnterior;
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!=null && JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]==1
				&& JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]==1 && JugadorActual.getNTurnos()>1
				&& JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacada)!=null){
		
			if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]==3){//Jugador Atacado En defenza cara abajo
				JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]=2;
				
			}
			
			valorJugadorActual=JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante).getValor();
			valorJugadorAnterior=JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacada).getValor();
			
			if(valorJugadorActual  > valorJugadorAnterior ){
				resultado=1;//gana
			}
			else if(valorJugadorActual  < valorJugadorAnterior){
				resultado=2;//pierde
			}
			else{
				resultado=0;//empata
			}
			
			if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]==1){//Jugador Atacado al Ataque
				if(resultado==1){//gana atacante
					JugadorAnterior.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada]=0;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada]=0;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]=0;
					JugadorAnterior.Barrera.Cartas.quitarUltimaCartaDisponible();
					if(JugadorAnterior.Barrera.Cartas.obtenerNumerodeCartas()==0){
						resultado=3;
					}
				}
				else if(resultado==2){//pierde atacante
					JugadorActual.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]=0;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=0;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]=0;
				}
				else{//Empate
					JugadorAnterior.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada]=0;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada]=0;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]=0;
					JugadorActual.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]=0;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=0;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]=0;
				}
			}
			else if(JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]==2 ||
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]==3){//Jugador Atacado a la Defenza 
				if(resultado==1){//gana atacante
					JugadorAnterior.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacada);
					JugadorAnterior.ZonaBatalla.dispataque[idCartaAtacada]=0;
					JugadorAnterior.ZonaBatalla.dispcambio[idCartaAtacada]=0;
					JugadorAnterior.ZonaBatalla.poscarta[idCartaAtacada]=0;
				}
				else if(resultado==2){//pierde atacante
					JugadorActual.ZonaBatalla.Cartas.quitarCartaenPos(idCartaAtacante);
					JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]=0;
					JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=0;
					JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]=0;
				}
				else{//empate
					//No pasa nada
				}
	
			}
			else{//0
				
			}
			
			
			JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]=0;
			JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=0;
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
	
	int  accionAtacarBarrera(Jugador JugadorActual,Jugador JugadorAnterior,int idCartaAtacante){
		int respuesta=-1;//-1: No se puedo, 0: Se atac� a la barrera,3: Termina el juego porque enemigo se quedo sin cartas (Termino 2)
		if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaAtacante)!=null && JugadorActual.ZonaBatalla.poscarta[idCartaAtacante]==1
				&& JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]==1 && JugadorAnterior.ZonaBatalla.Cartas.obtenerNumerodeCartas()==0
				&& JugadorActual.getNTurnos()>1){
			JugadorAnterior.Barrera.Cartas.quitarUltimaCartaDisponible();
			JugadorActual.ZonaBatalla.dispataque[idCartaAtacante]=0;
			JugadorActual.ZonaBatalla.dispcambio[idCartaAtacante]=0;
			if(JugadorAnterior.Barrera.Cartas.obtenerNumerodeCartas()==0){
				respuesta=3;
			}
			else{
				respuesta=0;
			}
		}
		return respuesta;
		
		
	}
}
