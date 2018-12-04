package model.impl;

public enum Team {
	FORLI_CITY("forliCity", "fcCoach"),
	CESENA_UNITED("cesenaUnited", "cuCoach");
	
    private final String shortName;
    private final String coachName;

    Team(final String shortName, final String coachName) {
    	this.shortName = shortName;
    	this.coachName = coachName;
    }

    public String getShortName() { return shortName; }
    
    public String getCoachName() { return coachName; }
    
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