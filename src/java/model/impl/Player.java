package model.impl;

import model.interfaces.*;

public class Player implements IPlayer {
	
	private Team team;
	private int jerseyNumber;
	private Role role;
	
	public Player(Team team, int jerseyNumber, Role role) {
		this.team = team;
		this.jerseyNumber = jerseyNumber;
		this.role = role;
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public int getJerseyNumber() {
		return this.jerseyNumber;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	@Override
	public String toString() {
		return "[" + getName() + "], " + role + " of " + team;
	}
	
	public String getName() {
		return this.team.getShortName() + this.jerseyNumber;
	}

}


