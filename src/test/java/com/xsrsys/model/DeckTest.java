package com.xsrsys.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.xsrsys.model.Carta;
import com.xsrsys.model.Deck;

public class DeckTest {

    @Test
    public void testDeckOps() throws Exception {
        Deck d=new Deck();
        Carta CNULL= null;
        Carta c=new Carta(3,Carta.Elemento.ESPADA);
        assertEquals(null,d.sacarUnaCarta());//no hay cartas que sacar
        assertEquals(false,d.agregarUnaCarta(CNULL));//El objeto carta es nulo
        assertEquals(true,d.agregarUnaCarta(c));
        assertEquals(c,d.sacarUnaCarta());
        for (int i=0;i<Deck.MAXDECK;i++)
        {
            d.agregarUnaCarta(c);
        }
        assertEquals(false,d.agregarUnaCarta(c));//no hay espacio para agregar mas cartas
    }


    @Test
    public void testClone() throws Exception {
        Deck d=new Deck();
        Deck dclone=(Deck)d.clone();
        Carta c=new Carta(6,Carta.Elemento.ESPADA);
        d.agregarUnaCarta(c);
        assertNotEquals(d,dclone);
        dclone=(Deck)d.clone();
        assertEquals(d,dclone);
    }

}
