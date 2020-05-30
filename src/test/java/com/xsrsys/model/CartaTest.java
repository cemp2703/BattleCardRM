package Clases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartaTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testClone() throws Exception {
        Carta c=new Carta(1, Carta.ELEMENTO.COCO);
        Carta vclone=(Carta)c.clone();
        Assert.assertEquals(vclone,c);
    }
}
