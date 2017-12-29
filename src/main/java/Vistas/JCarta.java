package Vistas;

import Clases.Carta;

import javax.swing.*;
import java.awt.*;

public class JCarta extends JLabel{

    public Carta carta;
    private int pos;
    private int jugador;


    JCarta(int ppos,int pjugador){
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.carta=null;
        this.setPos(ppos);
        this.setJugador(pjugador);
    }

    JCarta(Carta pcarta,int ppos,int pjugador){
        this.carta=pcarta;
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setPos(ppos);
        this.setJugador(pjugador);
    }


    public JCarta(int pelemento,int pvalor,int puso, Carta pcarta){
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.carta=pcarta;
    }

    public JCarta clone(){
        JCarta clon=new JCarta(this.carta, this.getPos(), this.getJugador());
        return clon;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getJugador() {
        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }
}
