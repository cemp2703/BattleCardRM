package com.xsrsys.service;

import org.junit.Test;

import com.xsrsys.service.Carta;
import com.xsrsys.service.VectorCartas;
import com.xsrsys.service.ZonaBatalla;
import com.xsrsys.service.ZonaBatalla.DispCambio;
import com.xsrsys.service.ZonaBatalla.PosBatalla;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ZonaBatallaTest {
	
    @Test
    public void testGetPosBatallaxId() throws Exception {
        int IDCARTAZB = 0;
        ZonaBatalla zb=new ZonaBatalla();
        PosBatalla POSBATALLA = ZonaBatalla.PosBatalla.ATAQUE;
        zb.agregarCartaEnPos(new Carta(1,Carta.Elemento.ESPADA),IDCARTAZB, POSBATALLA);
        zb.getPosBatallaxId(IDCARTAZB);
        assertEquals(POSBATALLA,zb.getPosBatallaxId(IDCARTAZB));
        //IDZONABATALLA mayor del tamaño maximo: 5
        int IDCARTAZBERRONEO = 8;
        assertNotEquals(POSBATALLA,zb.getPosBatallaxId(IDCARTAZBERRONEO));
    }


    @Test
    public void testRenovarDisponibilidades() throws Exception {
    	PosBatalla POSCARTA0 = ZonaBatalla.PosBatalla.ATAQUE;
    	PosBatalla POSCARTA1 = ZonaBatalla.PosBatalla.DEFCARAABAJO;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.Elemento.ESPADA);
        int idZB0 = 0;
        int idZB1 = 1;
        zb.agregarCartaEnPos(c, idZB0 , POSCARTA0);
        zb.agregarCartaEnPos(c, idZB1 , POSCARTA1);
        zb.renovarPosibilidades();
        assertEquals(ZonaBatalla.DispAtaque.DISPONIBLE,zb.dispAtaque[idZB0]);
        assertEquals(ZonaBatalla.DispCambio.DISPONIBLE,zb.dispCambio[idZB0]);
        assertEquals(ZonaBatalla.DispAtaque.NODISPONIBLE,zb.dispAtaque[idZB1]);
        assertEquals(ZonaBatalla.DispCambio.DISPONIBLE,zb.dispCambio[idZB1]);
    }

    @Test
    public void testAgregarCartaEnPos() throws Exception {
    	PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
        boolean CARTACOLOCADA = true;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.Elemento.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            assertEquals(POSCARTA,zb.posBatalla[i]);
            assertEquals(ZonaBatalla.DispCambio.NODISPONIBLE,zb.dispCambio[i]);
            assertEquals(ZonaBatalla.DispAtaque.DISPONIBLE,zb.dispAtaque[i]);
        }
        assertEquals(CARTACOLOCADA,zb.cartaColocada);
    }

    @Test
    public void testAgregarCartaEnEspacioVacio() throws Exception {
    	PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.Elemento.ESPADA);
        int idZonaB0= 0;
        int idZonaB1= 1;
        int idZonaB2= 2;
        assertEquals(idZonaB0,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
        assertEquals(idZonaB1,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
        assertEquals(idZonaB2,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
        assertEquals(VectorCartas.NOSEPUEDEAGREGARCARTAS,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
    }


    @Test
    public void testCambiarPosicionBatalla() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.Elemento.ESPADA);
        int ID = 0;
        zb.agregarCartaEnPos(c, ID, ZonaBatalla.PosBatalla.ATAQUE);
        zb.dispCambio[ID]=ZonaBatalla.DispCambio.DISPONIBLE;
        zb.nCambiosPosicionesDisponibles++;
        zb.cambiarPosicionBatalla(ID);
        assertEquals(ZonaBatalla.PosBatalla.DEFCARAARRIBA,zb.posBatalla[ID]);
        assertEquals(ZonaBatalla.DispCambio.NODISPONIBLE,zb.dispCambio[ID]);
        assertEquals(ZonaBatalla.DispAtaque.NODISPONIBLE,zb.dispAtaque[ID]);
        zb.dispCambio[ID]=ZonaBatalla.DispCambio.DISPONIBLE;
        zb.nCambiosPosicionesDisponibles++;
        zb.cambiarPosicionBatalla(ID);
        assertEquals(ZonaBatalla.PosBatalla.ATAQUE,zb.posBatalla[ID]);
        assertEquals(ZonaBatalla.DispCambio.NODISPONIBLE,zb.dispCambio[ID]);
        assertEquals(ZonaBatalla.DispAtaque.DISPONIBLE,zb.dispAtaque[ID]);
    }

    @Test
    public void testQuitarCartaenPos() throws Exception {
    	PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.Elemento.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            zb.quitarCartaenPos(i);
            assertEquals(ZonaBatalla.PosBatalla.NOHAYCARTA,zb.posBatalla[i]);
            assertEquals(ZonaBatalla.DispCambio.NODISPONIBLE,zb.dispCambio[i]);
            assertEquals(ZonaBatalla.DispAtaque.NODISPONIBLE,zb.dispAtaque[i]);
        }
    }

    @Test
    public void testPuedeCambiarPosicion() {
    	ZonaBatalla zb=new ZonaBatalla();
    	int IDCARTAZB = 0;
    	PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
    	Carta c=new Carta(1,Carta.Elemento.ESPADA);
    	assertEquals("Sin Cartas",false,zb.puedeCambiarPosicion());
    	zb.agregarCartaEnPos(c,IDCARTAZB,POSCARTA);
    	assertEquals("Sin cambios de posiciones disponibles",false,zb.puedeCambiarPosicion());
    	zb.nCambiosPosicionesDisponibles++;
    	zb.nAtaquesDisponibles--;
    	assertEquals("Sin ataques disponibles",false,zb.puedeCambiarPosicion());
    	zb.nAtaquesDisponibles++;
    	assertEquals("Exitoso",true,zb.puedeCambiarPosicion());
    }
    
    @Test
    public void testPosibilidadCambiarPosicionBatalla() throws Exception{
        ZonaBatalla zb=new ZonaBatalla();
        int IDCARTAZB = 1;
        int IDCARTAZB2 = 0;
        PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
        Carta c=new Carta(1,Carta.Elemento.ESPADA);
        assertEquals("No puede cambiarse posicion",false,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));
        zb.agregarCartaEnPos(c,IDCARTAZB,POSCARTA);
        zb.nCambiosPosicionesDisponibles++;
        zb.nAtaquesDisponibles++;
        assertEquals("No hay carta en esa posición",false,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB2));
        zb.dispCambio[IDCARTAZB] = DispCambio.NODISPONIBLE;
        assertEquals("Dicha carta no tiene disposicion de cambio",false,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));
        zb.dispCambio[IDCARTAZB] = DispCambio.DISPONIBLE;
        assertEquals("Exitoso",true,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));
    }

    @Test
    public void testClone() throws Exception {
        ZonaBatalla vc=new ZonaBatalla();
        int idZB0= 0;
        int idZB1= 1;
        PosBatalla POSCARTA0 = ZonaBatalla.PosBatalla.ATAQUE;
        vc.agregarCartaEnPos(new Carta(1,Carta.Elemento.ESPADA),idZB0,POSCARTA0);
        ZonaBatalla vclone=(ZonaBatalla)vc.clone();
        assertEquals(vc,vclone);
        vc.agregarCartaEnPos(new Carta(2,Carta.Elemento.ESPADA),idZB1,POSCARTA0);
        assertNotEquals(vc,vclone);
    }

}
