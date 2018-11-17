package model.impl;

import java.util.concurrent.TimeUnit;

import jason.asSemantics.DefaultInternalAction;
import model.interfaces.*;
import utils.Utils;

public class Player implements IPlayer {
	
	private Team team;
	private int jerseyNumber;
	private Role role;
	private IPosition position;
	
	public Player(Team team, int jerseyNumber, Role role) {
		this.team = team;
		this.jerseyNumber = jerseyNumber;
		this.role = role;
		this.position = new Position(0,0);
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
	
	public IPosition getPosition() {
		return this.position;
	}
	
	public void setPosition(int x, int y) {
		this.position.setPosition(x,y);
	}
	
	public void receive(IBall ball) {

		Role roleToPass = null;
		boolean tryGoal = false;
		ball.setOwner(this);
		System.out.println(this.toString() + " is deciding who will be the receiver of the ball");
		
		switch(this.role){
			case DEFENDER: 		roleToPass = Role.MIDFIELDER; 	break;
			case FORWARD:  		tryGoal = true; 				break;
			case GOALKEEPER:	roleToPass = Role.DEFENDER;		break;
			case MIDFIELDER:	roleToPass = Role.FORWARD;		break;
			default:											break;
		}

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(tryGoal) {
			System.out.println(this.toString() + " is trying to score...");
			Referee.getInstance().scored(this);
		} else if(roleToPass != null) {
			IPlayer p = Utils.randomIn(Referee.getInstance().getPlayers(this.team, roleToPass)); // can also be a peer
			System.out.println(this.toString() + " says that the receiver will be " + p.toString());
			p.receive(ball);
		}
		
		
	}
	
	@Override
	public String toString() {
		return "[ " + getName() + " ], " + role + " of " + team;
	}
	
	public String getName() {
		return this.team.getShortName() + this.jerseyNumber;
	}

}


