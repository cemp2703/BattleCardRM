package Vistas;

import javax.swing.*;
import java.awt.*;

public class FormJuego {
    private JFrame frmBattleCards;
    private JPanel PanelInicio;
    private JPanel PanelJuego;
    private Juego Juego;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FormJuego window = new FormJuego();
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
    private FormJuego() {
        initialize();
    }
    
    private void initialize() {

        frmBattleCards = new JFrame();
        frmBattleCards.setTitle("Battle Cards");
        frmBattleCards.setResizable(false);
        frmBattleCards.setBounds(100, 100, 800, 600);
        frmBattleCards.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmBattleCards.getContentPane().setLayout(new CardLayout(0, 0));

        Juego=new Juego();

        PanelInicio= new PanelInicio(frmBattleCards,Juego);
        PanelInicio.setBackground(SystemColor.activeCaption);
        frmBattleCards.getContentPane().add(PanelInicio, "PanelInicio");

        PanelJuego = new PanelJuego(frmBattleCards,Juego);
        PanelJuego.setBackground(SystemColor.activeCaption);
        frmBattleCards.getContentPane().add(PanelJuego, "PanelJuego");

    }
}
