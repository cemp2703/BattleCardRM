import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.Component;
import java.awt.Font;

import javax.swing.SwingConstants;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.MatteBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import javax.swing.ScrollPaneConstants;

public class Juego {

	private static final int NDIFICULTADES =2;
	private static final int FACIL = 0;
	private static final int NORMAL = 1;
	
	private Controlador Controlador;
	private Jugador Jugador1;
	private Jugador Jugador2;
	private JFrame frmBattleCards;
	
	
	private int Turno;// 1: Jugador 1, 2: Jugador 2
	private int CartaPos;//posicion  de ataque, defenza, etc  almacenado temporalmente para la carta a colocar en la zona de batalla
	private int IdCartaZonaB;//id de primera posicion almacenada temporalmente
	private int IdCartaMano;//id de segunda posicion almacenada temporalmente
	private int Accion; //0: NO accion, 1: Colocando una Carta, 2: Atacando una carta
	private Integer Termino; //Reglas que finalizan el juego 0: No termino, 1: Jugador actual se quedo sin cartas, 2: Jugador Anterior perdi� todas sus barreras
	private int ModoJuego;//0: Jugador 1 vs Jugador 2, 1: Jugador vs Maquina
	private int DificultadJuego; //0: Facil(Aleatorio), 1: Normal (Primero el mejor)
	
	private Jugador JugadorActual;
	private Jugador JugadorAnterior;
	private Estado Estado;
	private Regla Regla;
	private Maquina Maquina;
	
	private Color CMano;
	private Color CBarrera;
	private Color CZonaBatalla;
	private Color CSeleccionado;
	
	private JLabel lblModo;
	private JLabel lblInicia;
	private JLabel lblDificultad;
	private JLabel lblTituloDificultad;
	private JButton btnDificultadAtras;
	private JButton btnDificultadAdelante;
	private JLabel lblJugador1;
	private JLabel lblJugador2;
	private JLabel lblJugador1Deck;
	private JLabel lblJugador2Deck;
	private JLabel lblJugador2Mano5;	
	private JLabel lblJugador2Mano4;
	private JLabel lblJugador2Mano3;
	private JLabel lblJugador2Mano2;
	private JLabel lblJugador2Mano1;
	private JLabel lblJugador1Mano5;
	private JLabel lblJugador1Mano4;
	private JLabel lblJugador1Mano3;
	private JLabel lblJugador1Mano2;
	private JLabel lblJugador1Mano1;
	private JLabel lblJugador1Barrera1;
	private JLabel lblJugador1Barrera2;
	private JLabel lblJugador1Barrera3;
	private JLabel lblJugador1Barrera4;
	private JLabel lblJugador1Barrera5;	
	private JLabel lblJugador2Barrera5;
	private JLabel lblJugador2Barrera4;
	private JLabel lblJugador2Barrera3;
	private JLabel lblJugador2Barrera2;
	private JLabel lblJugador2Barrera1;
	private JLabel lblJugador1ZonaBatalla1;
	private JLabel lblJugador1ZonaBatalla2;
	private JLabel lblJugador1ZonaBatalla3;
	private JLabel lblJugador2ZonaBatalla1;
	private JLabel lblJugador2ZonaBatalla2;	
	private JLabel lblJugador2ZonaBatalla3;
	private JLabel lblNDeckJ1;
	private JLabel lblNDeckJ2;

	private JButton btnColocarEnAtk;
	private JButton btnColocarEnDef;
	private JButton btnTerminarTurno;
	private JButton btnAtacar;
	private JButton btnCambiarPosicion;
	private JLabel lblEnfoque;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JList<String> lstMovimientos;
	DefaultListModel<String> modelo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Juego window = new Juego();
					window.frmBattleCards.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Juego() {
		CMano=new Color(0, 0, 0);
		CBarrera=new Color(139,69,19);
		CZonaBatalla=new Color(85,107,47);
		CSeleccionado=new Color(255,215,0);
		Regla=new Regla();
		Turno=1;
		ModoJuego=0;
		DificultadJuego=0;
		initialize();
	}
	
	void terminarJuego(){
			
		if(Termino==1){//Jugador Actual sin cartas que jalar en su Deck
			JOptionPane.showMessageDialog( null, JugadorActual.getNombre()+" se ha quedado sin cartas en su DECK, "+JugadorAnterior.getNombre()+" ha ganado la partida",
					JugadorAnterior.getNombre()+" Gano!!",
					JOptionPane.INFORMATION_MESSAGE );
		}
		else if(Termino==2){//Jugador Anterior se qued� sin barreras
			JOptionPane.showMessageDialog( null, JugadorAnterior.getNombre()+" se ha quedado sin barreras que lo protejan, "+JugadorActual.getNombre()+" ha ganado la partida",
					JugadorActual.getNombre()+" Gano!!",
					JOptionPane.INFORMATION_MESSAGE );
		}
		
		
		
		ModoJuego=1;
		botonCambiarModoJuego();
		((CardLayout) frmBattleCards.getContentPane().getLayout()).show(frmBattleCards.getContentPane(),"PanelInicio");
	}
	
	void enfocarCarta(MouseEvent e){
		JLabel JL=(JLabel) e.getSource();
		String JLNAME=JL.getName();
		
		boolean JACA=false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece
		
		if(JLNAME.substring(10,11).equals(Turno+"")){
			JACA=true;
		}
		else{
			JACA=false;
		}
		
		int idCarta;
		

		//lblJugador1ZonaBatalla1
		if(JLNAME.length()==23){
			idCarta=Integer.parseInt(JLNAME.substring(22));
			if(JACA){
				if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1)!=null){
					lblEnfoque.setText((JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1).getValor() +1)+" "+devuelveUnicode(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1).getElemento())+"");
					lblEnfoque.setBackground(Color.white);
				}
			}
			else{
				if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1)!=null){
					lblEnfoque.setText("?");
					lblEnfoque.setBackground(Color.white);
				}
			}
		}
		//lblJugador1Mano1
		else if(JLNAME.length()==16){
			idCarta=Integer.parseInt(JLNAME.substring(15));
			if(JACA){
				if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta-1)!=null){
					lblEnfoque.setText((JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta-1).getValor() +1)+" "+devuelveUnicode(JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta-1).getElemento())+"");
					lblEnfoque.setBackground(Color.white);
				}
			}
			else{
				if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta-1)!=null){
					lblEnfoque.setText("M");
					lblEnfoque.setBackground(Color.white);
				}
			}
		}
		//lblJugador1Barrera1
		else if(JLNAME.length()==19){
			idCarta=Integer.parseInt(JLNAME.substring(18));
			if(JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta-1)!=null){
				lblEnfoque.setText("B");
				lblEnfoque.setBackground(CBarrera);
			}
		}
		
	}
	
	
	void habilitarControlesXTurno(){
		if(Turno==1){

			lblJugador1.setEnabled(true);
			lblJugador2.setEnabled(false);
			lblJugador1Deck.setEnabled(true);
			lblJugador2Deck.setEnabled(false);
			lblJugador1Barrera1.setEnabled(true);
			lblJugador1Barrera2.setEnabled(true);
			lblJugador1Barrera3.setEnabled(true);
			lblJugador1Barrera4.setEnabled(true);
			lblJugador1Barrera5.setEnabled(true);
			lblJugador2Barrera1.setEnabled(false);
			lblJugador2Barrera2.setEnabled(false);
			lblJugador2Barrera3.setEnabled(false);
			lblJugador2Barrera4.setEnabled(false);
			lblJugador2Barrera5.setEnabled(false);
			lblJugador1Mano1.setEnabled(true);
			lblJugador1Mano2.setEnabled(true);
			lblJugador1Mano3.setEnabled(true);
			lblJugador1Mano4.setEnabled(true);
			lblJugador1Mano5.setEnabled(true);
			lblJugador2Mano1.setEnabled(false);
			lblJugador2Mano2.setEnabled(false);
			lblJugador2Mano3.setEnabled(false);
			lblJugador2Mano4.setEnabled(false);
			lblJugador2Mano5.setEnabled(false);
			lblJugador1ZonaBatalla1.setEnabled(true);
			lblJugador1ZonaBatalla2.setEnabled(true);
			lblJugador1ZonaBatalla3.setEnabled(true);
			lblJugador2ZonaBatalla1.setEnabled(false);
			lblJugador2ZonaBatalla2.setEnabled(false);
			lblJugador2ZonaBatalla3.setEnabled(false);
			
		}
		else{
			
			lblJugador1.setEnabled(false);
			lblJugador2.setEnabled(true);
			lblJugador1Deck.setEnabled(false);
			lblJugador2Deck.setEnabled(true);
			lblJugador1Barrera1.setEnabled(false);
			lblJugador1Barrera2.setEnabled(false);
			lblJugador1Barrera3.setEnabled(false);
			lblJugador1Barrera4.setEnabled(false);
			lblJugador1Barrera5.setEnabled(false);
			lblJugador2Barrera1.setEnabled(true);
			lblJugador2Barrera2.setEnabled(true);
			lblJugador2Barrera3.setEnabled(true);
			lblJugador2Barrera4.setEnabled(true);
			lblJugador2Barrera5.setEnabled(true);
			lblJugador1Mano1.setEnabled(false);
			lblJugador1Mano2.setEnabled(false);
			lblJugador1Mano3.setEnabled(false);
			lblJugador1Mano4.setEnabled(false);
			lblJugador1Mano5.setEnabled(false);
			lblJugador2Mano1.setEnabled(true);
			lblJugador2Mano2.setEnabled(true);
			lblJugador2Mano3.setEnabled(true);
			lblJugador2Mano4.setEnabled(true);
			lblJugador2Mano5.setEnabled(true);
			lblJugador1ZonaBatalla1.setEnabled(false);
			lblJugador1ZonaBatalla2.setEnabled(false);
			lblJugador1ZonaBatalla3.setEnabled(false);
			lblJugador2ZonaBatalla1.setEnabled(true);
			lblJugador2ZonaBatalla2.setEnabled(true);
			lblJugador2ZonaBatalla3.setEnabled(true);
			
			
		}
		
	}
	
	String devuelveUnicode(int n){
		switch(n){
			case 0: return "\u2665";
			case 1: return "\u2666";
			case 2: return "\u2663";
			case 3: return "\u2660";
		}
		return "";
	}
	
	void imprimirMovimiento(Estado EI, Estado EF){//Estado Inicial, Estado Final
		int idcartacolocadazb=-1;
		Carta Cartacolocada=null;
		//Buscando si se sac� una carta de la mano
		for(int i=0;i<Mano.MAXMANOCARDS;i++){
			if(EI.Jugador2.Mano.Cartas.obtenerCartaxId(i)!=EF.Jugador2.Mano.Cartas.obtenerCartaxId(i)){
				Cartacolocada=EI.Jugador2.Mano.Cartas.obtenerCartaxId(i);
				break;
			}
		}
		
		//Si se coloc� alguna carta en ZB
		boolean encontrada=false;
		if(Cartacolocada!=null){
			for(int i=0;i<ZonaBatalla.MAXZONABATALLACARDS;i++){
				if(EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null){
					if(Cartacolocada.getElemento()==EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()
							&& Cartacolocada.getValor()==EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()){
						encontrada=true;
						idcartacolocadazb=i;
					}
				}
			}
			
			if(encontrada){
				if(EF.Jugador2.ZonaBatalla.poscarta[idcartacolocadazb]==3){
					modelo.addElement("-> Carta Colocada Cara Abajo");
				}
				else{
					modelo.addElement("-> Carta "+(Cartacolocada.getValor()+1)+" "+devuelveUnicode(Cartacolocada.getElemento())+" Colocada en Ataque" );
				}
			}
			else{
				modelo.addElement("-> Carta "+(Cartacolocada.getValor()+1)+" "+devuelveUnicode(Cartacolocada.getElemento())+" Colocada en Ataque" );
				modelo.addElement("-> Carta "+(Cartacolocada.getValor()+1)+" "+devuelveUnicode(Cartacolocada.getElemento())+" Atac� y se elimin� (perdi� o empat�)" );
			}
		}
		
		for(int i=0;i<ZonaBatalla.MAXZONABATALLACARDS;i++){
			//Si sigue habiendo carta
			if(EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null && EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null){
				//Si es la misma carta
				if(EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()==EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()
						&& EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()==EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()){
					if(EI.Jugador2.ZonaBatalla.poscarta[i]>=2 && EF.Jugador2.ZonaBatalla.poscarta[i]==1){
						modelo.addElement("-> Carta "+(Cartacolocada.getValor()+1)+" "+devuelveUnicode(Cartacolocada.getElemento())+" Cambio a Ataque" );
					}
					if(EF.Jugador2.ZonaBatalla.dispataque[i]==0){
						modelo.addElement("-> Carta "+(Cartacolocada.getValor()+1)+" "+devuelveUnicode(Cartacolocada.getElemento())+" Atac� (Gan� o Empat�)");
					}
					if(EI.Jugador2.ZonaBatalla.poscarta[i]==1 && EF.Jugador2.ZonaBatalla.poscarta[i]>=2){
						modelo.addElement("-> Carta "+(Cartacolocada.getValor()+1)+" "+devuelveUnicode(Cartacolocada.getElemento())+" Cambio a Defenza" );
					}
				}
			}
			else if(EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null && EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)==null){
				if(i!=idcartacolocadazb){
					modelo.addElement("-> Carta "+(EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()+1)+" "+devuelveUnicode(EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento())+" Atac� y se elimin� (perdi� o empat�)" );
				}
			}
		}
		
		
		
		
		//lstMovimientos.
	}
	
	
	void actualizarVista(){
		if(Turno==1){
			if(Jugador1.Mano.Cartas.obtenerCartaxId(0)!=null){
				lblJugador1Mano1.setText( (Jugador1.Mano.Cartas.obtenerCartaxId(0).getValor() +1)+" "+devuelveUnicode(Jugador1.Mano.Cartas.obtenerCartaxId(0).getElemento())+"");
				lblJugador1Mano1.setBackground(Color.white);
			}
			else{
				lblJugador1Mano1.setText("X");
				lblJugador1Mano1.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(1)!=null){
				lblJugador1Mano2.setText( (Jugador1.Mano.Cartas.obtenerCartaxId(1).getValor() +1) +" "+devuelveUnicode(Jugador1.Mano.Cartas.obtenerCartaxId(1).getElemento())+"");
				lblJugador1Mano2.setBackground(Color.white);
			}
			else{
				lblJugador1Mano2.setText("X");
				lblJugador1Mano2.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(2)!=null){
				lblJugador1Mano3.setText( (Jugador1.Mano.Cartas.obtenerCartaxId(2).getValor() +1)+" "+devuelveUnicode(Jugador1.Mano.Cartas.obtenerCartaxId(2).getElemento())+"");
				lblJugador1Mano3.setBackground(Color.white);
			}
			else{
				lblJugador1Mano3.setText("X");
				lblJugador1Mano3.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(3)!=null){
				lblJugador1Mano4.setText( (Jugador1.Mano.Cartas.obtenerCartaxId(3).getValor() +1)+" "+devuelveUnicode(Jugador1.Mano.Cartas.obtenerCartaxId(3).getElemento())+"");
				lblJugador1Mano4.setBackground(Color.white);
			}
			else{
				lblJugador1Mano4.setText("X");
				lblJugador1Mano4.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(4)!=null){
				lblJugador1Mano5.setText( (Jugador1.Mano.Cartas.obtenerCartaxId(4).getValor() +1)+" "+devuelveUnicode(Jugador1.Mano.Cartas.obtenerCartaxId(4).getElemento())+"");
				lblJugador1Mano5.setBackground(Color.white);
			}
			else{
				lblJugador1Mano5.setText("X");
				lblJugador1Mano5.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(0)!=null){
				lblJugador2Mano1.setText("M");
				lblJugador2Mano1.setBackground(Color.white);
			}
			else{
				lblJugador2Mano1.setText("X");
				lblJugador2Mano1.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(1)!=null){
				lblJugador2Mano2.setText("M");
				lblJugador2Mano2.setBackground(Color.white);
			}
			else{
				lblJugador2Mano2.setText("X");
				lblJugador2Mano2.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(2)!=null){
				lblJugador2Mano3.setText("M");
				lblJugador2Mano3.setBackground(Color.white);
			}
			else{
				lblJugador2Mano3.setText("X");
				lblJugador2Mano3.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(3)!=null){
				lblJugador2Mano4.setText("M");
				lblJugador2Mano4.setBackground(Color.white);
			}
			else{
				lblJugador2Mano4.setText("X");
				lblJugador2Mano4.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(4)!=null){
				lblJugador2Mano5.setText("M");
				lblJugador2Mano5.setBackground(Color.white);
			}
			else{
				lblJugador2Mano5.setText("X");
				lblJugador2Mano5.setBackground(Color.lightGray);
			}
		}
		else{
			
			if(Jugador1.Mano.Cartas.obtenerCartaxId(0)!=null){
				lblJugador1Mano1.setText("M");
				lblJugador1Mano1.setBackground(Color.white);
			}
			else{
				lblJugador1Mano1.setText("X");
				lblJugador1Mano1.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(1)!=null){
				lblJugador1Mano2.setText("M");
				lblJugador1Mano2.setBackground(Color.white);
			}
			else{
				lblJugador1Mano2.setText("X");
				lblJugador1Mano2.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(2)!=null){
				lblJugador1Mano3.setText("M");
				lblJugador1Mano3.setBackground(Color.white);
			}
			else{
				lblJugador1Mano3.setText("X");
				lblJugador1Mano3.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(3)!=null){
				lblJugador1Mano4.setText("M");
				lblJugador1Mano4.setBackground(Color.white);
			}
			else{
				lblJugador1Mano4.setText("X");
				lblJugador1Mano4.setBackground(Color.lightGray);
			}
			if(Jugador1.Mano.Cartas.obtenerCartaxId(4)!=null){
				lblJugador1Mano5.setText("M");
				lblJugador1Mano5.setBackground(Color.white);
			}
			else{
				lblJugador1Mano5.setText("X");
				lblJugador1Mano5.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(0)!=null){
				lblJugador2Mano1.setText( (Jugador2.Mano.Cartas.obtenerCartaxId(0).getValor() +1)+" "+devuelveUnicode(Jugador2.Mano.Cartas.obtenerCartaxId(0).getElemento())+"");
				lblJugador2Mano1.setBackground(Color.white);
			}
			else{
				lblJugador2Mano1.setText("X");
				lblJugador2Mano1.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(1)!=null){
				lblJugador2Mano2.setText( (Jugador2.Mano.Cartas.obtenerCartaxId(1).getValor() +1)+" "+devuelveUnicode(Jugador2.Mano.Cartas.obtenerCartaxId(1).getElemento())+"");
				lblJugador2Mano2.setBackground(Color.white);
			}
			else{
				lblJugador2Mano2.setText("X");
				lblJugador2Mano2.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(2)!=null){
				lblJugador2Mano3.setText( (Jugador2.Mano.Cartas.obtenerCartaxId(2).getValor() +1)+" "+devuelveUnicode(Jugador2.Mano.Cartas.obtenerCartaxId(2).getElemento())+"");
				lblJugador2Mano3.setBackground(Color.white);
			}
			else{
				lblJugador2Mano3.setText("X");
				lblJugador2Mano3.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(3)!=null){
				lblJugador2Mano4.setText( (Jugador2.Mano.Cartas.obtenerCartaxId(3).getValor() +1)+" "+devuelveUnicode(Jugador2.Mano.Cartas.obtenerCartaxId(3).getElemento())+"");
				lblJugador2Mano4.setBackground(Color.white);
			}
			else{
				lblJugador2Mano4.setText("X");
				lblJugador2Mano4.setBackground(Color.lightGray);
			}
			if(Jugador2.Mano.Cartas.obtenerCartaxId(4)!=null){
				lblJugador2Mano5.setText( (Jugador2.Mano.Cartas.obtenerCartaxId(4).getValor() +1)+" "+devuelveUnicode(Jugador2.Mano.Cartas.obtenerCartaxId(4).getElemento())+"");
				lblJugador2Mano5.setBackground(Color.white);
			}
			else{
				lblJugador2Mano5.setText("X");
				lblJugador2Mano5.setBackground(Color.lightGray);
			}
		}
		
		if(! (Jugador1.Deck.obtenerNumeroElementos()==0) ){
			lblJugador1Deck.setText("DECK");
		}
		else{
			lblJugador1Deck.setText("X");
		}
		
		if(!(Jugador2.Deck.obtenerNumeroElementos()==0) ){
			lblJugador2Deck.setText("DECK");
		}
		else{
			lblJugador2Deck.setText("X");
		}
		
		if(Jugador1.Barrera.Cartas.obtenerCartaxId(0)!=null){
			lblJugador1Barrera1.setText("B");
			lblJugador1Barrera1.setBackground(CBarrera);
		}
		else{
			lblJugador1Barrera1.setText("X");
			lblJugador1Barrera1.setBackground(Color.lightGray);
		}
		if(Jugador1.Barrera.Cartas.obtenerCartaxId(1)!=null){
			lblJugador1Barrera2.setText("B");
			lblJugador1Barrera2.setBackground(CBarrera);
		}
		else{
			lblJugador1Barrera2.setText("X");
			lblJugador1Barrera2.setBackground(Color.lightGray);
		}
		if(Jugador1.Barrera.Cartas.obtenerCartaxId(2)!=null){
			lblJugador1Barrera3.setText("B");
			lblJugador1Barrera3.setBackground(CBarrera);
		}
		else{
			lblJugador1Barrera3.setText("X");
			lblJugador1Barrera3.setBackground(Color.lightGray);
		}
		if(Jugador1.Barrera.Cartas.obtenerCartaxId(3)!=null){
			lblJugador1Barrera4.setText("B");
			lblJugador1Barrera4.setBackground(CBarrera);
		}
		else{
			lblJugador1Barrera4.setText("X");
			lblJugador1Barrera4.setBackground(Color.lightGray);
		}
		if(Jugador1.Barrera.Cartas.obtenerCartaxId(4)!=null){
			lblJugador1Barrera5.setText("B");
			lblJugador1Barrera5.setBackground(CBarrera);
		}
		else{
			lblJugador1Barrera5.setText("X");
			lblJugador1Barrera5.setBackground(Color.lightGray);
		}
		
		if(Jugador2.Barrera.Cartas.obtenerCartaxId(0)!=null){
			lblJugador2Barrera1.setText("B");
			lblJugador2Barrera1.setBackground(CBarrera);
		}
		else{
			lblJugador2Barrera1.setText("X");
			lblJugador2Barrera1.setBackground(Color.lightGray);
		}
		if(Jugador2.Barrera.Cartas.obtenerCartaxId(1)!=null){
			lblJugador2Barrera2.setText("B");
			lblJugador2Barrera2.setBackground(CBarrera);
		}
		else{
			lblJugador2Barrera2.setText("X");
			lblJugador2Barrera2.setBackground(Color.lightGray);
		}
		if(Jugador2.Barrera.Cartas.obtenerCartaxId(2)!=null){
			lblJugador2Barrera3.setText("B");
			lblJugador2Barrera3.setBackground(CBarrera);
		}
		else{
			lblJugador2Barrera3.setText("X");
			lblJugador2Barrera3.setBackground(Color.lightGray);
		}
		if(Jugador2.Barrera.Cartas.obtenerCartaxId(3)!=null){
			lblJugador2Barrera4.setText("B");
			lblJugador2Barrera4.setBackground(CBarrera);
		}
		else{
			lblJugador2Barrera4.setText("X");
			lblJugador2Barrera4.setBackground(Color.lightGray);
		}
		if(Jugador2.Barrera.Cartas.obtenerCartaxId(4)!=null){
			lblJugador2Barrera5.setText("B");
			lblJugador2Barrera5.setBackground(CBarrera);
		}
		else{
			lblJugador2Barrera5.setText("X");
			lblJugador2Barrera5.setBackground(Color.lightGray);
		}
		
		if(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(0)!=null){
			lblJugador1ZonaBatalla1.setText( (Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(0).getValor() +1)+" "+devuelveUnicode(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(0).getElemento()));
			if(Jugador1.ZonaBatalla.poscarta[0]==1){
				lblJugador1ZonaBatalla1.setBackground(Color.RED);
			}
			else if(Jugador1.ZonaBatalla.poscarta[0]==2){
				lblJugador1ZonaBatalla1.setBackground(Color.GREEN);
			}
			else{
				lblJugador1ZonaBatalla1.setText("?");
				lblJugador1ZonaBatalla1.setBackground(Color.GREEN);
			}
		}
		else{
			lblJugador1ZonaBatalla1.setText("X");
			lblJugador1ZonaBatalla1.setBackground(Color.BLACK);
			lblJugador1ZonaBatalla1.setForeground(Color.white);
		}
		
		if(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(1)!=null){
			lblJugador1ZonaBatalla2.setText( (Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(1).getValor() +1)+" "+devuelveUnicode(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(1).getElemento()));
			if(Jugador1.ZonaBatalla.poscarta[1]==1){
				lblJugador1ZonaBatalla2.setBackground(Color.RED);
			}
			else if(Jugador1.ZonaBatalla.poscarta[1]==2){
				lblJugador1ZonaBatalla2.setBackground(Color.GREEN);
			}
			else{
				lblJugador1ZonaBatalla2.setText("?");
				lblJugador1ZonaBatalla2.setBackground(Color.GREEN);
			}
		}
		else{
			lblJugador1ZonaBatalla2.setText("X");
			lblJugador1ZonaBatalla2.setBackground(Color.BLACK);
			lblJugador1ZonaBatalla2.setForeground(Color.white);
		}
		
		if(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(2)!=null){
			lblJugador1ZonaBatalla3.setText( (Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(2).getValor() +1)+" "+devuelveUnicode(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(2).getElemento()));
			if(Jugador1.ZonaBatalla.poscarta[2]==1){
				lblJugador1ZonaBatalla3.setBackground(Color.RED);
			}
			else if(Jugador1.ZonaBatalla.poscarta[2]==2){
				lblJugador1ZonaBatalla3.setBackground(Color.GREEN);
			}
			else{
				lblJugador1ZonaBatalla3.setText("?");
				lblJugador1ZonaBatalla3.setBackground(Color.GREEN);
			}
		}
		else{
			lblJugador1ZonaBatalla3.setText("X");
			lblJugador1ZonaBatalla3.setBackground(Color.BLACK);
			lblJugador1ZonaBatalla3.setForeground(Color.white);
		}
		
		
		if(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(0)!=null){
			lblJugador2ZonaBatalla1.setText( (Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(0).getValor() +1)+" "+devuelveUnicode(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(0).getElemento()));
			if(Jugador2.ZonaBatalla.poscarta[0]==1){
				lblJugador2ZonaBatalla1.setBackground(Color.RED);
			}
			else if(Jugador2.ZonaBatalla.poscarta[0]==2){
				lblJugador2ZonaBatalla1.setBackground(Color.GREEN);
			}
			else{
				lblJugador2ZonaBatalla1.setText("?");
				lblJugador2ZonaBatalla1.setBackground(Color.GREEN);
			}
		}
		else{
			lblJugador2ZonaBatalla1.setText("X");
			lblJugador2ZonaBatalla1.setBackground(Color.BLACK);
			lblJugador2ZonaBatalla1.setForeground(Color.white);
		}
		if(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(1)!=null){
			lblJugador2ZonaBatalla2.setText( (Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(1).getValor() +1)+" "+devuelveUnicode(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(1).getElemento()));
			if(Jugador2.ZonaBatalla.poscarta[1]==1){
				lblJugador2ZonaBatalla2.setBackground(Color.RED);
			}
			else if(Jugador2.ZonaBatalla.poscarta[1]==2){
				lblJugador2ZonaBatalla2.setBackground(Color.GREEN);
			}
			else{
				lblJugador2ZonaBatalla2.setText("?");
				lblJugador2ZonaBatalla2.setBackground(Color.GREEN);
			}
		}
		else{
			lblJugador2ZonaBatalla2.setText("X");
			lblJugador2ZonaBatalla2.setBackground(Color.BLACK);
			lblJugador2ZonaBatalla2.setForeground(Color.white);
		}
		
		if(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(2)!=null){
			lblJugador2ZonaBatalla3.setText( (Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(2).getValor() +1)+" "+devuelveUnicode(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(2).getElemento()));
			if(Jugador2.ZonaBatalla.poscarta[2]==1){
				lblJugador2ZonaBatalla3.setBackground(Color.RED);
			}
			else if(Jugador2.ZonaBatalla.poscarta[2]==2){
				lblJugador2ZonaBatalla3.setBackground(Color.GREEN);
			}
			else{
				lblJugador2ZonaBatalla3.setText("?");
				lblJugador2ZonaBatalla3.setBackground(Color.GREEN);
			}
		}
		else{
			lblJugador2ZonaBatalla3.setText("X");
			lblJugador2ZonaBatalla3.setBackground(Color.BLACK);
			lblJugador2ZonaBatalla3.setForeground(Color.white);
		}
		
	}
	
	void botonCambiarJugadorInicial(){
		if(ModoJuego==0){
			if(Turno==1){
				Turno=2;
				lblInicia.setText("Jugador 2");
			}
			else{
				Turno=1;
				lblInicia.setText("Jugador 1");
			}	
		}
		else{//Modo Jugador vs Maquina
			if(Turno==1){
				Turno=2;
				lblInicia.setText("M�quina");
			}
			else{
				Turno=1;
				lblInicia.setText("Jugador");
			}	
		}
	}	
	
	void botonCambiarModoJuego(){
		Turno=1;
		if(ModoJuego==0){
			//Maquina vs Jugador
			
			DificultadJuego=0;
			ModoJuego=1;
			
			lblModo.setText("Jugador vs M�quina");
			lblDificultad.setText("Facil");
			lblInicia.setText("Jugador");
			
			lblDificultad.setVisible(true);
			lblDificultad.setEnabled(true);
			btnDificultadAdelante.setEnabled(true);
			btnDificultadAdelante.setVisible(true);
			btnDificultadAtras.setEnabled(false);
			btnDificultadAtras.setVisible(true);
			lblTituloDificultad.setVisible(true);
			lblTituloDificultad.setEnabled(true);
		
		}
		else{
			
			//Jugador1 vs Jugador2
			
			ModoJuego=0;
			
			
			lblModo.setText("Jugador vs Jugador");
			lblInicia.setText("Jugador 1");
			
			lblDificultad.setVisible(false);
			lblDificultad.setEnabled(false);
			btnDificultadAdelante.setEnabled(false);
			btnDificultadAdelante.setVisible(false);
			btnDificultadAtras.setEnabled(false);
			btnDificultadAtras.setVisible(false);
			lblTituloDificultad.setVisible(false);
			lblTituloDificultad.setEnabled(false);
		}	
	}
	
	void botonCambiarDificultad(int sumando){
		if(ModoJuego==1){
			if(DificultadJuego + sumando==0){
				DificultadJuego=0;
				lblDificultad.setText("Facil");
				btnDificultadAtras.setEnabled(false);
				btnDificultadAdelante.setEnabled(true);
			}
			else if(DificultadJuego + sumando==1){
				DificultadJuego=1;
				lblDificultad.setText("Normal");
				btnDificultadAdelante.setEnabled(true);
				btnDificultadAtras.setEnabled(true);
			}
			else if(DificultadJuego + sumando==2){
				DificultadJuego=1;
				lblDificultad.setText("Experto");
				btnDificultadAdelante.setEnabled(false);
				btnDificultadAtras.setEnabled(true);
			}
			else{
				btnDificultadAtras.setEnabled(true);
				btnDificultadAdelante.setEnabled(true);
			}
		}
		
	}
	
	void botonIniciarJuego(){
		
		if(ModoJuego==0){
			lblJugador2.setText("Jugador 2");
			lblJugador1.setText("Jugador 1");
			Jugador1=new Jugador("Jugador 1");
			Jugador2=new Jugador("Jugador 2");
		}
		else{
			lblJugador1.setText("Jugador");
			lblJugador2.setText("M�quina");
			Jugador1=new Jugador("Jugador");
			Jugador2=new Jugador("Maquina");
		}
	
		Controlador=new Controlador();
		Maquina=new Maquina();
		Termino=0;
		Estado=new Estado(Jugador2,Jugador1,0,Turno,Termino);
		Controlador.repartirCartas(Jugador1);
		Controlador.repartirCartas(Jugador2);
		

		Accion=0;
		
		if(Turno==1){
			JugadorActual=Jugador1;
			JugadorAnterior=Jugador2;
			Jugador1.contarTurno();
		}
		else{
			JugadorActual=Jugador2;
			JugadorAnterior=Jugador1;
			Jugador2.contarTurno();
		}
		
		modelo.clear();
		modelo.addElement("Turno: "+JugadorActual.getNombre());
		
		
		btnAtacar.setEnabled(false);
		btnColocarEnAtk.setEnabled(false);
		btnColocarEnDef.setEnabled(false);
		btnCambiarPosicion.setEnabled(false);
		lblNDeckJ1.setText(Jugador1.Deck.Deck.size()+"");
		lblNDeckJ2.setText(Jugador2.Deck.Deck.size()+"");
		
		
		JugadorActual.ZonaBatalla.renovarDisponibilidades();
		habilitarControlesXTurno();
		actualizarVista();
		
		
		((CardLayout) frmBattleCards.getContentPane().getLayout()).show(frmBattleCards.getContentPane(),"PanelJuego");
		
		if(ModoJuego==1 && Turno==2 && Termino==0){
			EleccionMaquina();
			accionTerminarTurno();
		}
	}
	
	
	void botonTerminarTurno(){
		if(btnTerminarTurno.isEnabled()){
			btnColocarEnAtk.setEnabled(false);
			btnColocarEnDef.setEnabled(false);
			btnAtacar.setEnabled(false);
			btnCambiarPosicion.setEnabled(false);
			DeSeleccionarMano();
			DeSeleccionarZonaBatalla();
			
			accionTerminarTurno();
			
		}
	}
	
	void botonCambiarPosicionBatalla(){
		if(btnCambiarPosicion.isEnabled()){
			if(Accion==0){
				
				Regla.accionCambiarPosicionBatalla(JugadorActual,IdCartaZonaB-1);
				
				btnCambiarPosicion.setEnabled(false);
				btnAtacar.setEnabled(false);
				DeSeleccionarZonaBatalla();
				actualizarVista();
			}
		}
	}
	
	void botonColocarEnAtk(){
		if(btnColocarEnAtk.isEnabled()){	
			if(Accion==1 && CartaPos==1){ //Cancelar Colocar Carta en Ataque
				btnColocarEnAtk.setText("Colocar en ATK");
				CartaPos=0;
				Accion=0;
				btnColocarEnAtk.setEnabled(false);
				btnColocarEnDef.setEnabled(false);
				DeSeleccionarMano();
			}
			else if(Accion==1 && CartaPos==3){ //Cambiar a Colocar Carta en Ataque
				CartaPos=1;
				btnColocarEnDef.setText("Colocar en DEF");
				btnColocarEnAtk.setText("Cancelar");
			}
			else{
				CartaPos=1;
				Accion=1;
				
				btnColocarEnAtk.setText("Cancelar");
			}
		}
	}
	
	void botonColocarEnDef(){
		if(btnColocarEnDef.isEnabled()){
			if(Accion==1 && CartaPos==3){ //Cancelar Colocar Carta en Defenza
				btnColocarEnDef.setText("Colocar en DEF");
				CartaPos=0;
				Accion=0;
				btnColocarEnAtk.setEnabled(false);
				btnColocarEnDef.setEnabled(false);
				DeSeleccionarMano();
			}
			else if(Accion==1 && CartaPos==1){ //Cambiar a Colocar Carta en Defenza
				CartaPos=3;
				btnColocarEnAtk.setText("Colocar en ATK");
				btnColocarEnDef.setText("Cancelar");
			}
			else{
				CartaPos=3;
				Accion=1;
				
				btnColocarEnDef.setText("Cancelar");
			}
		}
	}

	void botonAtacar(){
		if(btnAtacar.isEnabled()){
			if(Accion==0){//Atacar
				if(JugadorAnterior.ZonaBatalla.Cartas.obtenerNumerodeCartas()==0){///Atacar a la barrera
					int resp;
					resp=Regla.accionAtacarBarrera(JugadorActual, JugadorAnterior,IdCartaZonaB-1);
						
					btnAtacar.setEnabled(false);
					btnCambiarPosicion.setEnabled(false);
					actualizarVista();
					DeSeleccionarZonaBatalla();
					if(resp==3){
						Termino=2;
						terminarJuego();
					}
					
				}
				else{//Atacar a una carta
					Accion=2;
					btnAtacar.setText("Cancelar");
					btnCambiarPosicion.setEnabled(false);
				}
				
			}
			else if(Accion==2){//Cancelar el ataque en curso
				
				Accion=0;
				btnAtacar.setText("Atacar");
				btnAtacar.setEnabled(false);
				DeSeleccionarZonaBatalla();
			}
			
		}
		
	}
	
	void DeSeleccionarMano(){
		MatteBorder B=new MatteBorder(2, 2, 2, 2, (Color) CMano); 
		if(Turno==1){
			lblJugador1Mano1.setBorder(B);
			lblJugador1Mano2.setBorder(B);
			lblJugador1Mano3.setBorder(B);
			lblJugador1Mano4.setBorder(B);
			lblJugador1Mano5.setBorder(B);
		}
		else{
			lblJugador2Mano1.setBorder(B);
			lblJugador2Mano2.setBorder(B);
			lblJugador2Mano3.setBorder(B);
			lblJugador2Mano4.setBorder(B);
			lblJugador2Mano5.setBorder(B);
		}
	}
	
	void DeSeleccionarZonaBatalla(){
		MatteBorder B=new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla); 
		if(Turno==1){
			lblJugador1ZonaBatalla1.setBorder(B);
			lblJugador1ZonaBatalla2.setBorder(B);
			lblJugador1ZonaBatalla3.setBorder(B);
		}
		else{
			lblJugador2ZonaBatalla1.setBorder(B);
			lblJugador2ZonaBatalla2.setBorder(B);
			lblJugador2ZonaBatalla3.setBorder(B);
		}
	}
	
	
	void ManoSeleccionada(MouseEvent e){
		JLabel JL=(JLabel) e.getSource();
		//lblJugador1Mano5
		String JLNAME=JL.getName();
		boolean JACA=false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece
		
		if(JLNAME.substring(10,11).equals(Turno+"")){
			JACA=true;
		}
		else{
			JACA=false;
		}
		
		
		
		if(Accion==0){
			IdCartaMano=Integer.parseInt(JLNAME.substring(15));
			if(JugadorActual.Mano.Cartas.obtenerCartaxId(IdCartaMano-1)!=null){//Si hay carta en ese espacio
				if(JACA){
					if(JugadorActual.ZonaBatalla.Cartas.obtenerNumerodeCartas()<ZonaBatalla.MAXZONABATALLACARDS){
						DeSeleccionarMano();
						DeSeleccionarZonaBatalla();
						btnAtacar.setEnabled(false);
						btnCambiarPosicion.setEnabled(false);
						JL.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CSeleccionado));
						if(!JugadorActual.ZonaBatalla.isCartacolocada()){
							btnColocarEnAtk.setEnabled(true);
							btnColocarEnDef.setEnabled(true);
						}
					}
				}
			}
		}
		
	}
	
	void resultadoAtaque(int idCartaZBJAnt,int idCartaZBJAct){//Muestra visualmente resultados
		int valorJugadorActual,valorJugadorAnterior;
		
		valorJugadorActual=JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZBJAct).getValor();
		valorJugadorAnterior=JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZBJAnt).getValor();
		
		if(valorJugadorActual  > valorJugadorAnterior ){
			JOptionPane.showMessageDialog( null, JugadorActual.getNombre()+" ("+ (valorJugadorActual+1) +")"
					+ " > "+ JugadorAnterior.getNombre()+" ("+ (valorJugadorAnterior+1)+ ")",
					"Resultado de Batalla",
					JOptionPane.INFORMATION_MESSAGE );
		}
		else if(valorJugadorActual  < valorJugadorAnterior){
			JOptionPane.showMessageDialog( null,JugadorActual.getNombre()+" ("+ (valorJugadorActual+1) +")"
					+ " < "+JugadorAnterior.getNombre()+" ("+ (valorJugadorAnterior+1)+ ")",
					"Resultado de Batalla",
					JOptionPane.INFORMATION_MESSAGE );
		}
		else{
			JOptionPane.showMessageDialog( null, JugadorActual.getNombre()+" ("+ (valorJugadorActual+1) +")"
					+ " = "+JugadorAnterior.getNombre()+" ("+ (valorJugadorAnterior+1)+ ")",
					"Resultado de Batalla",
					JOptionPane.INFORMATION_MESSAGE );
		}
	}
	
	void accionTerminarTurno(){
		if(Turno==1){
			Turno=2;
			JugadorActual=Jugador2;
			JugadorAnterior=Jugador1;
			Jugador2.contarTurno();
		}
		else{
			Turno=1;
			JugadorActual=Jugador1;
			JugadorAnterior=Jugador2;
			Jugador1.contarTurno();
		}
		
		Estado.setTurno(Turno);
		
		if( JugadorActual.Mano.Cartas.obtenerNumerodeCartas()<Mano.MAXMANOCARDS && JugadorActual.Deck.obtenerNumeroElementos()>0){
			//Jugador con menos cartas del maximo o con cartas iguales al maximo (Deck tiene cartas) Agrega una carata a su mano
			JugadorActual.Mano.Cartas.agregarCartaEnEspacioVacio(JugadorActual.Deck.sacarUnaCarta());
		}
		
		JugadorActual.ZonaBatalla.renovarDisponibilidades();
		
		
		habilitarControlesXTurno();
		actualizarVista();
		
		if(JugadorActual.Deck.obtenerNumeroElementos()==0){//Deck sin cartas
			Termino=1;
			terminarJuego();
		}
		
		Accion=0;
		
		lblNDeckJ1.setText(Jugador1.Deck.Deck.size()+"");
		lblNDeckJ2.setText(Jugador2.Deck.Deck.size()+"");
		
		
		modelo.addElement("---------------------------------------------");
		modelo.addElement("Turno: "+JugadorActual.getNombre());
		
		if(ModoJuego==1 && Turno==2 && Termino==0){
			EleccionMaquina();
			accionTerminarTurno();
		}
	}
	
	void ZonaBatallaSeleccionada(MouseEvent e){
		JLabel JL=(JLabel) e.getSource();
		String JLNAME=JL.getName();
		//lblJugador1ZonaBatalla1
		boolean JACA=false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece
		
		int idCarta;
		
		if(JLNAME.substring(10,11).equals(Turno+"")){
			JACA=true;
		}
		else{
			JACA=false;
		}
		
		//if(JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1)!=null){//Si existe carta en dicha posicion
		
			if(Accion==1){
				//Si se est� colocando una carta, esta confirmado que estas en tu propio turno
				if(JACA){
					idCarta=Integer.parseInt(JLNAME.substring(22));
					if(JugadorActual.ZonaBatalla.Cartas.obtenerNumerodeCartas()<=ZonaBatalla.MAXZONABATALLACARDS){
						if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1)==null ){
							//Colocar carta
							
							Regla.accionColocarCarta(JugadorActual,idCarta-1,IdCartaMano-1,CartaPos);
							
							Accion=0;
							btnColocarEnAtk.setEnabled(false);
							btnColocarEnAtk.setText("Colocar en ATK");
							btnColocarEnDef.setEnabled(false);
							btnColocarEnDef.setText("Colocar en DEF");
							btnTerminarTurno.setEnabled(true);
							DeSeleccionarMano();
							
							actualizarVista();
							enfocarCarta(e);
						}
					}
				}
			}
			else if(Accion==0){//Si no hay alguna accion en progreso
				if(JACA){//Jugador Actual toca una carta (o espacio de carta) que le pertenece
					IdCartaZonaB=Integer.parseInt(JLNAME.substring(22));
					if(JugadorActual.getNTurnos()>1){
					
						if(JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(IdCartaZonaB-1)!=null){//Si existe carta en dicha posicion
							DeSeleccionarZonaBatalla();
							DeSeleccionarMano();
							btnColocarEnAtk.setEnabled(false);
							btnColocarEnDef.setEnabled(false);
							JL.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CSeleccionado));
							if(JugadorActual.ZonaBatalla.dispataque[IdCartaZonaB-1]==1){//Si la carta est� activa para atacar						
								if(JugadorActual.ZonaBatalla.poscarta[IdCartaZonaB-1]==1)
									btnAtacar.setEnabled(true);
								else
									btnAtacar.setEnabled(false);
							}
							else{
								btnAtacar.setEnabled(false);
							}
							if(JugadorActual.ZonaBatalla.dispcambio[IdCartaZonaB-1]==1){
								btnCambiarPosicion.setEnabled(true);
							}
							else{
								btnCambiarPosicion.setEnabled(false);
							}
						}
					}
				}
			}
			else if(Accion==2){//Si se est� atacando a una carta 
				idCarta=Integer.parseInt(JLNAME.substring(22));
				
				if(JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1)!=null){//Si existe carta en dicha posicion
					
					
					if(!JACA){ //Jugador Actual toca una carta (o espacio de carta) que no le pertenece
						
						if(JugadorAnterior.ZonaBatalla.poscarta[idCarta-1]==3){//Jugador Atacado En defenza cara abajo
							JugadorAnterior.ZonaBatalla.poscarta[idCarta-1]=2;
							actualizarVista();
						}
						int resp;
						resultadoAtaque(idCarta-1,IdCartaZonaB-1);
						
						resp=Regla.accionAtacarCarta(JugadorActual, JugadorAnterior,idCarta-1,IdCartaZonaB-1);
						
						
						Accion=0;
						btnAtacar.setEnabled(false);
						btnAtacar.setText("Atacar");
						DeSeleccionarZonaBatalla();
						actualizarVista();
						
						if(resp==3){
							Termino=2;
							terminarJuego();
						}
					}
					
				}
			}
		//}
	}
	
	void EleccionMaquina(){
		Estado EI,EF;//Estado Inicial y Final del turno
		
		Estado.Jugador1=Jugador1.clone();
		Estado.Jugador2=Jugador2.clone();
		EI=Estado;
		Estado.imprimeEstado();
		Maquina.cargarEstado(Estado);
		Maquina.EstadosGenerados=Maquina.sistemaDeProduccion(Regla,Estado);
		
		if(DificultadJuego==0){
			Estado=Maquina.estrategiaRandom(Maquina.EstadosGenerados).clone();
		}
		else if(DificultadJuego==1){
			Estado=Maquina.estrategiaPrimeroElMejor(Maquina.EstadosGenerados,1).clone();
		}
		else if(DificultadJuego==2){
			Estado=Maquina.estrategiaMinMax(Maquina.EstadosGenerados).clone();
		}
		EF=Estado;
		Estado.imprimeEstado();
		imprimirMovimiento(EI, EF);
		Jugador1=Estado.Jugador1.clone();
		Jugador2=Estado.Jugador2.clone();
		Termino=Estado.getTermino();
		
		actualizarVista();
		if(Termino>0){
			terminarJuego();
		}
		else{
			JOptionPane.showMessageDialog( null, "La M�quina ha jugado, empieza tu nuevo turno",
					"M�quina Jug�",
					JOptionPane.INFORMATION_MESSAGE );
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmBattleCards = new JFrame();
		frmBattleCards.setTitle("Battle Cards");
		frmBattleCards.setResizable(false);
		frmBattleCards.setBounds(100, 100, 800, 600);
		frmBattleCards.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBattleCards.getContentPane().setLayout(new CardLayout(0, 0));
		
		
		
		JPanel PanelInicio = new JPanel();
		PanelInicio.setBackground(SystemColor.activeCaption);
		frmBattleCards.getContentPane().add(PanelInicio, "PanelInicio");
		
		JButton btnIniciarJuego = new JButton("Iniciar Juego");
		btnIniciarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonIniciarJuego();
			}
		});
		btnIniciarJuego.setFont(new Font("Wide Latin", Font.BOLD, 11));
		
		JLabel lbl456 = new JLabel("Modo");
		lbl456.setFont(new Font("Viner Hand ITC", Font.ITALIC, 17));
		
		JButton btnModoAtras = new JButton("<");
		btnModoAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonCambiarModoJuego();
			}
		});
		
		lblModo = new JLabel("Jugador vs Jugador");
		lblModo.setHorizontalAlignment(SwingConstants.CENTER);
		lblModo.setFont(new Font("Snap ITC", Font.PLAIN, 14));
		
		JButton btnModoAdelante = new JButton(">");
		btnModoAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonCambiarModoJuego();
			}
		});
		
		JLabel lblTitulo2 = new JLabel("Inicia");
		lblTitulo2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo2.setFont(new Font("Viner Hand ITC", Font.ITALIC, 17));
		
		JButton btnIniciaAdelante = new JButton(">");
		btnIniciaAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonCambiarJugadorInicial();
			}
		});
		
		lblInicia = new JLabel("Jugador 1");
		lblInicia.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicia.setFont(new Font("Snap ITC", Font.PLAIN, 14));
		
		JButton btnIniciaAtras = new JButton("<");
		btnIniciaAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonCambiarJugadorInicial();
			}
		});

		
		JLabel lblBattleCards = new JLabel("Battle Cards");
		lblBattleCards.setFont(new Font("Vivaldi", Font.PLAIN, 50));
		
		btnDificultadAdelante = new JButton(">");
		btnDificultadAdelante.setVisible(false);
		btnDificultadAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnDificultadAdelante.isEnabled()){
					botonCambiarDificultad(1);
				}
			}
		});
		btnDificultadAdelante.setEnabled(false);
		
		lblDificultad = new JLabel("Facil");
		lblDificultad.setVisible(false);
		lblDificultad.setEnabled(false);
		lblDificultad.setHorizontalAlignment(SwingConstants.CENTER);
		lblDificultad.setFont(new Font("Snap ITC", Font.PLAIN, 14));
		
		btnDificultadAtras = new JButton("<");
		btnDificultadAtras.setVisible(false);
		btnDificultadAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnDificultadAtras.isEnabled()){
					botonCambiarDificultad(-1);
				}
			}
		});
		btnDificultadAtras.setEnabled(false);
		
		lblTituloDificultad = new JLabel("Dificultad");
		lblTituloDificultad.setVisible(false);
		lblTituloDificultad.setEnabled(false);
		lblTituloDificultad.setFont(new Font("Viner Hand ITC", Font.ITALIC, 17));
		GroupLayout gl_PanelInicio = new GroupLayout(PanelInicio);
		gl_PanelInicio.setHorizontalGroup(
			gl_PanelInicio.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PanelInicio.createSequentialGroup()
					.addContainerGap(254, Short.MAX_VALUE)
					.addGroup(gl_PanelInicio.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PanelInicio.createSequentialGroup()
							.addGap(108)
							.addComponent(lblTitulo2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PanelInicio.createSequentialGroup()
							.addGap(108)
							.addComponent(lblTituloDificultad, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_PanelInicio.createSequentialGroup()
							.addGap(37)
							.addComponent(lblBattleCards))
						.addGroup(gl_PanelInicio.createSequentialGroup()
							.addGap(71)
							.addComponent(btnIniciarJuego))
						.addGroup(gl_PanelInicio.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_PanelInicio.createSequentialGroup()
								.addComponent(btnIniciaAtras, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addGap(35)
								.addComponent(lblInicia, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnIniciaAdelante, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_PanelInicio.createSequentialGroup()
								.addComponent(btnDificultadAtras, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addGap(36)
								.addComponent(lblDificultad, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDificultadAdelante, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_PanelInicio.createSequentialGroup()
								.addComponent(btnModoAtras)
								.addGap(36)
								.addComponent(lblModo)
								.addGap(37)
								.addComponent(btnModoAdelante, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))
					.addGap(226))
				.addGroup(gl_PanelInicio.createSequentialGroup()
					.addContainerGap(389, Short.MAX_VALUE)
					.addComponent(lbl456, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(356))
		);
		gl_PanelInicio.setVerticalGroup(
			gl_PanelInicio.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PanelInicio.createSequentialGroup()
					.addGap(71)
					.addComponent(lblBattleCards)
					.addGap(27)
					.addComponent(lbl456)
					.addGap(18)
					.addGroup(gl_PanelInicio.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblModo)
						.addComponent(btnModoAtras)
						.addComponent(btnModoAdelante))
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(lblTituloDificultad, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_PanelInicio.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDificultad, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDificultadAtras)
						.addComponent(btnDificultadAdelante))
					.addGap(22)
					.addComponent(lblTitulo2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_PanelInicio.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInicia, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnIniciaAtras)
						.addComponent(btnIniciaAdelante))
					.addGap(54)
					.addComponent(btnIniciarJuego)
					.addGap(99))
		);
		PanelInicio.setLayout(gl_PanelInicio);
		
		
		MouseAdapter MAManoSeleccionada= new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ManoSeleccionada(e);
			}
			public void mouseEntered(MouseEvent e) {
				enfocarCarta(e);
			}
		};
		
		MouseAdapter MAZonaBatallaSeleccionada=new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 ZonaBatallaSeleccionada(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				enfocarCarta(e);
			}
		};
	
		MouseAdapter MABarreraSeleccionada=new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				enfocarCarta(e);
			}
		};
		
		
		JPanel PanelJuego = new JPanel();
		PanelJuego.setBackground(SystemColor.activeCaption);
		frmBattleCards.getContentPane().add(PanelJuego, "PanelJuego");
		
		lblJugador2Mano5 = new JLabel("");
		lblJugador2Mano5.setOpaque(true);
		lblJugador2Mano5.addMouseListener(MAManoSeleccionada);
		lblJugador2Mano5.setName("lblJugador2Mano5");
		lblJugador2Mano5.addMouseListener(MAManoSeleccionada);
		lblJugador2Mano5.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Mano5.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Mano5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador2Mano4 = new JLabel("");
		lblJugador2Mano4.setOpaque(true);
		lblJugador2Mano4.addMouseListener(MAManoSeleccionada);
		lblJugador2Mano4.setName("lblJugador2Mano4");
		lblJugador2Mano4.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Mano4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Mano4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador2Mano3 = new JLabel("");
		lblJugador2Mano3.setOpaque(true);
		lblJugador2Mano3.addMouseListener(MAManoSeleccionada);
		lblJugador2Mano3.setName("lblJugador2Mano3");
		lblJugador2Mano3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Mano3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Mano3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador2Mano2 = new JLabel("");
		lblJugador2Mano2.setOpaque(true);
		lblJugador2Mano2.addMouseListener(MAManoSeleccionada);
		lblJugador2Mano2.setName("lblJugador2Mano2");
		lblJugador2Mano2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Mano2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Mano2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador2Mano1 = new JLabel("");
		lblJugador2Mano1.setOpaque(true);
		lblJugador2Mano1.addMouseListener(MAManoSeleccionada);
		lblJugador2Mano1.setName("lblJugador2Mano1");
		lblJugador2Mano1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Mano1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Mano1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador1Mano5 = new JLabel("");
		lblJugador1Mano5.setOpaque(true);
		lblJugador1Mano5.addMouseListener(MAManoSeleccionada);
		lblJugador1Mano5.setName("lblJugador1Mano5");
		lblJugador1Mano5.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Mano5.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Mano5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador1Mano4 = new JLabel("");
		lblJugador1Mano4.setOpaque(true);
		lblJugador1Mano4.addMouseListener(MAManoSeleccionada);
		lblJugador1Mano4.setName("lblJugador1Mano4");
		lblJugador1Mano4.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Mano4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Mano4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador1Mano3 = new JLabel("");
		lblJugador1Mano3.setOpaque(true);
		lblJugador1Mano3.addMouseListener(MAManoSeleccionada);
		lblJugador1Mano3.setName("lblJugador1Mano3");
		lblJugador1Mano3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Mano3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Mano3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador1Mano2 = new JLabel("");
		lblJugador1Mano2.setOpaque(true);
		lblJugador1Mano2.addMouseListener(MAManoSeleccionada);
		lblJugador1Mano2.setName("lblJugador1Mano2");
		lblJugador1Mano2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Mano2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Mano2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		
		lblJugador1Mano1 = new JLabel("");
		lblJugador1Mano1.setOpaque(true);
		lblJugador1Mano1.setName("lblJugador1Mano1");
		lblJugador1Mano1.addMouseListener(MAManoSeleccionada);
		lblJugador1Mano1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Mano1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Mano1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
		
		lblJugador1Barrera1 = new JLabel("");
		lblJugador1Barrera1.addMouseListener(MABarreraSeleccionada);
		lblJugador1Barrera1.setOpaque(true);

		lblJugador1Barrera1.setName("lblJugador1Barrera1");
		lblJugador1Barrera1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Barrera1.setAlignmentY(Component.TOP_ALIGNMENT);
		lblJugador1Barrera1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Barrera1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador1Barrera2 = new JLabel("");
		lblJugador1Barrera2.addMouseListener(MABarreraSeleccionada);
		lblJugador1Barrera2.setOpaque(true);
		

		lblJugador1Barrera2.setName("lblJugador1Barrera2");
		lblJugador1Barrera2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Barrera2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador1Barrera3 = new JLabel("");
		lblJugador1Barrera3.addMouseListener(MABarreraSeleccionada);
		lblJugador1Barrera3.setOpaque(true);

		lblJugador1Barrera3.setName("lblJugador1Barrera3");
		lblJugador1Barrera3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Barrera3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Barrera3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador1Barrera4 = new JLabel("");
		lblJugador1Barrera4.addMouseListener(MABarreraSeleccionada);
		lblJugador1Barrera4.setOpaque(true);

		lblJugador1Barrera4.setName("lblJugador1Barrera4");
		lblJugador1Barrera4.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Barrera4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Barrera4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador1Barrera5 = new JLabel("");
		lblJugador1Barrera5.addMouseListener(MABarreraSeleccionada);
		lblJugador1Barrera5.setOpaque(true);

		lblJugador1Barrera5.setName("lblJugador1Barrera5");
		lblJugador1Barrera5.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Barrera5.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Barrera5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador2Barrera5 = new JLabel("");
		lblJugador2Barrera5.addMouseListener(MABarreraSeleccionada);
		lblJugador2Barrera5.setOpaque(true);

		lblJugador2Barrera5.setName("lblJugador2Barrera5");
		lblJugador2Barrera5.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Barrera5.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Barrera5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador2Barrera4 = new JLabel("");
		lblJugador2Barrera4.addMouseListener(MABarreraSeleccionada);
		lblJugador2Barrera4.setOpaque(true);

		lblJugador2Barrera4.setName("lblJugador2Barrera4");
		lblJugador2Barrera4.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Barrera4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Barrera4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador2Barrera3 = new JLabel("");
		lblJugador2Barrera3.addMouseListener(MABarreraSeleccionada);
		lblJugador2Barrera3.setOpaque(true);

		lblJugador2Barrera3.setName("lblJugador2Barrera3");
		lblJugador2Barrera3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Barrera3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Barrera3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador2Barrera2 = new JLabel("");
		lblJugador2Barrera2.addMouseListener(MABarreraSeleccionada);
		lblJugador2Barrera2.setOpaque(true);

		lblJugador2Barrera2.setName("lblJugador2Barrera2");
		lblJugador2Barrera2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Barrera2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Barrera2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador2Barrera1 = new JLabel("");
		lblJugador2Barrera1.addMouseListener(MABarreraSeleccionada);
		lblJugador2Barrera1.setOpaque(true);

		lblJugador2Barrera1.setName("lblJugador2Barrera1");
		lblJugador2Barrera1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Barrera1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Barrera1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
		
		lblJugador1ZonaBatalla1 = new JLabel("");
		lblJugador1ZonaBatalla1.setOpaque(true);
		lblJugador1ZonaBatalla1.addMouseListener(MAZonaBatallaSeleccionada);
		lblJugador1ZonaBatalla1.setName("lblJugador1ZonaBatalla1");
		lblJugador1ZonaBatalla1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1ZonaBatalla1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1ZonaBatalla1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
		lblJugador1ZonaBatalla1.setBackground(SystemColor.menu);
		
		
		
		lblJugador1ZonaBatalla2 = new JLabel("");
		lblJugador1ZonaBatalla2.setOpaque(true);
		lblJugador1ZonaBatalla2.addMouseListener(MAZonaBatallaSeleccionada);
		lblJugador1ZonaBatalla2.setName("lblJugador1ZonaBatalla2");
		lblJugador1ZonaBatalla2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1ZonaBatalla2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1ZonaBatalla2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
		lblJugador1ZonaBatalla2.setBackground(SystemColor.menu);
		
		lblJugador1ZonaBatalla3 = new JLabel("");
		lblJugador1ZonaBatalla3.setOpaque(true);
		lblJugador1ZonaBatalla3.addMouseListener(MAZonaBatallaSeleccionada);
		lblJugador1ZonaBatalla3.setName("lblJugador1ZonaBatalla3");
		lblJugador1ZonaBatalla3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1ZonaBatalla3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1ZonaBatalla3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
		lblJugador1ZonaBatalla3.setBackground(SystemColor.menu);
		
		lblJugador2ZonaBatalla1 = new JLabel("");
		lblJugador2ZonaBatalla1.setOpaque(true);
		lblJugador2ZonaBatalla1.addMouseListener(MAZonaBatallaSeleccionada);
		lblJugador2ZonaBatalla1.setName("lblJugador2ZonaBatalla1");
		lblJugador2ZonaBatalla1.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2ZonaBatalla1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2ZonaBatalla1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
		lblJugador2ZonaBatalla1.setBackground(SystemColor.menu);
		
		lblJugador2ZonaBatalla2 = new JLabel("");
		lblJugador2ZonaBatalla2.setOpaque(true);
		lblJugador2ZonaBatalla2.addMouseListener(MAZonaBatallaSeleccionada);
		lblJugador2ZonaBatalla2.setName("lblJugador2ZonaBatalla2");
		lblJugador2ZonaBatalla2.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2ZonaBatalla2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2ZonaBatalla2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
		lblJugador2ZonaBatalla2.setBackground(SystemColor.menu);
		
		lblJugador2ZonaBatalla3 = new JLabel("");
		lblJugador2ZonaBatalla3.setOpaque(true);
		lblJugador2ZonaBatalla3.addMouseListener(MAZonaBatallaSeleccionada);
		lblJugador2ZonaBatalla3.setName("lblJugador2ZonaBatalla3");
		lblJugador2ZonaBatalla3.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2ZonaBatalla3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2ZonaBatalla3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
		lblJugador2ZonaBatalla3.setBackground(SystemColor.menu);
		
		lblJugador1Deck = new JLabel("");
		lblJugador1Deck.setOpaque(true);
		lblJugador1Deck.setName("lblJugador1Deck");
		lblJugador1Deck.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador1Deck.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador1Deck.setBorder(new LineBorder(new Color(191, 205, 219), 3));
		lblJugador1Deck.setBackground(Color.WHITE);
		
		lblJugador2Deck = new JLabel("");
		lblJugador2Deck.setOpaque(true);
		lblJugador2Deck.setName("lblJugador2Deck");
		lblJugador2Deck.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugador2Deck.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblJugador2Deck.setBorder(new LineBorder(new Color(191, 205, 219), 3));
		lblJugador2Deck.setBackground(Color.WHITE);
		
		lblJugador2 = new JLabel("Jugador 2");
		lblJugador2.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		lblJugador1 = new JLabel("Jugador 1");
		lblJugador1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JPanel panelDivisor = new JPanel();
		panelDivisor.setBackground(new Color(0, 0, 0));
		
		btnColocarEnAtk = new JButton("Colocar en ATK");
		btnColocarEnAtk.setBackground(Color.RED);
		btnColocarEnAtk.setFocusable(false);
		btnColocarEnAtk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				botonColocarEnAtk();
			}
		});
		btnColocarEnAtk.setEnabled(false);
		
		btnColocarEnDef = new JButton("Colocar en DEF");
		btnColocarEnDef.setBackground(Color.GREEN);
		btnColocarEnDef.setFocusable(false);
		btnColocarEnDef.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				botonColocarEnDef();
			}
		});
		btnColocarEnDef.setEnabled(false);
		
		btnTerminarTurno = new JButton("TerminarTurno");
		btnTerminarTurno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				botonTerminarTurno();
			}
		});
		
		btnAtacar = new JButton("Atacar");
		btnAtacar.setEnabled(false);
		btnAtacar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				botonAtacar();
			}
		});
		
		btnCambiarPosicion = new JButton("Cambiar Posici\u00F3n");
		btnCambiarPosicion.setEnabled(false);
		btnCambiarPosicion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				botonCambiarPosicionBatalla();
			}
		});
		
		lblNDeckJ1 = new JLabel("52");
		lblNDeckJ1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblNDeckJ2 = new JLabel("52");
		lblNDeckJ2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblEnfoque = new JLabel("");
		lblEnfoque.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		lblEnfoque.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnfoque.setHorizontalTextPosition(SwingConstants.CENTER);
		lblEnfoque.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		lblEnfoque.setOpaque(true);
		
		lstMovimientos = new JList<String>();
		lstMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		modelo = new DefaultListModel<String>();
		
		lstMovimientos.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		
		lstMovimientos.setModel(modelo);
		
		lblNewLabel = new JLabel("Movimientos");
		lblNewLabel.setVisible(false);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(lstMovimientos);
		
		
		GroupLayout gl_PanelTablero = new GroupLayout(PanelJuego);
		gl_PanelTablero.setHorizontalGroup(
			gl_PanelTablero.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PanelTablero.createSequentialGroup()
					.addGap(47)
					.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJugador2)
						.addGroup(gl_PanelTablero.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnTerminarTurno, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnColocarEnAtk, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnCambiarPosicion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnColocarEnDef, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAtacar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblJugador1, Alignment.LEADING)))
					.addGap(29)
					.addGroup(gl_PanelTablero.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_PanelTablero.createSequentialGroup()
							.addComponent(lblJugador2Deck, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(14))
						.addGroup(gl_PanelTablero.createSequentialGroup()
							.addComponent(lblNDeckJ2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
							.addGap(18)))
					.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PanelTablero.createSequentialGroup()
							.addComponent(lblJugador1Barrera1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_PanelTablero.createSequentialGroup()
									.addComponent(lblJugador1ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador1ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador1ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_PanelTablero.createSequentialGroup()
									.addComponent(lblJugador1Barrera2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador1Barrera3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador1Barrera4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador1Barrera5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_PanelTablero.createSequentialGroup()
									.addComponent(lblJugador2ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador2ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblJugador2ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
							.addGap(34)
							.addComponent(lblNDeckJ1))
						.addGroup(gl_PanelTablero.createSequentialGroup()
							.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panelDivisor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_PanelTablero.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_PanelTablero.createSequentialGroup()
										.addComponent(lblJugador1Mano1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador1Mano2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador1Mano3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador1Mano4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador1Mano5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_PanelTablero.createSequentialGroup()
										.addComponent(lblJugador2Mano5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblJugador2Mano4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblJugador2Mano3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblJugador2Mano2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblJugador2Mano1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_PanelTablero.createSequentialGroup()
										.addComponent(lblJugador2Barrera5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador2Barrera4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador2Barrera3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador2Barrera2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(lblJugador2Barrera1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))))
							.addGap(18)
							.addComponent(lblJugador1Deck, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
					.addGap(17)
					.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(lblEnfoque, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_PanelTablero.setVerticalGroup(
			gl_PanelTablero.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PanelTablero.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PanelTablero.createSequentialGroup()
							.addGap(84)
							.addComponent(lblJugador2)
							.addPreferredGap(ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
							.addComponent(btnCambiarPosicion)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnColocarEnAtk)
							.addGap(6)
							.addComponent(btnTerminarTurno)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnColocarEnDef)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAtacar)
							.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
							.addComponent(lblJugador1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGap(64))
						.addGroup(gl_PanelTablero.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_PanelTablero.createSequentialGroup()
									.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
										.addComponent(lblJugador2Mano1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJugador2Mano2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJugador2Mano3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJugador2Mano4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJugador2Mano5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblJugador2Deck, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_PanelTablero.createSequentialGroup()
											.addGroup(gl_PanelTablero.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_PanelTablero.createSequentialGroup()
													.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
														.addComponent(lblJugador2Barrera5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador2Barrera4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador2Barrera3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador2Barrera2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador2Barrera1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
													.addGroup(gl_PanelTablero.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblJugador2ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador2ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador2ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
													.addGap(18)
													.addComponent(panelDivisor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
														.addComponent(lblJugador1ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador1ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador1ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
													.addGap(54)
													.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
														.addComponent(lblJugador1Barrera1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador1Barrera2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador1Barrera3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador1Barrera4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblJugador1Barrera5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
												.addComponent(lblNDeckJ1))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_PanelTablero.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_PanelTablero.createParallelGroup(Alignment.LEADING)
													.addComponent(lblJugador1Mano1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblJugador1Mano2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblJugador1Mano3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblJugador1Mano4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblJugador1Mano5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
												.addComponent(lblJugador1Deck, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblNDeckJ2, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_PanelTablero.createSequentialGroup()
									.addComponent(lblEnfoque, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel)
									.addGap(18)
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))))
					.addGap(50))
		);
		PanelJuego.setLayout(gl_PanelTablero);
	}
}
