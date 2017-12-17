import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.*;
import java.util.*;
import java.io.*;

public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * boolean cartasposibles[][];
		cartasposibles=new boolean[4][13];
		
		
		for(int i=0;i<cartasposibles.length;i++){
			for(int j=0;j<cartasposibles[i].length;j++){
				System.out.print(cartasposibles[i][j]+" ");
			}
			System.out.println("");
		}
		
		Random rm=new Random();
		while(true){
			System.out.println(rm.nextInt(14));
		}
		
		
		Vector<Integer> f=new Vector<Integer>();
		
		f.add(5);
		f.add(6);
		f.add(56);
		f.add(8);
		f.size();
		f.remove(2);
		
		for(int i=0;i<f.size();i++){
			System.out.println(f.toArray()[i]);
		}
		
		

		JOptionPane.showMessageDialog( null, "Hola, Batman.",
				"JOptionPane/Ejemplo de MessageDialog",
				JOptionPane.INFORMATION_MESSAGE );
		
		*/
		
		
		//System.out.print("\u2665");
		/*
		System.out.println("List of ASCII Characters are given below: ");
        
        for(int i = 0; i <= 500; i++)
        {
             System.out.format("%1$-5d", i);
             System.out.format("%1$-2c", (char) i);
             System.out.println();
        }
        
        System.out.println("\u2665");
        
        */
		
		Jugador J1=new Jugador("Jugador 1");
		Jugador J2=new Jugador("Jugador 2");
		Controlador C=new Controlador();
		C.repartirCartas(J1);
		C.repartirCartas(J2);
		/*
		Estado E=new Estado(J2,J1);
		
		Estado E2=E.clone();
		
		E2.JugadorHumano.ZonaBatalla.cartacolocada=true;
		
		if(E2.JugadorHumano.ZonaBatalla.cartacolocada == E.JugadorHumano.ZonaBatalla.cartacolocada){
			int t=3;
		}
		else{
			int t=2;
		}
		*/
		int Turno=2;
		Regla R=new Regla();
		Estado E=new Estado(J2,J1,0,Turno,0);
		Maquina M=new Maquina();
		J2.ZonaBatalla.renovarDisponibilidades();
		M.cargarEstado(E);
		M.sistemaDeProduccion(R,E);
		
		/*
		Carta c=new Carta();
		Carta c2=c.clone();
		c.setElemento(33);
		c2.setElemento(21);
		System.out.print(c.getElemento()+" "+c2.getElemento());
		*/
		/*
		VectorCartas v1=new VectorCartas(3);
		VectorCartas v2=v1.clone();
		v1.agregarCartaEnEspacioVacio(new Carta(3,3,3));
		v2.agregarCartaEnEspacioVacio(new Carta(4,4,4));
		System.out.print(v1.obtenerCartaxId(0).getElemento()+" "+v2.obtenerCartaxId(0).getElemento());
		
		*/
		
		/*
		Deck d1=new Deck();
		Deck d2=d1.clone();
		d1.agregarUnaCarta(new Carta(3,4,5));
		d2.agregarUnaCarta(new Carta(6,7,8));
		System.out.print(d1.Deck.get(0).getElemento()+" "+d2.Deck.get(0).getElemento());
		*/
		
		/*
		Mano m1=new Mano();
		Mano m2=m1.clone();
		m1.Cartas.agregarCartaEnEspacioVacio(new Carta(1,2,3));
		m2.Cartas.agregarCartaEnEspacioVacio(new Carta(2,3,4));
		System.out.print(m1.Cartas.obtenerCartaxId(0).getElemento()+" "+m2.Cartas.obtenerCartaxId(0).getElemento());
		*/
		
		/*
		ZonaBatalla z1=new ZonaBatalla();
		ZonaBatalla z2=z1.clone();
		z1.dispataque[0]=4;
		z2.dispataque[0]=1;
		System.out.print(z1.dispataque[0]+" "+z2.dispataque[0]);
		
		*/
	}

}
