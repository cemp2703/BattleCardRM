package Clases;

import java.util.Random;


public class Controlador {
	
	public final int MAXVALORCARTA = 13;
	public final int MAXNUMEROELEMENTOCARTAS = 4;
	
	boolean cartaselegidas[][];
	public Controlador(){
		cartaselegidas=new boolean[MAXNUMEROELEMENTOCARTAS][MAXVALORCARTA];
	}
	
	public void repartirCartas(Jugador jug){
		Random rm;
		int n,m,maximoendeck,cartasrepartidas;
		
		maximoendeck=(jug.Deck.MAXDECK - Mano.MAXMANOCARDS - Barrera.MAXBARRERACARDS);
		
		for(int i=0;i<cartaselegidas.length;i++){
			for(int j=0;j<cartaselegidas[i].length;j++){
				cartaselegidas[i][j] = false;
			}
		}
		
		cartasrepartidas=0;
		
		while(cartasrepartidas < jug.Deck.MAXDECK ){
			rm=new Random();
			n=rm.nextInt(MAXNUMEROELEMENTOCARTAS);
			rm=new Random();
			m=rm.nextInt(MAXVALORCARTA);
			
			if(cartaselegidas[n][m]==false){
				cartaselegidas[n][m]=true;
				
				cartasrepartidas++;
				
				if(jug.Barrera.Cartas.obtenerNumerodeCartas()<Barrera.MAXBARRERACARDS){
					jug.Barrera.Cartas.agregarCartaEnEspacioVacio(new Carta(n,m+1,Carta.USO.BARRERA));
				}
				else if(jug.Mano.Cartas.obtenerNumerodeCartas()<Mano.MAXMANOCARDS){
					jug.Mano.Cartas.agregarCartaEnEspacioVacio(new Carta(n,m+1,Carta.USO.MANO));
				}
				else if(jug.Deck.obtenerNumeroElementos()< maximoendeck ){
					jug.Deck.agregarUnaCarta(new Carta(n,m+1,Carta.USO.DECK));
				}
				
			}
		}
	}
}
