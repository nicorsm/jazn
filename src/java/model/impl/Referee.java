package model.impl;

import java.util.*;

import model.interfaces.*;
import utils.Utils;
import model.impl.*;

public class Referee {
	
	private Map<Team,Map<Role,List<IPlayer>>> players;
	private IBall ball;
	private List<IPlayer> goals;
	
	private static Referee instance;
	
	private Referee() {}
	
	public static Referee getInstance() {
		if(instance == null) {
			instance = new Referee();
		}
		return instance;
	}
	
	public void startMatch() {
		
		System.out.println("The referee is preparing the match...");

		Team[] teams = Team.values();
		Role[] roles = Role.values();
		
		this.players = new HashMap<Team,Map<Role,List<IPlayer>>>();
		this.goals = new ArrayList<IPlayer>();
		
		System.out.println("Players on the pitch for today's match between " + teams[0] + " and " + teams[1] + " are: ");
		
		for(Team t : teams) {
			int jersey = 1;

			HashMap<Role, List<IPlayer>> rls = new HashMap<Role, List<IPlayer>>();
			
			for(Role r : roles) {
				ArrayList<IPlayer> pls = new ArrayList<IPlayer>();
				
				for(int i = 0; i < r.getCount(); i++) {
					IPlayer p = new Player(t, jersey, r);
					System.out.println(" - " + p.toString());
					pls.add(p);
					jersey++;
				}
				
				rls.put(r, pls);
			}
			this.players.put(t, rls);
		}
		
	
		ball = new Ball(0,0);
		
		IPlayer kickoff = Utils.randomIn(this.players.get(Team.FORLI_CITY).get(Role.GOALKEEPER));

		System.out.println("The player assigned for kickoff is " + kickoff.toString());
		System.out.println("START WHISTLE!");
		
		kickoff.receive(ball);
	}
	
	public List<IPlayer> getPlayers(Team t, Role r) {
		return this.players.get(t).get(r);
	}
	
	public boolean scored(Player p) {
		goals.add(p);
		System.out.println("GOAAAAAL by " + p.toString());
		return true;
	}
	
	public int goalsBy(Team t) {
		int goals = 0;
		for(IPlayer p : this.goals) {
			if(p.getTeam() == t) {
				goals++;
			}
		}
		return goals;
	}
	

}
