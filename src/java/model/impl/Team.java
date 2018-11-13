package model.impl;

public enum Team {
	FORLI_CITY("fc"),
	CESENA_UNITED("cu");
	
    private final String shortName;

    Team(final String shortName) {
    	this.shortName = shortName;
    }

    public String getShortName() { return shortName; }
}