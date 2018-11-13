package model.interfaces;

import model.impl.Role;
import model.impl.Team;

public interface IPlayer {

	public Team getTeam();
	public int getJerseyNumber();
	public IPosition getPosition();
	public Role getRole();
	public void receive(IBall ball);
	
}
