package model.interfaces;

import model.impl.Role;
import model.impl.Team;

public interface IPlayer {

	public Team getTeam();
	public int getJerseyNumber();
	public Role getRole();
	public String getName();
	
}
