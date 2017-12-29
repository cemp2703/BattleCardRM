package Vistas;

import Clases.Estado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelInicio extends JPanel{

    private JLabel lblModo;
    private JLabel lblInicia;
    private JLabel lblTituloDificultad;
    private JLabel lblDificultad;

    private JButton btnDificultadAtras;
    private JButton btnDificultadAdelante;

    Juego JuegoN;
    private JFrame JF;

    PanelInicio(JFrame pJF, Juego pJuego){
        JuegoN=pJuego;
        JF=pJF;

        this.setBackground(SystemColor.activeCaption);

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
                if (btnDificultadAdelante.isEnabled()) {
                    botonCambiarDificultad(1);
                }
            }
        });
        btnDificultadAdelante.setEnabled(true);

        lblDificultad = new JLabel("Facil");
        lblDificultad.setVisible(false);
        lblDificultad.setEnabled(true);
        lblDificultad.setHorizontalAlignment(SwingConstants.CENTER);
        lblDificultad.setFont(new Font("Snap ITC", Font.PLAIN, 14));

        btnDificultadAtras = new JButton("<");
        btnDificultadAtras.setVisible(false);
        btnDificultadAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (btnDificultadAtras.isEnabled()) {
                    botonCambiarDificultad(-1);
                }
            }
        });
        btnDificultadAtras.setEnabled(false);

        lblTituloDificultad = new JLabel("Dificultad");
        lblTituloDificultad.setVisible(false);
        lblTituloDificultad.setEnabled(true);
        lblTituloDificultad.setFont(new Font("Viner Hand ITC", Font.ITALIC, 17));

        //region gl_PanelInicio
        GroupLayout gl_PanelInicio = new GroupLayout(this);
        gl_PanelInicio.setHorizontalGroup(
                gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(gl_PanelInicio.createSequentialGroup()
                                .addContainerGap(254, Short.MAX_VALUE)
                                .addGroup(gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.LEADING)
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
                                        .addGroup(gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(gl_PanelInicio.createSequentialGroup()
                                                        .addComponent(btnIniciaAtras, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(35)
                                                        .addComponent(lblInicia, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnIniciaAdelante, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(gl_PanelInicio.createSequentialGroup()
                                                        .addComponent(btnDificultadAtras, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(36)
                                                        .addComponent(lblDificultad, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_PanelInicio.createSequentialGroup()
                                .addGap(71)
                                .addComponent(lblBattleCards)
                                .addGap(27)
                                .addComponent(lbl456)
                                .addGap(18)
                                .addGroup(gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblModo)
                                        .addComponent(btnModoAtras)
                                        .addComponent(btnModoAdelante))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(lblTituloDificultad, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDificultad, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnDificultadAtras)
                                        .addComponent(btnDificultadAdelante))
                                .addGap(22)
                                .addComponent(lblTitulo2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addGap(11)
                                .addGroup(gl_PanelInicio.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblInicia, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnIniciaAtras)
                                        .addComponent(btnIniciaAdelante))
                                .addGap(54)
                                .addComponent(btnIniciarJuego)
                                .addGap(99))
        );
        //endregion

        this.setLayout(gl_PanelInicio);
    }

    void botonIniciarJuego() {
        Component PJ= JF.getContentPane().getComponent(1);
        ((PanelJuego)PJ).iniciarJuego();
        ((CardLayout) JF.getContentPane().getLayout()).show(JF.getContentPane(),"PanelJuego");
    }

    void botonCambiarModoJuego() {

        if (JuegoN.ModoJuego == Juego.MODOJUEGO.VSPLAYER) {
            //Maquina vs Jugador

            JuegoN.ModoJuego = Juego.MODOJUEGO.VSBOT;

            lblModo.setText("Jugador vs Máquina");

            JuegoN.E.Jugador2.setNombre("Máquina");

            lblDificultad.setVisible(true);
            btnDificultadAdelante.setVisible(true);
            btnDificultadAtras.setVisible(true);
            lblTituloDificultad.setVisible(true);

        } else {

            //Jugador1 vs Jugador2

            JuegoN.ModoJuego = Juego.MODOJUEGO.VSPLAYER;

            lblModo.setText("Jugador vs Jugador");
            JuegoN.E.Jugador2.setNombre("Jugador 2");

            lblDificultad.setVisible(false);
            btnDificultadAdelante.setVisible(false);
            btnDificultadAtras.setVisible(false);
            lblTituloDificultad.setVisible(false);
        }
    }

    void botonCambiarJugadorInicial() {
        if (JuegoN.ModoJuego == Juego.MODOJUEGO.VSPLAYER) {
            if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1) {
                JuegoN.E.setTurno(Estado.TURNO.JUGADOR2BOT);
                lblInicia.setText("Jugador 2");
                JuegoN.E.Jugador2.setNombre("Jugador 2");
            }
            else{
                JuegoN.E.setTurno(Estado.TURNO.JUGADOR1);
                lblInicia.setText("Jugador 1");
            }
        } else {//Modo Jugador vs Maquina
            if (JuegoN.E.getTurno() == Estado.TURNO.JUGADOR1) {
                JuegoN.E.setTurno(Estado.TURNO.JUGADOR2BOT);
                lblInicia.setText("Máquina");
                JuegoN.E.Jugador2.setNombre("Máquina");
            }
            else{
                lblInicia.setText("Jugador 1");
                JuegoN.E.setTurno(Estado.TURNO.JUGADOR1);
            }
        }
    }

    void botonCambiarDificultad(int sumando) {
        if (JuegoN.ModoJuego == Juego.MODOJUEGO.VSBOT) {
            if (JuegoN.Dificultad + sumando == Juego.DIFICULTAD.FACIL) {
                JuegoN.Dificultad = Juego.DIFICULTAD.FACIL;
                lblDificultad.setText("Facil");
                btnDificultadAtras.setEnabled(false);
                btnDificultadAdelante.setEnabled(true);
            } else if (JuegoN.Dificultad + sumando == Juego.DIFICULTAD.NORMAL) {
                JuegoN.Dificultad = Juego.DIFICULTAD.NORMAL;
                lblDificultad.setText("Normal");
                btnDificultadAdelante.setEnabled(true);
                btnDificultadAtras.setEnabled(true);
            } else if (JuegoN.Dificultad + sumando == Juego.DIFICULTAD.AVANZADO ){
                JuegoN.Dificultad = Juego.DIFICULTAD.AVANZADO;
                lblDificultad.setText("Experto");
                btnDificultadAdelante.setEnabled(false);
                btnDificultadAtras.setEnabled(true);
            } else {
                btnDificultadAtras.setEnabled(true);
                btnDificultadAdelante.setEnabled(true);
            }
        }

    }

}
