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

    private Color CSeleccionado;

    private JList lstHistorial;
    private DefaultListModel mhistorial;
    private JScrollPane scrollPane;

    private Juego JuegoN;
    private JFrame JF;

    PanelJuego(JFrame pJF, Juego pJuego) {
        JuegoN = pJuego;
        JF = pJF;

        CSeleccionado = new Color(255, 215, 0);

        MouseAdapter MAManoSeleccionada = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ManoSeleccionada(e);
            }

            /*
            public void mouseEntered(MouseEvent e) {
                enfocarCarta(e);
            }
            */
        };

        MouseAdapter MAZonaBatallaSeleccionada = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { ZonaBatallaSeleccionada(e); }
        };

        for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
            JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).addMouseListener(MAManoSeleccionada);
            JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).addMouseListener(MAManoSeleccionada);
        }

        for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
            JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).addMouseListener(MAZonaBatallaSeleccionada);
            JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).addMouseListener(MAZonaBatallaSeleccionada);
        }

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
            public void mouseClicked(MouseEvent e) { botonCambiarPosicionBatalla();
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
        lstHistorial.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
        mhistorial = new DefaultListModel();
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
                                                .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                .addComponent(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(34)
                                                .addComponent(lblNDeckJ1))
                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(panelDivisor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                        .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))))
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
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblJugador2Deck, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                        .addGroup(gl_PanelTablero.createSequentialGroup()
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                                        .addComponent(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18)
                                                                                                .addComponent(panelDivisor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18)
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(54)
                                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
                                                                                        .addComponent(lblNDeckJ1))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                        .addGroup(gl_PanelTablero.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(0), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(1), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(2), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(3), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(4), GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
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

        this.setLayout(gl_PanelTablero);
        //endregion
    }

    //region Acciones

    void iniciarJuego(){

        lblJugador1.setText(JuegoN.E.Jugador1.getNombre());
        lblJugador2.setText(JuegoN.E.Jugador2.getNombre());

        JuegoN.Controlador = new Controlador();
        JuegoN.Controlador.repartirCartas(JuegoN.E.Jugador1);
        JuegoN.Controlador.repartirCartas(JuegoN.E.Jugador2);

        JuegoN.Regla = new Regla();

        if(JuegoN.ModoJuego == Juego.MODOJUEGO.VSBOT)
            JuegoN.Maquina = new Maquina();

        JuegoN.Accion = Juego.ACCION.NOACCION;
        JuegoN.E.Termino = Estado.TERMINO.NOTERMINO;

        if(JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1){
            JuegoN.JugadorActual = JuegoN.E.Jugador1;
            JuegoN.JugadorAnterior = JuegoN.E.Jugador2;
            JuegoN.E.Jugador1.contarTurno();
        }
        else{
            JuegoN.JugadorActual = JuegoN.E.Jugador2;
            JuegoN.JugadorAnterior = JuegoN.E.Jugador1;
            JuegoN.E.Jugador2.contarTurno();
        }

        mhistorial.clear();
        mhistorial.addElement("Turno: "+JuegoN.JugadorActual.getNombre());

        btnAtacar.setEnabled(false);
        btnColocarEnAtk.setEnabled(false);
        btnColocarEnDef.setEnabled(false);
        btnCambiarPosicion.setEnabled(false);
        lblNDeckJ1.setText(JuegoN.E.Jugador1.Deck.Deck.size() + "");
        lblNDeckJ2.setText(JuegoN.E.Jugador2.Deck.Deck.size() + "");

        JuegoN.JugadorActual.ZonaBatalla.renovarDisponibilidades();

        habilitarControlesXTurno();
        actualizarVista();
        if(JuegoN.ModoJuego == Juego.MODOJUEGO.VSBOT &&
                JuegoN.E.getTurno() == Estado.TURNO.JUGADOR2BOT &&
                JuegoN.E.Termino == Estado.TERMINO.NOTERMINO){
            TurnoMaquina();
            accionTerminarTurno();
        }
    }

    void terminarJuego() {
        if (JuegoN.E.Termino == Estado.TERMINO.SINCARTAS) {//Jugador Actual sin cartas que jalar en su Deck
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() +
                            " se ha quedado sin cartas en su DECK, " + JuegoN.JugadorAnterior.getNombre() +
                            " ha ganado la partida", JuegoN.JugadorAnterior.getNombre() + " Gano!!",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (JuegoN.E.Termino == Estado.TERMINO.SINBARRERAS) {//Jugador Anterior se quedó sin barreras
            JOptionPane.showMessageDialog(null, JuegoN.JugadorAnterior.getNombre() +
                            " se ha quedado sin barreras que lo protejan, " + JuegoN.JugadorActual.getNombre() +
                            " ha ganado la partida", JuegoN.JugadorActual.getNombre() + " Gano!!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        ((CardLayout) JF.getContentPane().getLayout()).show(JF.getContentPane(), "PanelInicio");
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
            if(JuegoN.Accion == Juego.ACCION.NOACCION){//Colocar carta en ataque
                JuegoN.Accion = Juego.ACCION.COLOCARATK;
                btnColocarEnAtk.setText("Cancelar");
                btnColocarEnDef.setEnabled(false);
            }
            else if (JuegoN.Accion == Juego.ACCION.COLOCARATK){
                JuegoN.Accion= Juego.ACCION.NOACCION;
                btnColocarEnAtk.setText("Colocar en ATK");
                btnColocarEnAtk.setEnabled(false);
                DeSeleccionarMano();
            }
        }
    }

    void botonColocarEnDef() {
        if (btnColocarEnDef.isEnabled()) {
            if(JuegoN.Accion == Juego.ACCION.NOACCION){//Colocar carta en defensa
                JuegoN.Accion = Juego.ACCION.COLOCARDEFBA;
                btnColocarEnDef.setText("Cancelar");
                btnColocarEnAtk.setEnabled(false);
            }
            else if (JuegoN.Accion == Juego.ACCION.COLOCARDEFBA){//Cancelar Colocar carta en defensa
                JuegoN.Accion= Juego.ACCION.NOACCION;
                btnColocarEnDef.setText("Colocar en DEF");
                btnColocarEnDef.setEnabled(false);
                DeSeleccionarMano();
            }
        }
    }

    void botonAtacar() {
        if (btnAtacar.isEnabled()) {
            if (JuegoN.Accion == Juego.ACCION.NOACCION) {//Atacar
                if (JuegoN.JugadorAnterior.ZonaBatalla.obtenerNumerodeCartas() == 0) {///Atacar a la barrera
                    int resp;
                    resp = JuegoN.Regla.accionAtacarBarrera(JuegoN.JugadorActual, JuegoN.JugadorAnterior, JuegoN.IdCartaZonaBSel);
                    btnAtacar.setEnabled(false);
                    btnCambiarPosicion.setEnabled(false);
                    actualizarVista();
                    DeSeleccionarZonaBatalla();
                    if (resp == Regla.RESULTADOATACARBARRERA.SINBARRERAS) {
                        JuegoN.E.Termino = Estado.TERMINO.SINBARRERAS;
                        terminarJuego();
                    }
                } else {//Atacar a una carta
                    JuegoN.Accion = Juego.ACCION.ATACARCARTA;
                    btnAtacar.setText("Cancelar");
                    btnCambiarPosicion.setEnabled(false);
                }
            } else if (JuegoN.Accion == Juego.ACCION.ATACARCARTA) {//Cancelar el ataque en curso

                JuegoN.Accion = Juego.ACCION.NOACCION;
                btnAtacar.setText("Atacar");
                btnAtacar.setEnabled(false);
                DeSeleccionarZonaBatalla();
            }

        }

    }

    void ManoSeleccionada(MouseEvent e) {
        JCarta JL = (JCarta) e.getSource();
        if (JL.getJugador() == JuegoN.E.getTurno()) {//Jugador Actual toca una carta (o espacio de carta) que le pertenece
            if (JuegoN.Accion == Juego.ACCION.NOACCION) {
                if (JuegoN.JugadorActual.Mano.obtenerJCartaxId(JL.getPos()) != null) {//Si hay carta en ese espacio
                    if (JuegoN.JugadorActual.Mano.obtenerCartaxId(JL.getPos()) != null) {
                        if (JuegoN.JugadorActual.ZonaBatalla.obtenerNumerodeCartas() < ZonaBatalla.MAXZONABATALLACARDS) {
                            btnAtacar.setEnabled(false);
                            btnCambiarPosicion.setEnabled(false);
                            //JL.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CSeleccionado));
                            if (!JuegoN.JugadorActual.ZonaBatalla.isCartacolocada()) {
                                btnColocarEnAtk.setEnabled(true);
                                btnColocarEnDef.setEnabled(true);
                            }
                            JuegoN.IdCartaManoSel = JL.getPos();
                            DeSeleccionarMano();
                            DeSeleccionarZonaBatalla();
                            enfocarCarta(e);
                        }
                    }
                }
            }
        }

    }

    void ZonaBatallaSeleccionada(MouseEvent e) {
        JCarta JL = (JCarta) e.getSource();
        if (JL.getJugador() == JuegoN.E.getTurno()) { //Jugador Actual toca una carta (o espacio de carta) que le pertenece
            if (JuegoN.Accion == Juego.ACCION.COLOCARATK ||
                    JuegoN.Accion == Juego.ACCION.COLOCARDEFBA) { //Colocar una carta en ataque o defensa en espacio vacio
                if (JuegoN.JugadorActual.ZonaBatalla.obtenerJCartaxId(JL.getPos()) != null) {
                    if (JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(JL.getPos()) == null) {
                        if (JuegoN.JugadorActual.ZonaBatalla.obtenerNumerodeCartas() <= ZonaBatalla.MAXZONABATALLACARDS) {
                            JuegoN.IdCartaZonaBSel=JL.getPos();
                            btnTerminarTurno.setEnabled(true);
                            if (JuegoN.Accion == Juego.ACCION.COLOCARATK){
                                JuegoN.Regla.accionColocarCarta(JuegoN.JugadorActual, JuegoN.IdCartaZonaBSel, JuegoN.IdCartaManoSel, ZonaBatalla.POSCARTA.ATAQUE);
                                btnAtacar.setEnabled(true);
                            }
                            else{
                                JuegoN.Regla.accionColocarCarta(JuegoN.JugadorActual, JuegoN.IdCartaZonaBSel, JuegoN.IdCartaManoSel, ZonaBatalla.POSCARTA.DEFCARAABAJO);
                                btnAtacar.setEnabled(false);
                            }
                            btnColocarEnAtk.setEnabled(false);
                            btnColocarEnAtk.setText("Colocar en ATK");
                            btnColocarEnDef.setEnabled(false);
                            btnColocarEnDef.setText("Colocar en DEF");
                            JuegoN.Accion = Juego.ACCION.NOACCION;
                            DeSeleccionarMano();
                            DeSeleccionarZonaBatalla();
                            actualizarVista();
                            enfocarCarta(e);
                        }
                    }
                }
            }
            else if (JuegoN.Accion == Juego.ACCION.NOACCION) {//Si no hay alguna accion en progreso, seleccionas una carta
                if (JuegoN.JugadorActual.ZonaBatalla.obtenerJCartaxId(JL.getPos()) != null) {
                    if (JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(JL.getPos()) != null) {
                        if (JuegoN.JugadorActual.getNTurnos() > 1) {
                            JuegoN.IdCartaZonaBSel = JL.getPos();
                            btnColocarEnAtk.setEnabled(false);
                            btnColocarEnDef.setEnabled(false);
                            //JL.setBorder(new MatteBorder(2, 2, 2, 2, (Color) CSeleccionado));
                            if (JuegoN.JugadorActual.ZonaBatalla.dispataque[JuegoN.IdCartaZonaBSel]
                                    == ZonaBatalla.DISPATAQUE.DISPONIBLE) {//Si la carta puede atacar
                                if (JuegoN.JugadorActual.ZonaBatalla.poscarta[JuegoN.IdCartaZonaBSel] == ZonaBatalla.POSCARTA.ATAQUE)
                                    btnAtacar.setEnabled(true);
                                else
                                    btnAtacar.setEnabled(false);
                            } else {
                                btnAtacar.setEnabled(false);
                            }
                            if (JuegoN.JugadorActual.ZonaBatalla.dispcambio[JuegoN.IdCartaZonaBSel] ==
                                    ZonaBatalla.DISPCAMBIO.DISPONIBLE) {//Si la carta puede cambiar de posición
                                btnCambiarPosicion.setEnabled(true);
                            } else {
                                btnCambiarPosicion.setEnabled(false);
                            }
                            DeSeleccionarZonaBatalla();
                            DeSeleccionarMano();
                        }
                    }
                }
            }
        }
        else{ //Jugador Actual toca una carta (o espacio de carta) que no le pertenece
            if (JuegoN.Accion == Juego.ACCION.ATACARCARTA) {//Si se está atacando a una carta
                if (JuegoN.JugadorAnterior.ZonaBatalla.obtenerJCartaxId(JL.getPos()) != null) {//Si existe carta en dicha posicion
                    if (JuegoN.JugadorAnterior.ZonaBatalla.obtenerCartaxId(JL.getPos()) != null) {//Si existe carta en dicha posicion
                        btnAtacar.setEnabled(false);
                        btnAtacar.setText("Atacar");
                        if (JuegoN.JugadorAnterior.ZonaBatalla.poscarta[JL.getPos()] == ZonaBatalla.POSCARTA.DEFCARAABAJO) {//Jugador Atacado En defenza cara abajo
                            JuegoN.JugadorAnterior.ZonaBatalla.poscarta[JL.getPos()] = ZonaBatalla.POSCARTA.DEFCARAARRIBA;
                            actualizarVista();
                        }
                        imprimirAtaque(JL.getPos(), JuegoN.IdCartaZonaBSel);
                        int resp;
                        resp = JuegoN.Regla.accionAtacarCarta(JuegoN.JugadorActual, JuegoN.JugadorAnterior, JL.getPos(), JuegoN.IdCartaZonaBSel);
                        JuegoN.Accion = Juego.ACCION.NOACCION;
                        DeSeleccionarZonaBatalla();
                        actualizarVista();
                        if (resp == Regla.RESULTADOATACARCARTA.ENEMIGOSINCARTAS) {//Termina el juego porque enemigo se quedo sin cartas
                            JuegoN.E.Termino = Estado.TERMINO.SINBARRERAS;
                            terminarJuego();
                        }
                    }
                }
            }
        }
    }

    void accionTerminarTurno() {
        JuegoN.E.imprimeEstado();
        if(JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1){
            JuegoN.E.setTurno(Estado.TURNO.JUGADOR2BOT);
            JuegoN.JugadorActual=JuegoN.E.Jugador2;
            JuegoN.JugadorAnterior=JuegoN.E.Jugador1;
            JuegoN.E.Jugador2.contarTurno();
        }
        else{
            JuegoN.E.setTurno(Estado.TURNO.JUGADOR1);
            JuegoN.JugadorActual=JuegoN.E.Jugador1;
            JuegoN.JugadorAnterior=JuegoN.E.Jugador2;
            JuegoN.E.Jugador1.contarTurno();
        }
        if( JuegoN.JugadorActual.Mano.obtenerNumerodeCartas() < Mano.MAXMANOCARDS &&
                JuegoN.JugadorActual.Deck.obtenerNumeroElementos() > 0){
            //Jugador con menos cartas del maximo o con cartas iguales al maximo (Deck tiene cartas) Agrega una carata a su mano
            JuegoN.JugadorActual.Mano.agregarCartaEnEspacioVacio(JuegoN.JugadorActual.Deck.sacarUnaCarta());
        }
        if (JuegoN.JugadorActual.Deck.obtenerNumeroElementos() == 0) {//Deck sin cartas
            JuegoN.E.Termino = Estado.TERMINO.SINCARTAS;
            terminarJuego();
        }
        JuegoN.JugadorActual.ZonaBatalla.renovarDisponibilidades();
        habilitarControlesXTurno();
        actualizarVista();
        lblNDeckJ1.setText(JuegoN.E.Jugador1.Deck.Deck.size() + "");
        lblNDeckJ2.setText(JuegoN.E.Jugador2.Deck.Deck.size() + "");
        mhistorial.addElement("---------------------------------------------");
        mhistorial.addElement("Turno: "+JuegoN.JugadorActual.getNombre());
        JuegoN.Accion= Juego.ACCION.NOACCION;
        if (JuegoN.ModoJuego == Juego.MODOJUEGO.VSBOT &&
                JuegoN.E.getTurno() == Estado.TURNO.JUGADOR2BOT &&
                JuegoN.E.Termino == Estado.TERMINO.NOTERMINO) {
            TurnoMaquina();
            accionTerminarTurno();
        }
    }

    void TurnoMaquina(){
        Estado EI,EF;//Estado Inicial y Final del turno
        EI=JuegoN.E;
        JuegoN.Maquina.cargarEstado(EI);
        JuegoN.Maquina.EstadosGenerados=JuegoN.Maquina.sistemaDeProduccion(JuegoN.Regla,EI);
        if(JuegoN.Dificultad==Juego.DIFICULTAD.FACIL){
            JuegoN.E=JuegoN.Maquina.estrategiaRandom(JuegoN.Maquina.EstadosGenerados).clone();
        }
        else if(JuegoN.Dificultad==Juego.DIFICULTAD.NORMAL){
            JuegoN.E=JuegoN.Maquina.estrategiaPrimeroElMejor(JuegoN.Maquina.EstadosGenerados,Maquina.MEJOR.MAYOR).clone();
        }
        else if(JuegoN.Dificultad==Juego.DIFICULTAD.AVANZADO){
            JuegoN.E=JuegoN.Maquina.estrategiaMinMax(JuegoN.Maquina.EstadosGenerados).clone();
        }
        EF=JuegoN.E;
        imprimirMovimiento(EI, EF);
        actualizarVista();
        if(JuegoN.E.Termino > Estado.TERMINO.NOTERMINO ){
            terminarJuego();
        }
        else{
            JOptionPane.showMessageDialog( null, "La Máquina ha jugado, empieza tu nuevo turno",
                    "Máquina Jugó", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    //endregion

    //region Vista

    void DeSeleccionarMano() {
        MatteBorder B = new MatteBorder(2, 2, 2, 2, (Color) JuegoN.CMano);
        for(int i=0;i < Mano.MAXMANOCARDS;i++){
            if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1) {
                JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setBorder(B);
            } else {
                JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setBorder(B);
            }
        }

    }

    void DeSeleccionarZonaBatalla() {
        MatteBorder B = new MatteBorder(2, 2, 2, 2, (Color) JuegoN.CZonaBatalla);
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1) {
                JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBorder(B);
            } else {
                JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBorder(B);
            }
        }

    }

    void actualizarVista() {
        for(int i=0;i < Mano.MAXMANOCARDS;i++){
            if(JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i) != null){
                if(JuegoN.E.Jugador1.Mano.obtenerCartaxId(i) != null){
                    if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1){
                        JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setText(JuegoN.E.Jugador1.Mano.obtenerCartaxId(i).getValor()  + " " +
                                Carta.devuelveUnicode(JuegoN.E.Jugador1.Mano.obtenerCartaxId(i).getElemento()) + "");
                    }
                    else{
                        JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setText("M");
                    }
                    JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setBackground(Color.white);
                }
                else{
                    JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setText("X");
                    JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setBackground(Color.lightGray);
                }
            }
            if(JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i) != null){
                if(JuegoN.E.Jugador2.Mano.obtenerCartaxId(i) != null){
                    if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR2BOT){
                        JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setText(JuegoN.E.Jugador2.Mano.obtenerCartaxId(i).getValor()  + " " +
                                Carta.devuelveUnicode(JuegoN.E.Jugador2.Mano.obtenerCartaxId(i).getElemento()) + "");
                    }
                    else{
                        JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setText("M");
                    }
                    JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setBackground(Color.white);
                }
                else{
                    JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setText("X");
                    JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setBackground(Color.lightGray);
                }
            }
        }
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if (JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i) != null) {
                JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i).setText("B");
                JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i).setBackground(JuegoN.CBarrera);
            } else {
                JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i).setText("X");
                JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i).setBackground(Color.lightGray);
            }
            if (JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i) != null) {
                JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i).setText("B");
                JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i).setBackground(JuegoN.CBarrera);
            } else {
                JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i).setText("X");
                JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i).setBackground(Color.lightGray);
            }
        }
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++){
            if (JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i) != null) {
                if(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).carta != null){
                    JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setText(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).carta.getValor()  + " " +
                            Carta.devuelveUnicode(JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).carta.getElemento()));
                    if (JuegoN.E.Jugador1.ZonaBatalla.poscarta[i] == ZonaBatalla.POSCARTA.ATAQUE) {
                        JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.RED);
                    } else if (JuegoN.E.Jugador1.ZonaBatalla.poscarta[i] == ZonaBatalla.POSCARTA.DEFCARAARRIBA) {
                        JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.GREEN);
                    }
                    else {
                        JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setText("?");
                        JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.GREEN);
                    }
                }
                else {
                    JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setText("X");
                    JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.BLACK);
                    JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setForeground(Color.white);
                }
            }
            if (JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i) != null) {
                if(JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).carta != null) {
                    JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setText(JuegoN.E.Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor() + " " +
                            Carta.devuelveUnicode(JuegoN.E.Jugador2.ZonaBatalla.obtenerCartaxId(i).getElemento()));
                    if (JuegoN.E.Jugador2.ZonaBatalla.poscarta[i] == ZonaBatalla.POSCARTA.ATAQUE) {
                        JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.RED);
                    } else if (JuegoN.E.Jugador2.ZonaBatalla.poscarta[i] == ZonaBatalla.POSCARTA.DEFCARAARRIBA) {
                        JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.GREEN);
                    }
                    else {
                        JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setText("?");
                        JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.GREEN);
                    }
                }
                else {
                    JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setText("X");
                    JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBackground(Color.BLACK);
                    JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setForeground(Color.white);
                }

            }
        }
        if (!(JuegoN.E.Jugador1.Deck.obtenerNumeroElementos() == 0)) {
            lblJugador1Deck.setText("DECK");
        } else {
            lblJugador1Deck.setText("X");
        }
        if (!(JuegoN.E.Jugador2.Deck.obtenerNumeroElementos() == 0)) {
            lblJugador2Deck.setText("DECK");
        } else {
            lblJugador2Deck.setText("X");
        }
    }

    void habilitarControlesXTurno() {
        if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1) {
            lblJugador1.setEnabled(true);
            lblJugador2.setEnabled(false);
            lblJugador1Deck.setEnabled(true);
            lblJugador2Deck.setEnabled(false);
            for(int i=0;i < Barrera.MAXBARRERACARDS;i++) {
                JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i).setEnabled(true);
                JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i).setEnabled(false);
            }
            for(int i=0;i < Mano.MAXMANOCARDS;i++) {
                JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setEnabled(true);
                JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setEnabled(false);
            }
            for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++) {
                JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setEnabled(true);
                JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setEnabled(false);
            }
        } else {
            lblJugador1.setEnabled(false);
            lblJugador2.setEnabled(true);
            lblJugador1Deck.setEnabled(false);
            lblJugador2Deck.setEnabled(true);
            for(int i=0;i < Barrera.MAXBARRERACARDS;i++) {
                JuegoN.E.Jugador1.Barrera.obtenerJCartaxId(i).setEnabled(false);
                JuegoN.E.Jugador2.Barrera.obtenerJCartaxId(i).setEnabled(true);
            }
            for(int i=0;i < Mano.MAXMANOCARDS;i++) {
                JuegoN.E.Jugador1.Mano.obtenerJCartaxId(i).setEnabled(false);
                JuegoN.E.Jugador2.Mano.obtenerJCartaxId(i).setEnabled(true);
            }
            for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++) {
                JuegoN.E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setEnabled(false);
                JuegoN.E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setEnabled(true);
            }
        }
    }

    void enfocarCarta(MouseEvent e) {
        JCarta JL = (JCarta) e.getSource();
        int dueñocarta = JL.getJugador();
        boolean JACA = false;//Jugador Actual toca una carta (o espacio de carta) que le pertenece
        int idCarta;
        if (dueñocarta == JuegoN.E.getTurno()) {
            JACA = true;
        } else {
            JACA = false;
        }
        idCarta = JL.getPos();
        if (JACA) {
            if (JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(idCarta) != null) {
                lblEnfoque.setText(JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(idCarta).getValor() + " " +
                        Carta.devuelveUnicode(JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(idCarta).getElemento()) + "");
                lblEnfoque.setBackground(Color.white);
            }
        } else {
            if (JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(idCarta) != null) {
                lblEnfoque.setText("?");
                lblEnfoque.setBackground(Color.white);
            }
        }
    }

    void imprimirMovimiento(Estado EI, Estado EF) {//Estado Inicial, Estado Final
        int idcartacolocadazb = -1;
        Carta Cartacolocada = null;
        //Buscando si se sacó una carta de la mano
        for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
            if (EI.Jugador2.Mano.obtenerCartaxId(i) != EF.Jugador2.Mano.obtenerCartaxId(i)) {
                Cartacolocada = EI.Jugador2.Mano.obtenerCartaxId(i);
                break;
            }
        }

        //Si se colocó alguna carta en ZB
        boolean encontrada = false;
        if (Cartacolocada != null) {
            for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
                if (EF.Jugador2.ZonaBatalla.obtenerCartaxId(i) != null) {
                    if (Cartacolocada.getElemento() == EF.Jugador2.ZonaBatalla.obtenerCartaxId(i).getElemento()
                            && Cartacolocada.getValor() == EF.Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor()) {
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
            if (EI.Jugador2.ZonaBatalla.obtenerCartaxId(i) != null && EF.Jugador2.ZonaBatalla.obtenerCartaxId(i) != null) {
                //Si es la misma carta
                if (EI.Jugador2.ZonaBatalla.obtenerCartaxId(i).getElemento() == EF.Jugador2.ZonaBatalla.obtenerCartaxId(i).getElemento()
                        && EI.Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor() == EF.Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor()) {
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
            } else if (EI.Jugador2.ZonaBatalla.obtenerCartaxId(i) != null && EF.Jugador2.ZonaBatalla.obtenerCartaxId(i) == null) {
                if (i != idcartacolocadazb) {
                    mhistorial.addElement("-> Carta " + EI.Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor() + " " + Carta.devuelveUnicode(EI.Jugador2.ZonaBatalla.obtenerCartaxId(i).getElemento()) + " Atac� y se elimin� (perdi� o empat�)");
                }
            }
        }
    }

    void imprimirAtaque(int idCartaAtacada, int idCartaAtacante) {//Muestra visualmente resultados
        int valorJugadorActual, valorJugadorAnterior;

        valorJugadorActual = JuegoN.JugadorActual.ZonaBatalla.obtenerCartaxId(idCartaAtacante).getValor();
        valorJugadorAnterior = JuegoN.JugadorAnterior.ZonaBatalla.obtenerCartaxId(idCartaAtacada).getValor();

        if (valorJugadorActual > valorJugadorAnterior) {
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " (" +
                            valorJugadorActual + ")" + " > "  +
                            JuegoN.JugadorAnterior.getNombre() + " (" + valorJugadorAnterior + ")",
                    "Resultado de Batalla", JOptionPane.INFORMATION_MESSAGE);
        } else if (valorJugadorActual < valorJugadorAnterior) {
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " (" +
                            valorJugadorActual + ")" + " < " +
                            JuegoN.JugadorAnterior.getNombre() + " (" + valorJugadorAnterior + ")",
                    "Resultado de Batalla", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, JuegoN.JugadorActual.getNombre() + " (" +
                            valorJugadorActual + ")" + " = " +
                            JuegoN.JugadorAnterior.getNombre() + " (" + valorJugadorAnterior + ")",
                    "Resultado de Batalla", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //endregion
}
