package model.impl;

public enum Role {
	GOALKEEPER(1),			//Portiere
	DEFENDER(4),			//Difensore
	MIDFIELDER(4),			//Centrocampista
	FORWARD(2);				//Attaccante

    private final int count;

    Role(final int newValue) {
    	count = newValue;
    }

    public int getCount() { return count; }
}
