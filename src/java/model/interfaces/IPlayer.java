package model.interfaces;

import model.impl.Role;
import model.impl.Team;

public interface IPlayer {

	public void init(Team team, int jerseyNumber, Role role);
	public Team getTeam();
	public int getJerseyNumber();
	public IPosition getPosition();
	public Role getRole();
	
}
