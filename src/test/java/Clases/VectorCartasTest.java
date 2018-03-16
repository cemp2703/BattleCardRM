package Clases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class VectorCartasTest {


    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testVectorCartasOps() throws Exception{
        final int POS0 = 0;
        final int POS1 = 1;
        final int POS2 = 2;
        final int POSFUERADEZONA = -1;
        VectorCartas vc=new VectorCartas(Mano.MAXMANOCARDS);
        Assert.assertEquals(vc.obtenerNumerodeCartas(),0);
        Carta c0 = new Carta(1,Carta.ELEMENTO.COCO);
        Carta c2 = new Carta(3,Carta.ELEMENTO.COCO);
        Assert.assertEquals(vc.agregarCartaEnEspacioVacio(c0),POS0);
        Assert.assertEquals(vc.agregarCartaEnPos(c2,POS2),true);
        Assert.assertEquals(vc.obtenerCartaxId(POS2),c2);
        Assert.assertEquals(vc.obtenerNumerodeCartas(),2);
        Assert.assertEquals(vc.quitarCartaenPos(POSFUERADEZONA),false);
        Assert.assertEquals(vc.quitarCartaenPos(POS2),true);
        Assert.assertEquals(vc.quitarUltimaCartaDisponible(),POS0);
        Assert.assertEquals(vc.quitarUltimaCartaDisponible(),VectorCartas.NOCARTASDISPONIBLES);//No quedan cartas disponibles para quitar
    }

    @Test
    public void testClone() throws Exception {
        VectorCartas vc=new VectorCartas(Mano.MAXMANOCARDS);
        VectorCartas vclone=(VectorCartas)vc.clone();
        vc.agregarCartaEnEspacioVacio(new Carta(1,Carta.ELEMENTO.COCO));
        Assert.assertNotEquals(vclone,vc);
        vclone=(VectorCartas)vc.clone();
        Assert.assertEquals(vclone,vc);
        vc.setMaxNCartas(8);
        Assert.assertNotEquals(vclone.getMaxNCartas(),vc.getMaxNCartas());
    }

}