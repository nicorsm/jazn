package model.impl;

public enum Team {
	FORLI_CITY("forliCity"),
	CESENA_UNITED("cesenaUnited");
	
    private final String shortName;

    Team(final String shortName) {
    	this.shortName = shortName;
    }

    public String getShortName() { return shortName; }
    
    public Team getOtherTeam() {
    	if(Team.values().length != 2) {
    		return null;
    	}
    	
    	for(Team t: Team.values()) {
    		if(t != this) {
    			return t;
    		}
    	}
    	
    	return null;
    }
}