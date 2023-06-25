package it.polito.oop.futsal;

import it.polito.oop.futsal.Fields.Features;

public class Campo {
	int numero;
	boolean coperto;
	boolean riscaldato;
	boolean condizionato;
	
	public Campo(Features features,int n) {
		numero = n;
		coperto=features.indoor;
		riscaldato=features.heating;
		condizionato=features.ac;
	}
	
	public boolean getCoperto() {
		return coperto;
	}
	
	public boolean getRiscaldato() {
		return riscaldato;
	}
	
	public boolean getCondizionato() {
		return condizionato;
	}
	
	public int getCode() {
		return numero;
	}
}
