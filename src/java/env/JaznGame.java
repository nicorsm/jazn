package env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.impl.Ball;
import model.impl.Player;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IBall;
import model.interfaces.IPlayer;
import utils.Utils;

public class JaznGame {

	private Map<Team, Map<Role, List<IPlayer>>> players;
	private IBall ball;
	private List<IPlayer> goals;
	private JaznEnvironment env;

	public JaznGame(JaznEnvironment env) {
		this.env = env;
		System.out.println("Game manager initialized successfully.");
	}

	public void prepareField() {

		System.out.println("The referee is preparing the match...");

		Team[] teams = Team.values();
		Role[] roles = Role.values();

		this.players = new HashMap<Team, Map<Role, List<IPlayer>>>();
		this.goals = new ArrayList<IPlayer>();

		System.out
				.println("Players on the pitch for today's match between " + teams[0] + " and " + teams[1] + " are: ");

		for (Team t : teams) {
			int jersey = 1;

			HashMap<Role, List<IPlayer>> rls = new HashMap<Role, List<IPlayer>>();

			for (Role r : roles) {
				ArrayList<IPlayer> pls = new ArrayList<IPlayer>();

				for (int i = 0; i < r.getCount(); i++) {
					IPlayer p = new Player(t, jersey, r);
					System.out.println(" - " + p.toString());
					pls.add(p);
					jersey++;
				}

				rls.put(r, pls);
			}
			this.players.put(t, rls);
		}
		
		this.env.setPlayerPercepts(this.players);

		ball = new Ball(0, 0);

		IPlayer kickoff = Utils.randomIn(this.players.get(Team.FORLI_CITY).get(Role.GOALKEEPER));
		System.out.println("The player assigned for kickoff is " + kickoff.toString());
		System.out.println("Referee, WHISTLE!");
		

		this.env.whistle(kickoff);
		
		// kickoff.receive(ball);
	}
	
	public IPlayer getPlayerFromAgentName(String agentName) {
		for(Team t: Team.values()) {
			if(agentName.startsWith(t.getShortName())) {
				int jersey = Integer.parseInt(agentName.replace(t.getShortName(), ""));
				
				Map<Role, List<IPlayer>> rose = this.players.get(t);
				
				for(Role r: rose.keySet()) {
					for(IPlayer p: rose.get(r)) {
						if(p.getJerseyNumber() == jersey) {
							return p;
						}
					}
				}
			}
		}
		return null;
	}
	

	public List<IPlayer> getPlayers(Team t, Role r) {
		return this.players.get(t).get(r);
	}
	
	public boolean scored(IPlayer p) {
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
