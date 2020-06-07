package com.xsrsys.model;

public class Estado implements Cloneable {
	public Jugador jugadorActual;
	public Jugador jugadorAnterior;
	public Jugador jugador1;
	public Jugador jugador2;
	private double valorEstado;
	
	public Estado(Jugador pJugador1,Jugador pJugador2){
		jugador1=pJugador1;
		jugador2=pJugador2;
		valorEstado = 0;
		jugadorActual=jugador1;
		jugadorAnterior=jugador2;
	}
	public Object clone() throws CloneNotSupportedException{
		Estado clon=(Estado) super.clone();
		clon.jugadorActual=jugadorActual;
		clon.jugadorAnterior=jugadorAnterior;
		clon.jugador1=jugador1;
		clon.jugador2=jugador2;
		return clon;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (jugador1 == null) {
			if (other.jugador1 != null)
				return false;
		} else if (!jugador1.equals(other.jugador1))
			return false;
		if (jugador2 == null) {
			if (other.jugador2 != null)
				return false;
		} else if (!jugador2.equals(other.jugador2))
			return false;
		if (jugadorActual == null) {
			if (other.jugadorActual != null)
				return false;
		} else if (!jugadorActual.equals(other.jugadorActual))
			return false;
		if (jugadorAnterior == null) {
			if (other.jugadorAnterior != null)
				return false;
		} else if (!jugadorAnterior.equals(other.jugadorAnterior))
			return false;
		if (Double.doubleToLongBits(valorEstado) != Double.doubleToLongBits(other.valorEstado))
			return false;
		return true;
	}
	public double getValorEstado() {
		return valorEstado;
	}
	public void setValorEstado(double pvalorEstado) {
		valorEstado = pvalorEstado;
	}
}
