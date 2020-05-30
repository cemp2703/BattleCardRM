package Clases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeckTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testDeckOps() throws Exception {
        Deck d=new Deck();
        Carta CNULL= null;
        Carta c=new Carta(Carta.ELEMENTO.ESPADA,3);
        Assert.assertEquals(d.sacarUnaCarta(),null);//no hay cartas que sacar
        Assert.assertEquals(d.agregarUnaCarta(CNULL),false);//El objeto carta es nulo
        Assert.assertEquals(d.agregarUnaCarta(c),true);
        Assert.assertEquals(d.sacarUnaCarta(),c);
        for (int i=0;i<Deck.MAXDECK;i++)
        {
            d.agregarUnaCarta(c);
        }
        Assert.assertEquals(d.agregarUnaCarta(c),false);//no hay espacio para agregar mas cartas
    }


    @Test
    public void testClone() throws Exception {
        Deck d=new Deck();
        Deck dclone=(Deck)d.clone();
        Carta c=new Carta(Carta.ELEMENTO.ESPADA,6);
        d.agregarUnaCarta(c);
        Assert.assertNotEquals(d,dclone);
        dclone=(Deck)d.clone();
        Assert.assertEquals(d,dclone);
    }

}
