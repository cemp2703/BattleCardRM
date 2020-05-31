package com.xsrsys.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xsrsys.model.Carta;

public class CartaTest {

    @Test
    public void testClone() throws Exception {
        Carta c=new Carta(1, Carta.ELEMENTO.COCO);
        Carta vclone=(Carta)c.clone();
        assertEquals(c,vclone);
    }
}
