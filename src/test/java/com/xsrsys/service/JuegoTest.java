package com.xsrsys.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xsrsys.service.Carta.Elemento;
import com.xsrsys.service.Juego.DialogoJuego;
import com.xsrsys.service.Juego.Momento;
import com.xsrsys.service.ZonaBatalla.PosBatalla;
import com.xsrsys.service.Tablero;
import com.xsrsys.service.Juego;
import com.xsrsys.service.Jugador;

public class JuegoTest {
	@Test
	public void testCambioDeJugadorActual() throws Exception{
		String nomJug1="J1";
		String nomJug2="J1";
		Jugador jug1=new Jugador(nomJug1);
		Jugador jug2=new Jugador(nomJug2);
		Juego j=new Juego();
		j.tablero= new Tablero(jug1,jug2);
		assertEquals(true,j.tablero.jugadorActual.getNombre()==nomJug1);
		j.tablero.cambioDeJugadorActual();
		assertEquals(true,j.tablero.jugadorActual.getNombre()==nomJug2);
	}
	
	@Test
	public void testCogerUnaCartaDelDeck() throws Exception{
		String nomJug1="J1";
		Jugador jug1=new Jugador(nomJug1);
		Juego j = new Juego();
		j.tablero=new Tablero(jug1,null);
		Carta c1=new Carta(1,Elemento.COCO);
		Carta c2=new Carta(2,Elemento.COCO);
		Carta c3=new Carta(3,Elemento.COCO);
		Carta c4=new Carta(4,Elemento.COCO);
		Carta c5=new Carta(5,Elemento.COCO);
		Carta c6=new Carta(6,Elemento.COCO);
		//Reparticion de cartas en deck
		j.tablero.jugadorActual.Deck.agregarUnaCarta(c1);
		j.tablero.jugadorActual.Deck.agregarUnaCarta(c2);
		j.tablero.jugadorActual.Deck.agregarUnaCarta(c3);
		j.tablero.jugadorActual.Deck.agregarUnaCarta(c4);
		j.tablero.jugadorActual.Deck.agregarUnaCarta(c5);
		j.tablero.jugadorActual.Deck.agregarUnaCarta(c6);
		//Manos llenas
		j.tablero.jugadorActual.accionIniciarTurno();
		j.tablero.jugadorActual.accionCogerUnaCartaDelDeck();
		j.tablero.jugadorActual.accionCogerUnaCartaDelDeck();
		j.tablero.jugadorActual.accionCogerUnaCartaDelDeck();
		j.tablero.jugadorActual.accionCogerUnaCartaDelDeck();
		j.tablero.jugadorActual.accionCogerUnaCartaDelDeck();
		j.tablero.jugadorActual.accionIniciarTurno();
		j.cogerUnaCartaDelDeck();
		assertEquals(DialogoJuego.OPCIONESENTURNO,j.dialogoJuego);
		assertEquals(Momento.OPCIONESENTURNO,j.momento);
		j.tablero.jugadorActual.accionColocarCarta(0,0, PosBatalla.ATAQUE);
		//EXITO
		j.tablero.jugadorActual.accionIniciarTurno();
		j.cogerUnaCartaDelDeck();
		assertEquals(DialogoJuego.OPCIONESENTURNO,j.dialogoJuego);
		assertEquals(Momento.OPCIONESENTURNO,j.momento);
		j.tablero.jugadorActual.accionColocarCarta(1,1, PosBatalla.ATAQUE);
		//SIN CARTAS EN DECK
		j.tablero.jugadorActual.accionIniciarTurno();
		j.cogerUnaCartaDelDeck();
		assertEquals(DialogoJuego.JUGADORSINCARTASBARRERA,j.dialogoJuego);
		assertEquals(Momento.JUGADORSINCARTASBARRERA,j.momento);
		assertEquals(j.tablero.jugadorAnterior,j.jugadorVictorioso);
	}
	
}
