package com.xsrsys.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xsrsys.service.Juego;

public class JuegoTest {
	@Test
	public void testCambioDeJugadorActual() throws Exception{
		String nomJug1="J1";
		String nomJug2="J1";
		Jugador jug1=new Jugador(nomJug1);
		Jugador jug2=new Jugador(nomJug2);
		Juego j=new Juego();
		j.E= new Estado(jug1,jug2);
		assertEquals(true,j.E.jugadorActual.getNombre()==nomJug1);
		j.cambioDeJugadorActual();
		assertEquals(true,j.E.jugadorActual.getNombre()==nomJug2);
	}
}
