package com.xsrsys.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.xsrsys.model.Carta;
import com.xsrsys.model.VectorCartas;
import com.xsrsys.model.ZonaBatalla;

public class ZonaBatallaTest {
	
    @Test
    public void testGetPosBatallaxId() throws Exception {
        int IDCARTAZB = 0;
        ZonaBatalla zb=new ZonaBatalla();
        int POSBATALLA = ZonaBatalla.POSBATALLA.ATAQUE;
        zb.agregarCartaEnPos(new Carta(1,Carta.ELEMENTO.ESPADA),IDCARTAZB, POSBATALLA);
        zb.getPosBatallaxId(IDCARTAZB);
        assertEquals(POSBATALLA,zb.getPosBatallaxId(IDCARTAZB));
        //IDZONABATALLA mayor del tamaño maximo: 5
        int IDCARTAZBERRONEO = 8;
        assertNotEquals(POSBATALLA,zb.getPosBatallaxId(IDCARTAZBERRONEO));
    }


    @Test
    public void testRenovarDisponibilidades() throws Exception {
        int POSCARTA0 = ZonaBatalla.POSBATALLA.ATAQUE;
        int POSCARTA1 = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int idZB0 = 0;
        int idZB1 = 1;
        zb.agregarCartaEnPos(c, idZB0 , POSCARTA0);
        zb.agregarCartaEnPos(c, idZB1 , POSCARTA1);
        zb.renovarPosibilidades();
        assertEquals(ZonaBatalla.DISPATAQUE.DISPONIBLE,zb.dispataque[idZB0]);
        assertEquals(ZonaBatalla.DISPCAMBIO.DISPONIBLE,zb.dispcambio[idZB0]);
        assertEquals(ZonaBatalla.DISPATAQUE.NODISPONIBLE,zb.dispataque[idZB1]);
        assertEquals(ZonaBatalla.DISPCAMBIO.DISPONIBLE,zb.dispcambio[idZB1]);
    }

    @Test
    public void testAgregarCartaEnPos() throws Exception {
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        boolean CARTACOLOCADA = true;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            assertEquals(POSCARTA,zb.posbatalla[i]);
            assertEquals(ZonaBatalla.DISPCAMBIO.NODISPONIBLE,zb.dispcambio[i]);
            assertEquals(ZonaBatalla.DISPATAQUE.DISPONIBLE,zb.dispataque[i]);
        }
        assertEquals(CARTACOLOCADA,zb.cartaColocada);
    }

    @Test
    public void testAgregarCartaEnEspacioVacio() throws Exception {
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int idZonaB0= 0;
        int idZonaB1= 1;
        int idZonaB2= 2;
        assertEquals(idZonaB0,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
        assertEquals(idZonaB1,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
        assertEquals(idZonaB2,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
        assertEquals(VectorCartas.NOSEPUEDEAGREGARCARTAS,zb.agregarCartaEnEspacioVacio(c,POSCARTA));
    }


    @Test
    public void testCambiarPosicionBatallaAtaque() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, ZonaBatalla.POSBATALLA.ATAQUE);
            zb.dispcambio[i]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
            zb.nCambiosPosicionesDisponibles++;
            zb.cambiarPosicionBatalla(i);
            assertEquals(ZonaBatalla.POSBATALLA.DEFCARAARRIBA,zb.posbatalla[i]);
            assertEquals(ZonaBatalla.DISPCAMBIO.NODISPONIBLE,zb.dispcambio[i]);
            assertEquals(ZonaBatalla.DISPATAQUE.NODISPONIBLE,zb.dispataque[i]);
        }
    }

    @Test
    public void testCambiarPosicionBatallaDefensa() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, ZonaBatalla.POSBATALLA.DEFCARAABAJO);
            zb.dispcambio[i]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
            zb.nCambiosPosicionesDisponibles++;
            zb.cambiarPosicionBatalla(i);
            assertEquals(ZonaBatalla.POSBATALLA.ATAQUE,zb.posbatalla[i]);
            assertEquals(ZonaBatalla.DISPCAMBIO.NODISPONIBLE,zb.dispcambio[i]);
            assertEquals(ZonaBatalla.DISPATAQUE.DISPONIBLE,zb.dispataque[i]);
        }
    }

    @Test
    public void testQuitarCartaenPos() throws Exception {
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            zb.quitarCartaenPos(i);
            assertEquals(ZonaBatalla.POSBATALLA.NOHAYCARTA,zb.posbatalla[i]);
            assertEquals(ZonaBatalla.DISPCAMBIO.NODISPONIBLE,zb.dispcambio[i]);
            assertEquals(ZonaBatalla.DISPATAQUE.NODISPONIBLE,zb.dispataque[i]);
        }
    }

    @Test
    public void testPosibilidadCambiarPosicionBatalla() throws Exception{
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        int IDCARTAZB = 0;
        boolean NOCARTAS = false, DISP_CAMBIO = false;
        assertEquals(NOCARTAS,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));
        zb.agregarCartaEnPos(c,IDCARTAZB,POSCARTA);
        assertEquals(DISP_CAMBIO,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));
        zb.dispcambio[IDCARTAZB]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
        zb.nCambiosPosicionesDisponibles++;
        DISP_CAMBIO = true;
        assertEquals(DISP_CAMBIO,zb.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));

    }

    @Test
    public void testClone() throws Exception {
        ZonaBatalla vc=new ZonaBatalla();
        int idZB0= 0;
        int idZB1= 1;
        int POSCARTA0 = ZonaBatalla.POSBATALLA.ATAQUE;
        vc.agregarCartaEnPos(new Carta(Carta.ELEMENTO.ESPADA,1),idZB0,POSCARTA0);
        ZonaBatalla vclone=(ZonaBatalla)vc.clone();
        assertEquals(vc,vclone);
        vc.agregarCartaEnPos(new Carta(Carta.ELEMENTO.ESPADA,2),idZB1,POSCARTA0);
        assertNotEquals(vc,vclone);
    }

}
