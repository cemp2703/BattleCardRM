package com.xsrsys.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xsrsys.model.Carta;

public class CartaTest {

   @Test
   public void testEquals() throws Exception{
	   Carta c1=new Carta(1, Carta.Elemento.COCO);
	   Carta c2=new Carta(1, Carta.Elemento.COCO);
	   Carta c3=new Carta(1, Carta.Elemento.CORAZON);
	   Object o=new Object();
	   assertEquals("Objeto no es carta",false,c1.equals(o));
	   assertEquals("Objetos distintos, algun valor es distinto",false,c1.equals(c3));
	   assertEquals("Objetos son identicos en contenido",true,c1.equals(c2));
   } 
	
	@Test
    public void testClone() throws Exception {
        Carta c=new Carta(1, Carta.Elemento.COCO);
        Carta vclone=(Carta)c.clone();
        assertEquals(c,vclone);
    }
   
	@Test
	public void testGetNumeroElementosCartas() throws Exception{
		assertEquals(4,Carta.getNumeroElementosCartas());
	}
}
