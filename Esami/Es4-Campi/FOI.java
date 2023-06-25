package it.polito.oop.futsal;

public class FOI implements FieldOption{

	int campo;
	int pieno;
	public FOI(int c,int p) {
		campo = c;
		pieno = p;
	}
	@Override
	public int getField() {
		// TODO Auto-generated method stub
		return campo;
	}
	@Override
	public int getOccupation() {
		// TODO Auto-generated method stub
		return pieno;
	}
}
