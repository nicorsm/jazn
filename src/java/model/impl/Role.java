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
    
    public Role getInverse() {
    	switch(this) {
		case DEFENDER: return Role.FORWARD;
		case FORWARD: return Role.DEFENDER;
		case MIDFIELDER: return Role.MIDFIELDER;
		case GOALKEEPER: return null;
		default: return null;
    	
    	}
    }
}
