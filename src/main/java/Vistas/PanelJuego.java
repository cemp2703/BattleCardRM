package Vistas;

import Clases.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelJuego extends JPanel {
    private JButton btnColocarEnAtk;
    private JButton btnColocarEnDef;
    private JButton btnTerminarTurno;
    private JButton btnAtacar;
    private JButton btnCambiarPosicion;

    private JLabel lblNDeckJ1;
    private JLabel lblNDeckJ2;
    private JLabel lblEnfoque;
    private JLabel lblNewLabel;
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

    private Color CMano;
    private Color CBarrera;
    private Color CZonaBatalla;
    private Color CSeleccionado;

    private JList lstHistorial;
    private DefaultListModel mhistorial;
    private JScrollPane scrollPane;

    private Juego JuegoN;
    private JFrame JF;

    PanelJuego(JFrame pJF, Juego pJuego){
        JuegoN=pJuego;
        JF=pJF;

        CMano=new Color(0, 0, 0);
        CBarrera=new Color(139,69,19);
        CZonaBatalla=new Color(85,107,47);
        CSeleccionado=new Color(255,215,0);

        MouseAdapter MAManoSeleccionada = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ManoSeleccionada(e);
            }

            public void mouseEntered(MouseEvent e) {
                enfocarCarta(e);
            }
        };

        MouseAdapter MAZonaBatallaSeleccionada = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ZonaBatallaSeleccionada(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                enfocarCarta(e);
            }
        };

        MouseAdapter MABarreraSeleccionada = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enfocarCarta(e);
            }
        };

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

        lstHistorial = new JList();
        lstHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mhistorial = new DefaultListModel();

        lstHistorial.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));

        lstHistorial.setModel(mhistorial);

        lblNewLabel = new JLabel("Movimientos");
        lblNewLabel.setVisible(false);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(lstHistorial);

        //region gl_PanelTablero
        GroupLayout gl_PanelTablero = new GroupLayout(this);
        gl_PanelTablero.setHorizontalGroup(
                gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                .addGap(47)
                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblJugador2)
                                        .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(btnTerminarTurno, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnColocarEnAtk, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnCambiarPosicion, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnColocarEnDef, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAtacar, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblJugador1, GroupLayout.Alignment.LEADING)))
                                .addGap(29)
                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addComponent(lblJugador2Deck, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                .addGap(14))
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addComponent(lblNDeckJ2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)))
                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addComponent(lblJugador1Barrera1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(lblJugador1ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador1ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador1ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(lblJugador1Barrera2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador1Barrera3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador1Barrera4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador1Barrera5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(lblJugador2ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador2ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblJugador2ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(34)
                                                .addComponent(lblNDeckJ1))
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(panelDivisor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
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
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lblJugador2Mano4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lblJugador2Mano3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lblJugador2Mano2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
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
                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblNewLabel)
                                        .addComponent(lblEnfoque, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                                .addContainerGap())
        );
        gl_PanelTablero.setVerticalGroup(
                gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                .addGap(33)
                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addGap(84)
                                                .addComponent(lblJugador2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                                                .addComponent(btnCambiarPosicion)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnColocarEnAtk)
                                                .addGap(6)
                                                .addComponent(btnTerminarTurno)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnColocarEnDef)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAtacar)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                                .addComponent(lblJugador1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                                .addGap(64))
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblJugador2Mano1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblJugador2Mano2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblJugador2Mano3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblJugador2Mano4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblJugador2Mano5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblJugador2Deck, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(lblJugador2Barrera5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador2Barrera4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador2Barrera3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador2Barrera2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador2Barrera1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                                        .addComponent(lblJugador2ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador2ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador2ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18)
                                                                                                .addComponent(panelDivisor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18)
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(lblJugador1ZonaBatalla2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador1ZonaBatalla1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador1ZonaBatalla3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(54)
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(lblJugador1Barrera1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador1Barrera2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador1Barrera3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador1Barrera4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(lblJugador1Barrera5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
                                                                                        .addComponent(lblNDeckJ1))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                        .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(lblJugador1Mano1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblJugador1Mano2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblJugador1Mano3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblJugador1Mano4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblJugador1Mano5, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                        .addComponent(lblJugador1Deck, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
                                                                        .addComponent(lblNDeckJ2, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(lblEnfoque, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblNewLabel)
                                                                .addGap(18)
                                                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))))
                                .addGap(50))
        );

        /**
         * Initialize the contents of the frame.
         */



        //endregion
        this.setLayout(gl_PanelTablero);

    }

    void terminarJuego() {

        if (JuegoN.Estado.Termino == Estado.TERMINO.SINCARTAS) {//Jugador Actual sin cartas que jalar en su Deck
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " se ha quedado sin cartas en su DECK, " + JuegoN.JugadorAnterior.getNombre() + " ha ganado la partida",
                    JuegoN.JugadorAnterior.getNombre() + " Gano!!",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (JuegoN.Estado.Termino == Estado.TERMINO.SINBARRERAS) {//Jugador Anterior se qued� sin barreras
            JOptionPane.showMessageDialog(null, JuegoN.JugadorAnterior.getNombre() + " se ha quedado sin barreras que lo protejan, " + JuegoN.JugadorActual.getNombre() + " ha ganado la partida",
                    JuegoN.JugadorActual.getNombre() + " Gano!!",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        ((CardLayout) JF.getContentPane().getLayout()).show(JF.getContentPane(), "PanelInicio");
    }

    void enfocarCarta(MouseEvent e) {
        JLabel JL = (JLabel) e.getSource();
        String JLNAME = JL.getName();

        boolean JACA = false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece

        if (JLNAME.substring(10, 11).equals(JuegoN.Estado.getTurno() + "")) {
            JACA = true;
        } else {
            JACA = false;
        }

        int idCarta;


        //ZonaBatalla
        if (JLNAME.length() == 23) {
            idCarta = Integer.parseInt(JLNAME.substring(22));
            if (JACA) {
                if (JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta - 1) != null) {
                    lblEnfoque.setText(JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta - 1).getValor() + " " +
                            Carta.devuelveUnicode(JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta - 1).getElemento()) + "");
                    lblEnfoque.setBackground(Color.white);
                }
            } else {
                if (JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta - 1) != null) {
                    lblEnfoque.setText("?");
                    lblEnfoque.setBackground(Color.white);
                }
            }
        }
        //Mano
        else if (JLNAME.length() == 16) {
            idCarta = Integer.parseInt(JLNAME.substring(15));
            if (JACA) {
                if (JuegoN.JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta - 1) != null) {
                    lblEnfoque.setText(JuegoN.JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta - 1).getValor() + " " +
                            Carta.devuelveUnicode(JuegoN.JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta - 1).getElemento()) + "");
                    lblEnfoque.setBackground(Color.white);
                }
            } else {
                if (JuegoN.JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta - 1) != null) {
                    lblEnfoque.setText("M");
                    lblEnfoque.setBackground(Color.white);
                }
            }
        }
        //Barrera
        else if (JLNAME.length() == 19) {
            idCarta = Integer.parseInt(JLNAME.substring(18));
            if (JuegoN.JugadorActual.Mano.Cartas.obtenerCartaxId(idCarta - 1) != null) {
                lblEnfoque.setText("B");
                lblEnfoque.setBackground(CBarrera);
            }
        }

    }

    void habilitarControlesXTurno() {
        if (JuegoN.Estado.getTurno() == Estado.TURNO.JUGADOR1) {

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

        } else {

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

    void imprimirMovimiento(Estado EI, Estado EF) {//Estado Inicial, Estado Final
        int idcartacolocadazb = -1;
        Carta Cartacolocada = null;
        //Buscando si se sac� una carta de la mano
        for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
            if (EI.Jugador2.Mano.Cartas.obtenerCartaxId(i) != EF.Jugador2.Mano.Cartas.obtenerCartaxId(i)) {
                Cartacolocada = EI.Jugador2.Mano.Cartas.obtenerCartaxId(i);
                break;
            }
        }

        //Si se coloc� alguna carta en ZB
        boolean encontrada = false;
        if (Cartacolocada != null) {
            for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
                if (EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i) != null) {
                    if (Cartacolocada.getElemento() == EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()
                            && Cartacolocada.getValor() == EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()) {
                        encontrada = true;
                        idcartacolocadazb = i;
                    }
                }
            }

            if (encontrada) {
                if (EF.Jugador2.ZonaBatalla.poscarta[idcartacolocadazb] == ZonaBatalla.POSCARTA.DEFCARAABAJO) {
                    mhistorial.addElement("-> Carta Colocada Cara Abajo");
                } else {
                    mhistorial.addElement("-> Carta " + Cartacolocada.getValor() + " " + Carta.devuelveUnicode(Cartacolocada.getElemento()) + " Colocada en Ataque");
                }
            } else {
                mhistorial.addElement("-> Carta " + Cartacolocada.getValor() + " " + Carta.devuelveUnicode(Cartacolocada.getElemento()) + " Colocada en Ataque");
                mhistorial.addElement("-> Carta " + Cartacolocada.getValor() + " " + Carta.devuelveUnicode(Cartacolocada.getElemento()) + " Atac� y se elimin� (perdi� o empat�)");
            }
        }

        for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
            //Si sigue habiendo carta
            if (EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i) != null && EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i) != null) {
                //Si es la misma carta
                if (EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento() == EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()
                        && EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor() == EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()) {
                    if (EI.Jugador2.ZonaBatalla.poscarta[i] >= ZonaBatalla.POSCARTA.DEFCARAARRIBA && EF.Jugador2.ZonaBatalla.poscarta[i] == ZonaBatalla.POSCARTA.ATAQUE) {
                        mhistorial.addElement("-> Carta " + Cartacolocada.getValor() + " " + Carta.devuelveUnicode(Cartacolocada.getElemento()) + " Cambio a Ataque");
                    }
                    if (EF.Jugador2.ZonaBatalla.dispataque[i] == ZonaBatalla.DISPATAQUE.NODISPONIBLE) {
                        mhistorial.addElement("-> Carta " + Cartacolocada.getValor()  + " " + Carta.devuelveUnicode(Cartacolocada.getElemento()) + " Atac� (Gan� o Empat�)");
                    }
                    if (EI.Jugador2.ZonaBatalla.poscarta[i] == ZonaBatalla.POSCARTA.ATAQUE && EF.Jugador2.ZonaBatalla.poscarta[i] >= ZonaBatalla.POSCARTA.DEFCARAARRIBA) { //jug2 en def cara arriba o cara abajo
                        mhistorial.addElement("-> Carta " + Cartacolocada.getValor() + " " + Carta.devuelveUnicode(Cartacolocada.getElemento()) + " Cambio a Defenza");
                    }
                }
            } else if (EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i) != null && EF.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i) == null) {
                if (i != idcartacolocadazb) {
                    mhistorial.addElement("-> Carta " + EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor() + " " + Carta.devuelveUnicode(EI.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()) + " Atac� y se elimin� (perdi� o empat�)");
                }
            }
        }
    }

    void actualizarVista() {
        if (JuegoN.Estado.getTurno() == Estado.TURNO.JUGADOR1) {
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(0) != null) {
                lblJugador1Mano1.setText(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(0).getValor()  + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(0).getElemento()) + "");
                lblJugador1Mano1.setBackground(Color.white);
            } else {
                lblJugador1Mano1.setText("X");
                lblJugador1Mano1.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(1) != null) {
                lblJugador1Mano2.setText(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(1).getValor()  + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(1).getElemento()) + "");
                lblJugador1Mano2.setBackground(Color.white);
            } else {
                lblJugador1Mano2.setText("X");
                lblJugador1Mano2.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(2) != null) {
                lblJugador1Mano3.setText(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(2).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(2).getElemento()) + "");
                lblJugador1Mano3.setBackground(Color.white);
            } else {
                lblJugador1Mano3.setText("X");
                lblJugador1Mano3.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(3) != null) {
                lblJugador1Mano4.setText(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(3).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(3).getElemento()) + "");
                lblJugador1Mano4.setBackground(Color.white);
            } else {
                lblJugador1Mano4.setText("X");
                lblJugador1Mano4.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(4) != null) {
                lblJugador1Mano5.setText(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(4).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(4).getElemento()) + "");
                lblJugador1Mano5.setBackground(Color.white);
            } else {
                lblJugador1Mano5.setText("X");
                lblJugador1Mano5.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(0) != null) {
                lblJugador2Mano1.setText("M");
                lblJugador2Mano1.setBackground(Color.white);
            } else {
                lblJugador2Mano1.setText("X");
                lblJugador2Mano1.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(1) != null) {
                lblJugador2Mano2.setText("M");
                lblJugador2Mano2.setBackground(Color.white);
            } else {
                lblJugador2Mano2.setText("X");
                lblJugador2Mano2.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(2) != null) {
                lblJugador2Mano3.setText("M");
                lblJugador2Mano3.setBackground(Color.white);
            } else {
                lblJugador2Mano3.setText("X");
                lblJugador2Mano3.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(3) != null) {
                lblJugador2Mano4.setText("M");
                lblJugador2Mano4.setBackground(Color.white);
            } else {
                lblJugador2Mano4.setText("X");
                lblJugador2Mano4.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(4) != null) {
                lblJugador2Mano5.setText("M");
                lblJugador2Mano5.setBackground(Color.white);
            } else {
                lblJugador2Mano5.setText("X");
                lblJugador2Mano5.setBackground(Color.lightGray);
            }
        } else {

            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(0) != null) {
                lblJugador1Mano1.setText("M");
                lblJugador1Mano1.setBackground(Color.white);
            } else {
                lblJugador1Mano1.setText("X");
                lblJugador1Mano1.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(1) != null) {
                lblJugador1Mano2.setText("M");
                lblJugador1Mano2.setBackground(Color.white);
            } else {
                lblJugador1Mano2.setText("X");
                lblJugador1Mano2.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(2) != null) {
                lblJugador1Mano3.setText("M");
                lblJugador1Mano3.setBackground(Color.white);
            } else {
                lblJugador1Mano3.setText("X");
                lblJugador1Mano3.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(3) != null) {
                lblJugador1Mano4.setText("M");
                lblJugador1Mano4.setBackground(Color.white);
            } else {
                lblJugador1Mano4.setText("X");
                lblJugador1Mano4.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador1.Mano.Cartas.obtenerCartaxId(4) != null) {
                lblJugador1Mano5.setText("M");
                lblJugador1Mano5.setBackground(Color.white);
            } else {
                lblJugador1Mano5.setText("X");
                lblJugador1Mano5.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(0) != null) {
                lblJugador2Mano1.setText(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(0).getValor()  + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(0).getElemento()) + "");
                lblJugador2Mano1.setBackground(Color.white);
            } else {
                lblJugador2Mano1.setText("X");
                lblJugador2Mano1.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(1) != null) {
                lblJugador2Mano2.setText(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(1).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(1).getElemento()) + "");
                lblJugador2Mano2.setBackground(Color.white);
            } else {
                lblJugador2Mano2.setText("X");
                lblJugador2Mano2.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(2) != null) {
                lblJugador2Mano3.setText(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(2).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(2).getElemento()) + "");
                lblJugador2Mano3.setBackground(Color.white);
            } else {
                lblJugador2Mano3.setText("X");
                lblJugador2Mano3.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(3) != null) {
                lblJugador2Mano4.setText(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(3).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(3).getElemento()) + "");
                lblJugador2Mano4.setBackground(Color.white);
            } else {
                lblJugador2Mano4.setText("X");
                lblJugador2Mano4.setBackground(Color.lightGray);
            }
            if (JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(4) != null) {
                lblJugador2Mano5.setText(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(4).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.Estado.Jugador2.Mano.Cartas.obtenerCartaxId(4).getElemento()) + "");
                lblJugador2Mano5.setBackground(Color.white);
            } else {
                lblJugador2Mano5.setText("X");
                lblJugador2Mano5.setBackground(Color.lightGray);
            }
        }

        if (!(JuegoN.Estado.Jugador1.Deck.obtenerNumeroElementos() == 0)) {
            lblJugador1Deck.setText("DECK");
        } else {
            lblJugador1Deck.setText("X");
        }

        if (!(JuegoN.Estado.Jugador2.Deck.obtenerNumeroElementos() == 0)) {
            lblJugador2Deck.setText("DECK");
        } else {
            lblJugador2Deck.setText("X");
        }

        if (JuegoN.Estado.Jugador1.Barrera.Cartas.obtenerCartaxId(0) != null) {
            lblJugador1Barrera1.setText("B");
            lblJugador1Barrera1.setBackground(CBarrera);
        } else {
            lblJugador1Barrera1.setText("X");
            lblJugador1Barrera1.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador1.Barrera.Cartas.obtenerCartaxId(1) != null) {
            lblJugador1Barrera2.setText("B");
            lblJugador1Barrera2.setBackground(CBarrera);
        } else {
            lblJugador1Barrera2.setText("X");
            lblJugador1Barrera2.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador1.Barrera.Cartas.obtenerCartaxId(2) != null) {
            lblJugador1Barrera3.setText("B");
            lblJugador1Barrera3.setBackground(CBarrera);
        } else {
            lblJugador1Barrera3.setText("X");
            lblJugador1Barrera3.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador1.Barrera.Cartas.obtenerCartaxId(3) != null) {
            lblJugador1Barrera4.setText("B");
            lblJugador1Barrera4.setBackground(CBarrera);
        } else {
            lblJugador1Barrera4.setText("X");
            lblJugador1Barrera4.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador1.Barrera.Cartas.obtenerCartaxId(4) != null) {
            lblJugador1Barrera5.setText("B");
            lblJugador1Barrera5.setBackground(CBarrera);
        } else {
            lblJugador1Barrera5.setText("X");
            lblJugador1Barrera5.setBackground(Color.lightGray);
        }

        if (JuegoN.Estado.Jugador2.Barrera.Cartas.obtenerCartaxId(0) != null) {
            lblJugador2Barrera1.setText("B");
            lblJugador2Barrera1.setBackground(CBarrera);
        } else {
            lblJugador2Barrera1.setText("X");
            lblJugador2Barrera1.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador2.Barrera.Cartas.obtenerCartaxId(1) != null) {
            lblJugador2Barrera2.setText("B");
            lblJugador2Barrera2.setBackground(CBarrera);
        } else {
            lblJugador2Barrera2.setText("X");
            lblJugador2Barrera2.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador2.Barrera.Cartas.obtenerCartaxId(2) != null) {
            lblJugador2Barrera3.setText("B");
            lblJugador2Barrera3.setBackground(CBarrera);
        } else {
            lblJugador2Barrera3.setText("X");
            lblJugador2Barrera3.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador2.Barrera.Cartas.obtenerCartaxId(3) != null) {
            lblJugador2Barrera4.setText("B");
            lblJugador2Barrera4.setBackground(CBarrera);
        } else {
            lblJugador2Barrera4.setText("X");
            lblJugador2Barrera4.setBackground(Color.lightGray);
        }
        if (JuegoN.Estado.Jugador2.Barrera.Cartas.obtenerCartaxId(4) != null) {
            lblJugador2Barrera5.setText("B");
            lblJugador2Barrera5.setBackground(CBarrera);
        } else {
            lblJugador2Barrera5.setText("X");
            lblJugador2Barrera5.setBackground(Color.lightGray);
        }

        if (JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(0) != null) {
            lblJugador1ZonaBatalla1.setText(JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(0).getValor()  + " " +
                    Carta.devuelveUnicode(JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(0).getElemento()));
            if (JuegoN.Estado.Jugador1.ZonaBatalla.poscarta[0] == 1) {
                lblJugador1ZonaBatalla1.setBackground(Color.RED);
            } else if (JuegoN.Estado.Jugador1.ZonaBatalla.poscarta[0] == 2) {
                lblJugador1ZonaBatalla1.setBackground(Color.GREEN);
            } else {
                lblJugador1ZonaBatalla1.setText("?");
                lblJugador1ZonaBatalla1.setBackground(Color.GREEN);
            }
        } else {
            lblJugador1ZonaBatalla1.setText("X");
            lblJugador1ZonaBatalla1.setBackground(Color.BLACK);
            lblJugador1ZonaBatalla1.setForeground(Color.white);
        }

        if (JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(1) != null) {
            lblJugador1ZonaBatalla2.setText(JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(1).getValor() + " " +
                    Carta.devuelveUnicode(JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(1).getElemento()));
            if (JuegoN.Estado.Jugador1.ZonaBatalla.poscarta[1] == 1) {
                lblJugador1ZonaBatalla2.setBackground(Color.RED);
            } else if (JuegoN.Estado.Jugador1.ZonaBatalla.poscarta[1] == 2) {
                lblJugador1ZonaBatalla2.setBackground(Color.GREEN);
            } else {
                lblJugador1ZonaBatalla2.setText("?");
                lblJugador1ZonaBatalla2.setBackground(Color.GREEN);
            }
        } else {
            lblJugador1ZonaBatalla2.setText("X");
            lblJugador1ZonaBatalla2.setBackground(Color.BLACK);
            lblJugador1ZonaBatalla2.setForeground(Color.white);
        }

        if (JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(2) != null) {
            lblJugador1ZonaBatalla3.setText(JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(2).getValor() + " " +
                    Carta.devuelveUnicode(JuegoN.Estado.Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(2).getElemento()));
            if (JuegoN.Estado.Jugador1.ZonaBatalla.poscarta[2] == 1) {
                lblJugador1ZonaBatalla3.setBackground(Color.RED);
            } else if (JuegoN.Estado.Jugador1.ZonaBatalla.poscarta[2] == 2) {
                lblJugador1ZonaBatalla3.setBackground(Color.GREEN);
            } else {
                lblJugador1ZonaBatalla3.setText("?");
                lblJugador1ZonaBatalla3.setBackground(Color.GREEN);
            }
        } else {
            lblJugador1ZonaBatalla3.setText("X");
            lblJugador1ZonaBatalla3.setBackground(Color.BLACK);
            lblJugador1ZonaBatalla3.setForeground(Color.white);
        }


        if (JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(0) != null) {
            lblJugador2ZonaBatalla1.setText(JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(0).getValor()  + " " +
                    Carta.devuelveUnicode(JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(0).getElemento()));
            if (JuegoN.Estado.Jugador2.ZonaBatalla.poscarta[0] == 1) {
                lblJugador2ZonaBatalla1.setBackground(Color.RED);
            } else if (JuegoN.Estado.Jugador2.ZonaBatalla.poscarta[0] == 2) {
                lblJugador2ZonaBatalla1.setBackground(Color.GREEN);
            } else {
                lblJugador2ZonaBatalla1.setText("?");
                lblJugador2ZonaBatalla1.setBackground(Color.GREEN);
            }
        } else {
            lblJugador2ZonaBatalla1.setText("X");
            lblJugador2ZonaBatalla1.setBackground(Color.BLACK);
            lblJugador2ZonaBatalla1.setForeground(Color.white);
        }
        if (JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(1) != null) {
            lblJugador2ZonaBatalla2.setText(JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(1).getValor() + " " +
                    Carta.devuelveUnicode(JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(1).getElemento()));
            if (JuegoN.Estado.Jugador2.ZonaBatalla.poscarta[1] == 1) {
                lblJugador2ZonaBatalla2.setBackground(Color.RED);
            } else if (JuegoN.Estado.Jugador2.ZonaBatalla.poscarta[1] == 2) {
                lblJugador2ZonaBatalla2.setBackground(Color.GREEN);
            } else {
                lblJugador2ZonaBatalla2.setText("?");
                lblJugador2ZonaBatalla2.setBackground(Color.GREEN);
            }
        } else {
            lblJugador2ZonaBatalla2.setText("X");
            lblJugador2ZonaBatalla2.setBackground(Color.BLACK);
            lblJugador2ZonaBatalla2.setForeground(Color.white);
        }

        if (JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(2) != null) {
            lblJugador2ZonaBatalla3.setText(JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(2).getValor() + " " +
                    Carta.devuelveUnicode(JuegoN.Estado.Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(2).getElemento()));
            if (JuegoN.Estado.Jugador2.ZonaBatalla.poscarta[2] == 1) {
                lblJugador2ZonaBatalla3.setBackground(Color.RED);
            } else if (JuegoN.Estado.Jugador2.ZonaBatalla.poscarta[2] == 2) {
                lblJugador2ZonaBatalla3.setBackground(Color.GREEN);
            } else {
                lblJugador2ZonaBatalla3.setText("?");
                lblJugador2ZonaBatalla3.setBackground(Color.GREEN);
            }
        } else {
            lblJugador2ZonaBatalla3.setText("X");
            lblJugador2ZonaBatalla3.setBackground(Color.BLACK);
            lblJugador2ZonaBatalla3.setForeground(Color.white);
        }
    }

    void actualizarVista2(){
        if(JuegoN.JugadorActual.getNombre() == JuegoN.Estado.Jugador1.getNombre()){
            int pc0= JuegoN.JugadorActual.ZonaBatalla.poscarta[0];
            int pc1= JuegoN.JugadorActual.ZonaBatalla.poscarta[1];
            int pc2= JuegoN.JugadorActual.ZonaBatalla.poscarta[2];
            Carta c0=JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(0);
            Carta c1=JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(1);
            Carta c2=JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(2);
            if(pc0 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc0 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla1.setText(c0.getValor()+ " " +Carta.devuelveUnicode(c0.getElemento()));
            if(pc1 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc1 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla2.setText(c1.getValor()+ " " +Carta.devuelveUnicode(c1.getElemento()));
            if(pc2 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc2 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla3.setText(c2.getValor()+ " " +Carta.devuelveUnicode(c2.getElemento()));
            pc0= JuegoN.JugadorAnterior.ZonaBatalla.poscarta[0];
            pc1= JuegoN.JugadorAnterior.ZonaBatalla.poscarta[1];
            pc2= JuegoN.JugadorAnterior.ZonaBatalla.poscarta[2];
            c0=JuegoN.JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(0);
            c1=JuegoN.JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(1);
            c2=JuegoN.JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(2);
            if(pc0 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc0 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla1.setText(c0.getValor()+ " " +Carta.devuelveUnicode(c0.getElemento()));
            if(pc1 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc1 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla2.setText(c1.getValor()+ " " +Carta.devuelveUnicode(c1.getElemento()));
            if(pc2 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc2 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla3.setText(c2.getValor()+ " " +Carta.devuelveUnicode(c2.getElemento()));
        }
        if(JuegoN.JugadorActual.getNombre() == JuegoN.Estado.Jugador2.getNombre()){
            int pc0= JuegoN.JugadorActual.ZonaBatalla.poscarta[0];
            int pc1= JuegoN.JugadorActual.ZonaBatalla.poscarta[1];
            int pc2= JuegoN.JugadorActual.ZonaBatalla.poscarta[2];
            Carta c0=JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(0);
            Carta c1=JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(1);
            Carta c2=JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(2);
            if(pc0 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc0 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla1.setText(c0.getValor()+ " " +Carta.devuelveUnicode(c0.getElemento()));
            if(pc1 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc1 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla2.setText(c1.getValor()+ " " +Carta.devuelveUnicode(c1.getElemento()));
            if(pc2 == ZonaBatalla.POSCARTA.DEFCARAARRIBA || pc2 == ZonaBatalla.POSCARTA.ATAQUE)
                lblJugador1ZonaBatalla3.setText(c2.getValor()+ " " +Carta.devuelveUnicode(c2.getElemento()));
        }
    }

    void iniciarJuego(){

        lblJugador1.setText(JuegoN.Estado.Jugador1.getNombre());
        lblJugador2.setText(JuegoN.Estado.Jugador2.getNombre());

        JuegoN.Controlador = new Controlador();
        JuegoN.Controlador.repartirCartas(JuegoN.Estado.Jugador1);
        JuegoN.Controlador.repartirCartas(JuegoN.Estado.Jugador2);

        JuegoN.Regla=new Regla();

        if(JuegoN.ModoJuego==Juego.MODOJUEGO.VSBOT)
            JuegoN.Maquina = new Maquina();

        JuegoN.Accion = Juego.ACCION.NOACCION;
        JuegoN.Estado.Termino= Estado.TERMINO.NOTERMINO;

        if(JuegoN.Estado.getTurno()==Estado.TURNO.JUGADOR1){
            JuegoN.JugadorActual=JuegoN.Estado.Jugador1;
            JuegoN.JugadorAnterior=JuegoN.Estado.Jugador2;
            JuegoN.Estado.Jugador1.contarTurno();
        }
        else{
            JuegoN.JugadorActual=JuegoN.Estado.Jugador2;
            JuegoN.JugadorAnterior=JuegoN.Estado.Jugador1;
            JuegoN.Estado.Jugador2.contarTurno();
        }

        mhistorial.clear();
        mhistorial.addElement("Turno: "+JuegoN.JugadorActual.getNombre());

        btnAtacar.setEnabled(false);
        btnColocarEnAtk.setEnabled(false);
        btnColocarEnDef.setEnabled(false);
        btnCambiarPosicion.setEnabled(false);
        lblNDeckJ1.setText(JuegoN.Estado.Jugador1.Deck.Deck.size()+"");
        lblNDeckJ2.setText(JuegoN.Estado.Jugador2.Deck.Deck.size()+"");

        JuegoN.JugadorActual.ZonaBatalla.renovarDisponibilidades();

        habilitarControlesXTurno();
        actualizarVista();
        if(JuegoN.ModoJuego==Juego.MODOJUEGO.VSBOT &&
                JuegoN.Estado.getTurno()==Estado.TURNO.JUGADOR2BOT &&
                JuegoN.Estado.Termino==Estado.TERMINO.NOTERMINO){
            EleccionMaquina();
            accionTerminarTurno();
        }

    }

    void botonTerminarTurno() {
        if (btnTerminarTurno.isEnabled()) {
            btnColocarEnAtk.setEnabled(false);
            btnColocarEnDef.setEnabled(false);
            btnAtacar.setEnabled(false);
            btnCambiarPosicion.setEnabled(false);
            DeSeleccionarMano();
            DeSeleccionarZonaBatalla();

            accionTerminarTurno();

        }
    }

    void botonCambiarPosicionBatalla() {
        if (btnCambiarPosicion.isEnabled()) {
            if (JuegoN.Accion == Juego.ACCION.NOACCION) {

                JuegoN.Regla.accionCambiarPosicionBatalla(JuegoN.JugadorActual, JuegoN.IdCartaZonaBSel - 1);

                btnCambiarPosicion.setEnabled(false);
                btnAtacar.setEnabled(false);
                DeSeleccionarZonaBatalla();
                actualizarVista();
            }
        }
    }

    void botonColocarEnAtk() {
        if (btnColocarEnAtk.isEnabled()) {
            if (JuegoN.Accion == Juego.ACCION.COLOCANDOCARTA &&
                    JuegoN.CartaPosSel == Carta.CARTAPOS.COLOCARENDEFENSA) { //Cancelar Colocar Carta en Ataque
                btnColocarEnAtk.setText("Colocar en ATK");
                JuegoN.CartaPosSel = Carta.CARTAPOS.NOSELECCION;
                JuegoN.Accion = Juego.ACCION.NOACCION;
                btnColocarEnAtk.setEnabled(false);
                btnColocarEnDef.setEnabled(false);
                DeSeleccionarMano();
            } else if (JuegoN.Accion == Carta.CARTAPOS.COLOCARENDEFENSA &&
                    JuegoN.CartaPosSel == Carta.CARTAPOS.COLOCARENATAQUE) { //Cambiar a Colocar Carta en Ataque
                JuegoN.CartaPosSel = Carta.CARTAPOS.COLOCARENDEFENSA;
                btnColocarEnDef.setText("Colocar en DEF");
                btnColocarEnAtk.setText("Cancelar");
            } else {
                JuegoN.CartaPosSel = Carta.CARTAPOS.COLOCARENDEFENSA;
                JuegoN.Accion = Juego.ACCION.COLOCANDOCARTA;
                btnColocarEnAtk.setText("Cancelar");
            }
        }
    }

    void botonColocarEnDef() {
        if (btnColocarEnDef.isEnabled()) {
            if (JuegoN.Accion == Juego.ACCION.COLOCANDOCARTA &&
                    JuegoN.CartaPosSel == Carta.CARTAPOS.COLOCARENATAQUE) { //Cancelar Colocar Carta en Defenza
                btnColocarEnDef.setText("Colocar en DEF");
                JuegoN.CartaPosSel = Carta.CARTAPOS.NOSELECCION;
                JuegoN.Accion = Juego.ACCION.NOACCION;
                btnColocarEnAtk.setEnabled(false);
                btnColocarEnDef.setEnabled(false);
                DeSeleccionarMano();
            } else if (JuegoN.Accion == Juego.ACCION.COLOCANDOCARTA &&
                    JuegoN.CartaPosSel == Carta.CARTAPOS.COLOCARENDEFENSA) { //Cambiar a Colocar Carta en Defenza
                JuegoN.CartaPosSel = Carta.CARTAPOS.COLOCARENATAQUE;
                btnColocarEnAtk.setText("Colocar en ATK");
                btnColocarEnDef.setText("Cancelar");
            } else {
                JuegoN.CartaPosSel = Carta.CARTAPOS.COLOCARENATAQUE;
                JuegoN.Accion = Juego.ACCION.COLOCANDOCARTA;
                btnColocarEnDef.setText("Cancelar");
            }
        }
    }

    void botonAtacar() {
        if (btnAtacar.isEnabled()) {
            if (JuegoN.Accion == Juego.ACCION.NOACCION) {//Atacar
                if (JuegoN.JugadorAnterior.ZonaBatalla.Cartas.obtenerNumerodeCartas() == 0) {///Atacar a la barrera
                    int resp;
                    resp = JuegoN.Regla.accionAtacarBarrera(JuegoN.JugadorActual, JuegoN.JugadorAnterior, JuegoN.IdCartaZonaBSel - 1);

                    btnAtacar.setEnabled(false);
                    btnCambiarPosicion.setEnabled(false);
                    actualizarVista();
                    DeSeleccionarZonaBatalla();
                    if (resp == Regla.RESULTADOATACARBARRERA.SINBARRERAS) {
                        JuegoN.Estado.Termino = Estado.TERMINO.SINBARRERAS;
                        terminarJuego();
                    }

                } else {//Atacar a una carta
                    JuegoN.Accion = Juego.ACCION.ATACANDOCARTA;
                    btnAtacar.setText("Cancelar");
                    btnCambiarPosicion.setEnabled(false);
                }

            } else if (JuegoN.Accion == Juego.ACCION.ATACANDOCARTA) {//Cancelar el ataque en curso

                JuegoN.Accion = Juego.ACCION.NOACCION;
                btnAtacar.setText("Atacar");
                btnAtacar.setEnabled(false);
                DeSeleccionarZonaBatalla();
            }

        }

    }

    void DeSeleccionarMano() {
        MatteBorder B = new MatteBorder(2, 2, 2, 2, (Color) CMano);
        if (JuegoN.Estado.getTurno() == Estado.TURNO.JUGADOR1) {
            lblJugador1Mano1.setBorder(B);
            lblJugador1Mano2.setBorder(B);
            lblJugador1Mano3.setBorder(B);
            lblJugador1Mano4.setBorder(B);
            lblJugador1Mano5.setBorder(B);
        } else {
            lblJugador2Mano1.setBorder(B);
            lblJugador2Mano2.setBorder(B);
            lblJugador2Mano3.setBorder(B);
            lblJugador2Mano4.setBorder(B);
            lblJugador2Mano5.setBorder(B);
        }
    }

    void DeSeleccionarZonaBatalla() {
        MatteBorder B = new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla);
        if (JuegoN.Estado.getTurno() == Estado.TURNO.JUGADOR1) {
            lblJugador1ZonaBatalla1.setBorder(B);
            lblJugador1ZonaBatalla2.setBorder(B);
            lblJugador1ZonaBatalla3.setBorder(B);
        } else {
            lblJugador2ZonaBatalla1.setBorder(B);
            lblJugador2ZonaBatalla2.setBorder(B);
            lblJugador2ZonaBatalla3.setBorder(B);
        }
    }

    void ManoSeleccionada(MouseEvent e) {
        JLabel JL = (JLabel) e.getSource();
        //lblJugador1Mano5
        String JLNAME = JL.getName();
        boolean JACA = false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece

        if (JLNAME.substring(10, 11).equals(JuegoN.Estado.getTurno() + "")) {
            JACA = true;
        } else {
            JACA = false;
        }

        if (JuegoN.Accion == Juego.ACCION.NOACCION) {
            JuegoN.IdCartaManoSel = Integer.parseInt(JLNAME.substring(15));
            if (JuegoN.JugadorActual.Mano.Cartas.obtenerCartaxId(JuegoN.IdCartaManoSel - 1) != null) {//Si hay carta en ese espacio
                if (JACA) {
                    if (JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerNumerodeCartas() <
                            ZonaBatalla.MAXZONABATALLACARDS) {
                        DeSeleccionarMano();
                        DeSeleccionarZonaBatalla();
                        btnAtacar.setEnabled(false);
                        btnCambiarPosicion.setEnabled(false);
                        JL.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CSeleccionado));
                        if (!JuegoN.JugadorActual.ZonaBatalla.isCartacolocada()) {
                            btnColocarEnAtk.setEnabled(true);
                            btnColocarEnDef.setEnabled(true);
                        }
                    }
                }
            }
        }

    }

    void resultadoAtaque(int idCartaZBJAnt, int idCartaZBJAct) {//Muestra visualmente resultados
        int valorJugadorActual, valorJugadorAnterior;

        valorJugadorActual = JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZBJAct).getValor();
        valorJugadorAnterior = JuegoN.JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCartaZBJAnt).getValor();

        if (valorJugadorActual > valorJugadorAnterior) {
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " (" +
                            (valorJugadorActual + 1) + ")" + " > "  +
                            JuegoN.JugadorAnterior.getNombre() + " (" + (valorJugadorAnterior + 1) + ")",
                            "Resultado de Batalla", JOptionPane.INFORMATION_MESSAGE);
        } else if (valorJugadorActual < valorJugadorAnterior) {
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " (" +
                            (valorJugadorActual + 1) + ")" + " < " +
                            JuegoN.JugadorAnterior.getNombre() + " (" + (valorJugadorAnterior + 1) + ")",
                            "Resultado de Batalla", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " (" +
                            (valorJugadorActual + 1) + ")" + " = " +
                            JuegoN.JugadorAnterior.getNombre() + " (" + (valorJugadorAnterior + 1) + ")",
                            "Resultado de Batalla", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void accionTerminarTurno() {

        JuegoN.Estado.imprimeEstado();

        if(JuegoN.Estado.getTurno() == Estado.TURNO.JUGADOR1){
            JuegoN.Estado.setTurno(Estado.TURNO.JUGADOR2BOT);
            JuegoN.JugadorActual=JuegoN.Estado.Jugador2;
            JuegoN.JugadorAnterior=JuegoN.Estado.Jugador1;
            JuegoN.Estado.Jugador2.contarTurno();
        }
        else{
            JuegoN.Estado.setTurno(Estado.TURNO.JUGADOR1);
            JuegoN.JugadorActual=JuegoN.Estado.Jugador1;
            JuegoN.JugadorAnterior=JuegoN.Estado.Jugador2;
            JuegoN.Estado.Jugador1.contarTurno();
        }

        if( JuegoN.JugadorActual.Mano.Cartas.obtenerNumerodeCartas() < Mano.MAXMANOCARDS &&
                JuegoN.JugadorActual.Deck.obtenerNumeroElementos() > 0){
            //Jugador con menos cartas del maximo o con cartas iguales al maximo (Deck tiene cartas) Agrega una carata a su mano
            JuegoN.JugadorActual.Mano.Cartas.agregarCartaEnEspacioVacio(JuegoN.JugadorActual.Deck.sacarUnaCarta());
        }

        if (JuegoN.JugadorActual.Deck.obtenerNumeroElementos() == 0) {//Deck sin cartas
            JuegoN.Estado.Termino = Estado.TERMINO.SINCARTAS;
            terminarJuego();
        }

        JuegoN.JugadorActual.ZonaBatalla.renovarDisponibilidades();

        habilitarControlesXTurno();
        actualizarVista();
        lblNDeckJ1.setText(JuegoN.Estado.Jugador1.Deck.Deck.size() + "");
        lblNDeckJ2.setText(JuegoN.Estado.Jugador2.Deck.Deck.size() + "");
        mhistorial.addElement("---------------------------------------------");
        mhistorial.addElement("Turno: "+JuegoN.JugadorActual.getNombre());


        JuegoN.Accion= Juego.ACCION.NOACCION;


        if (JuegoN.ModoJuego == Juego.MODOJUEGO.VSBOT &&
                JuegoN.Estado.getTurno() == Estado.TURNO.JUGADOR2BOT &&
                JuegoN.Estado.Termino == Estado.TERMINO.NOTERMINO) {
            EleccionMaquina();
            accionTerminarTurno();
        }
    }

    void ZonaBatallaSeleccionada(MouseEvent e) {
        JLabel JL = (JLabel) e.getSource();
        String JLNAME = JL.getName();
        //lblJugador1ZonaBatalla1
        boolean JACA = false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece

        int idCarta;

        if (JLNAME.substring(10, 11).equals(JuegoN.Estado.getTurno() + "")) {
            JACA = true;
        } else {
            JACA = false;
        }

        //if(JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCarta-1)!=null){//Si existe carta en dicha posicion

        if (JuegoN.Accion == Juego.ACCION.COLOCANDOCARTA) {
            //Si se est� colocando una carta, esta confirmado que estas en tu propio turno
            if (JACA) {
                idCarta = Integer.parseInt(JLNAME.substring(22));
                if (JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerNumerodeCartas() <= ZonaBatalla.MAXZONABATALLACARDS) {
                    if (JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(idCarta - 1) == null) {
                        //Colocar carta

                        JuegoN.Regla.accionColocarCarta(JuegoN.JugadorActual,
                                idCarta - 1, JuegoN.IdCartaManoSel - 1, JuegoN.CartaPosSel);

                        JuegoN.Accion = Juego.ACCION.NOACCION;
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
        } else if (JuegoN.Accion == Juego.ACCION.NOACCION) {//Si no hay alguna accion en progreso
            if (JACA) {//Jugador Actual toca una carta (o espacio de carta) que le pertenece
                JuegoN.IdCartaZonaBSel = Integer.parseInt(JLNAME.substring(22));
                if (JuegoN.JugadorActual.getNTurnos() > 1) {

                    if (JuegoN.JugadorActual.ZonaBatalla.Cartas.obtenerCartaxId(JuegoN.IdCartaZonaBSel - 1) != null) {//Si existe carta en dicha posicion
                        DeSeleccionarZonaBatalla();
                        DeSeleccionarMano();
                        btnColocarEnAtk.setEnabled(false);
                        btnColocarEnDef.setEnabled(false);
                        JL.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CSeleccionado));
                        if (JuegoN.JugadorActual.ZonaBatalla.dispataque[JuegoN.IdCartaZonaBSel - 1] == 1) {//Si la carta est� activa para atacar
                            if (JuegoN.JugadorActual.ZonaBatalla.poscarta[JuegoN.IdCartaZonaBSel - 1] == 1)
                                btnAtacar.setEnabled(true);
                            else
                                btnAtacar.setEnabled(false);
                        } else {
                            btnAtacar.setEnabled(false);
                        }
                        if (JuegoN.JugadorActual.ZonaBatalla.dispcambio[JuegoN.IdCartaZonaBSel - 1] == 1) {
                            btnCambiarPosicion.setEnabled(true);
                        } else {
                            btnCambiarPosicion.setEnabled(false);
                        }
                    }
                }
            }
        } else if (JuegoN.Accion == Juego.ACCION.ATACANDOCARTA) {//Si se est� atacando a una carta
            idCarta = Integer.parseInt(JLNAME.substring(22));

            if (JuegoN.JugadorAnterior.ZonaBatalla.Cartas.obtenerCartaxId(idCarta - 1) != null) {//Si existe carta en dicha posicion


                if (!JACA) { //Jugador Actual toca una carta (o espacio de carta) que no le pertenece

                    if (JuegoN.JugadorAnterior.ZonaBatalla.poscarta[idCarta - 1] == ZonaBatalla.POSCARTA.DEFCARAABAJO) {//Jugador Atacado En defenza cara abajo
                        JuegoN.JugadorAnterior.ZonaBatalla.poscarta[idCarta - 1] = ZonaBatalla.POSCARTA.DEFCARAARRIBA;
                        actualizarVista();
                    }
                    int resp;
                    resultadoAtaque(idCarta - 1, JuegoN.IdCartaZonaBSel - 1);

                    resp = JuegoN.Regla.accionAtacarCarta(JuegoN.JugadorActual, JuegoN.JugadorAnterior, idCarta - 1, JuegoN.IdCartaZonaBSel - 1);


                    JuegoN.Accion = Juego.ACCION.NOACCION;
                    btnAtacar.setEnabled(false);
                    btnAtacar.setText("Atacar");
                    DeSeleccionarZonaBatalla();
                    actualizarVista();

                    if (resp == Regla.RESULTADOATACARCARTA.ENEMIGOSINCARTAS) {//Termina el juego porque enemigo se quedo sin cartas
                        JuegoN.Estado.Termino = Estado.TERMINO.SINBARRERAS;
                        terminarJuego();
                    }
                }

            }
        }
        //}
    }

    void EleccionMaquina(){
        Estado EI,EF;//Estado Inicial y Final del turno

        EI=JuegoN.Estado;

        JuegoN.Maquina.cargarEstado(EI);
        JuegoN.Maquina.EstadosGenerados=JuegoN.Maquina.sistemaDeProduccion(JuegoN.Regla,EI);

        if(JuegoN.Dificultad==Juego.DIFICULTAD.FACIL){
            JuegoN.Estado=JuegoN.Maquina.estrategiaRandom(JuegoN.Maquina.EstadosGenerados).clone();
        }
        else if(JuegoN.Dificultad==Juego.DIFICULTAD.NORMAL){
            JuegoN.Estado=JuegoN.Maquina.estrategiaPrimeroElMejor(JuegoN.Maquina.EstadosGenerados,Maquina.MEJOR.MAYOR).clone();
        }
        else if(JuegoN.Dificultad==Juego.DIFICULTAD.AVANZADO){
            JuegoN.Estado=JuegoN.Maquina.estrategiaMinMax(JuegoN.Maquina.EstadosGenerados).clone();
        }
        EF=JuegoN.Estado;

        imprimirMovimiento(EI, EF);

        actualizarVista();

        if(JuegoN.Estado.Termino > Estado.TERMINO.NOTERMINO ){
            terminarJuego();
        }
        else{
            JOptionPane.showMessageDialog( null, "La Máquina ha jugado, empieza tu nuevo turno",
                    "Máquina Jugó",
                    JOptionPane.INFORMATION_MESSAGE );
        }


    }






























}
