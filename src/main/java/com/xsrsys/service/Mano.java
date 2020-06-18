package com.xsrsys.service;

public class Mano extends VectorCartas implements Cloneable{
	public static final int MAXMANOCARDS = 5;
	
	public Mano(){
		super(MAXMANOCARDS);
	}

}
