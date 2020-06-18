package com.xsrsys.service;

public class Tablero implements Cloneable {
	public Jugador jugadorActual;
	public Jugador jugadorAnterior;
	public Jugador jugador1;
	public Jugador jugador2;
	private double valorEstado;
	
	public Tablero(Jugador pJugador1,Jugador pJugador2){
		jugador1=pJugador1;
		jugador2=pJugador2;
		valorEstado = 0;
		jugadorActual=jugador1;
		jugadorAnterior=jugador2;
	}
	public Object clone() throws CloneNotSupportedException{
		Tablero clon=(Tablero) super.clone();
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
		Tablero other = (Tablero) obj;
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
	
	public String obtenerStringEstado() {
    	String resp = "";
    	resp+="TURNO: "+jugadorActual.getNombre()+"\n";
    	resp+="*****************************************************************************\n";
        resp+="Jugador 1\n";
        resp+="Mano\n";
        for(int i = 0; i < Mano.MAXMANOCARDS; i++){
            if(jugador1.Mano.obtenerCartaxId(i) != null)
                resp+=jugador1.Mano.obtenerCartaxId(i).getValor() + " " +
                		Carta.obtenerStringElementoUnicode(jugador1.Mano.obtenerCartaxId(i).getElemento()) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="Barrera\n";
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(jugador1.Barrera.obtenerCartaxId(i) != null)
                resp+="BARRERA| ";
            else
                resp+="VACIO  | ";
        }
        resp+="\n";
        resp+="ZonaBatalla\n";
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(jugador1.ZBatalla.obtenerCartaxId(i) != null)
                resp+=jugador1.ZBatalla.obtenerCartaxId(i).getValor()+ " " +
                        Carta.obtenerStringElementoUnicode(jugador1.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        ZonaBatalla.obtenerStringPosCarta(jugador1.ZBatalla.posBatalla[i]) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";

        resp+="Jugador 2\n";
        resp+="Mano\n";
        for(int i=0;i < Mano.MAXMANOCARDS;i++){
            if(jugador2.Mano.obtenerCartaxId(i) != null)
                resp+=jugador2.Mano.obtenerCartaxId(i).getValor() + " " +
                        Carta.obtenerStringElementoUnicode(jugador2.Mano.obtenerCartaxId(i).getElemento()) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="Barrera\n";
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(jugador2.Barrera.obtenerCartaxId(i) != null)
                resp+="BARRERA| ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="ZonaBatalla\n";
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(jugador2.ZBatalla.obtenerCartaxId(i) != null)
                resp+=jugador2.ZBatalla.obtenerCartaxId(i).getValor() + " " +
                        Carta.obtenerStringElementoUnicode(jugador2.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        ZonaBatalla.obtenerStringPosCarta(jugador2.ZBatalla.posBatalla[i]) + " | ";
            else
                resp+="VACIO | ";
        }

        resp+="\n";
        resp+="*****************************************************************************\n";
        resp+="\n";
        return resp;
    }
    
	public void cambioDeJugadorActual(){
		Jugador jugadorTmp =jugadorActual;
		jugadorActual = jugadorAnterior;
		jugadorAnterior = jugadorTmp;
	}
	
}
