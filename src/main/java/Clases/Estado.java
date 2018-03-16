package Clases;

import Clases.POJO.ResultadoAtaque;

public class Estado implements Cloneable {
	public Jugador JugadorActual;
	public Jugador JugadorAnterior;
	public Jugador Jugador1;
	public Jugador Jugador2;
	public Jugador JugadorVictorioso;
	public Jugador JugadorDerrotado;

	private int TurnoActual;
	public static class TURNO {
		public final static int JUGADOR1 = 1; //Jugador 1
		public final static int JUGADOR2BOT = 2;  //Jugador 2 o BOT
	}
	private double valorEstado;

	public Integer Termino; //Reglas que finalizan el juego
	public static class TERMINO {
		public final static int NOTERMINO = 0; //El juego aun no llega a una condicion de victoria/derrota
		public final static int SINCARTAS = 1;  //Jugador actual se quedo sin cartas
		public final static int SINBARRERAS = 2;  //Jugador Anterior perdió todas sus barreras
	}

	public Estado(Jugador pJugador1,Jugador pJugador2){
		Jugador1=pJugador1;
		Jugador2=pJugador2;
		TurnoActual = TURNO.JUGADOR1;
		valorEstado = 0;
		JugadorActual=Jugador1;
		JugadorAnterior=Jugador2;
		JugadorVictorioso = null;
		JugadorDerrotado = null;
	}

	public Object clone() throws CloneNotSupportedException{
		Estado clon=(Estado) super.clone();
		clon.JugadorActual=JugadorActual;
		clon.JugadorAnterior=JugadorAnterior;
		clon.JugadorVictorioso=JugadorVictorioso;
		clon.JugadorDerrotado=JugadorDerrotado;
		clon.JugadorVictorioso =JugadorVictorioso;
		clon.JugadorDerrotado=JugadorDerrotado;
		return clon;
	}

	public int cambioDeTurno(){
		Jugador JugadorTmp = JugadorActual;
		JugadorActual = JugadorAnterior;
		JugadorAnterior = JugadorTmp;
		if(TurnoActual == TURNO.JUGADOR1){
			TurnoActual = TURNO.JUGADOR2BOT;

		}
		else{
			TurnoActual = TURNO.JUGADOR1;

		}
		int res= JugadorActual.accionIniciarTurno();

		if( res == Jugador.RESULTADOCOJERUNACARTA.DECKVACIO) {
			terminoSinCartas();
		}
		return res;
	}

	public void terminoSinBarreras(){
		Termino = Estado.TERMINO.SINBARRERAS;
		JugadorVictorioso = JugadorActual;
		JugadorDerrotado = JugadorAnterior;
	}

	public void terminoSinCartas(){
		JugadorVictorioso=JugadorAnterior;
		JugadorDerrotado=JugadorActual;
		Termino = TERMINO.SINCARTAS;
	}

	public double getValorEstado() {
		return valorEstado;
	}
	public void setValorEstado(double pvalorEstado) {
		valorEstado = pvalorEstado;
	}
	public int getTurno() {
		return TurnoActual;
	}
	public void setTurno(int turno) {
		TurnoActual = turno;
	}

	public Integer getTermino() {
		return Termino;
	}
	public void setTermino(Integer termino) {
		Termino = termino;
	}
}
