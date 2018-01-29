package Clases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TestNGSimpleTest {
    @BeforeClass
    public void setUp() {
        // code that will be invoked when this test is instantiated
    }

    /*
    @Test
    public void testAdd() {
        String str = "TestNG is working fine";
//        AssertEquals("TestNG is working fine", str);
    }
    */

    @Test(groups = { "fast" })
    public void aFastTest() {
        System.out.println("Fast test");
    }

    @Test(groups = { "slow" })
    public void aSlowTest() {
        System.out.println("Slow test");
    }


}
