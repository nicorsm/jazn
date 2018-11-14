package model.impl;

public enum Team {
	FORLI_CITY("forliCity"),
	CESENA_UNITED("cesenaUnited");
	
    private final String shortName;

    Team(final String shortName) {
    	this.shortName = shortName;
    }

    public String getShortName() { return shortName; }
}