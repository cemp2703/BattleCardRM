package com.xsrsys.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.xsrsys.model.Carta;
import com.xsrsys.model.Mano;
import com.xsrsys.model.VectorCartas;


public class VectorCartasTest {

    @Test
    public void testVectorCartasOps() throws Exception{
        final int POS0 = 0;
        final int POS1 = 1;
        final int POS2 = 2;
        final int POSFUERADEZONA = -1;
        VectorCartas vc=new VectorCartas(Mano.MAXMANOCARDS);
        assertEquals(0,vc.obtenerNumerodeCartas());
        Carta c0 = new Carta(1,Carta.ELEMENTO.COCO);
        Carta c2 = new Carta(3,Carta.ELEMENTO.COCO);
        assertEquals(POS0,vc.agregarCartaEnEspacioVacio(c0));
        assertEquals(true,vc.agregarCartaEnPos(c2,POS2));
        assertEquals(c2,vc.obtenerCartaxId(POS2));
        assertEquals(2,vc.obtenerNumerodeCartas());
        assertEquals(false,vc.quitarCartaenPos(POSFUERADEZONA));
        assertEquals(true,vc.quitarCartaenPos(POS2));
        assertEquals(POS0,vc.quitarUltimaCartaDisponible());
        assertEquals(VectorCartas.NOCARTASDISPONIBLES,vc.quitarUltimaCartaDisponible());//No quedan cartas disponibles para quitar
    }

    @Test
    public void testClone() throws Exception {
        VectorCartas vc=new VectorCartas(Mano.MAXMANOCARDS);
        VectorCartas vclone=(VectorCartas)vc.clone();
        vc.agregarCartaEnEspacioVacio(new Carta(1,Carta.ELEMENTO.COCO));
        assertNotEquals(vc,vclone);
        vclone=(VectorCartas)vc.clone();
        assertEquals(vc,vclone);
        vc.setMaxNCartas(8);
        assertNotEquals(vc.getMaxNCartas(),vclone.getMaxNCartas());
    }

}