package env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import model.impl.Player;
import model.impl.Referee;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;

public class JaznEnvironment extends Environment {

	public static final Literal prepareField = Literal.parseLiteral("prepareField");
	public static final Literal tryGoal = Literal.parseLiteral("tryGoal");
	public static final Literal ball = Literal.parseLiteral("ball");
	public static final String passToPrefix = "passTo_";

	private Map<Team, Map<Role, List<IPlayer>>> players;
	private List<IPlayer> goals;
	private Logger log = Logger.getLogger("jazn."+JaznEnvironment.class.getName());
    
	@Override
	public void init(String[] args) {
		log.info("Environment initialized successfully.");
	}
	
	public void whistle(IPlayer kickoff) {
		log.info("Adding a percept for whistle");
		addPercept("referee", Literal.parseLiteral("whistled"));
		addPercept(kickoff.getName(), ball);
	}
	
	public void setPlayersPercepts(Map<Team, Map<Role, List<IPlayer>>> map) {
		
		log.info("Adding percepts for players...");
		
		for(Team t: map.keySet()) {
			
			Map<Role, List<IPlayer>> rose = map.get(t);
			
			for(Role r: rose.keySet()) {
				
				List<IPlayer> players = rose.get(r);
				
				for(IPlayer p: players) {
					setPerceptsFor(p);
				}
			}
			
		}
		
		log.info("Percepts adding completed.");
	}
	
	public Map<Team, Map<Role, List<IPlayer>>> getPlayers() {
		return this.players;
	}
	
	private void setPerceptsFor(IPlayer p) {
		String teamPercept = "team(" + p.getTeam().getShortName() + ")";
		addPercept(p.getName(), Literal.parseLiteral(teamPercept));

		String jerseyPercept = "jersey(" + p.getJerseyNumber() + ")";
		addPercept(p.getName(), Literal.parseLiteral(jerseyPercept));
		
		String rolePercept = p.getRole().toString().toLowerCase();
		addPercept(p.getName(), Literal.parseLiteral(rolePercept));
	}
	
	@Override
	public boolean executeAction(String agName, Structure action) {
		boolean execute = false;
		if(action.equals(prepareField)) {
			log.info("Preparing field...");
			this.prepareField();
			execute = true;
		}
		
		if(action.equals(tryGoal)) {
			IPlayer player = JaznUtils.getPlayerFromAgentName(this, agName);
			if(player == null) {
				return execute;
			}
			
			tryGoal(agName, player);
			
			execute = true;
		}
		
		for(Role r: Role.values()) {

			if(action.equals(Literal.parseLiteral(passToPrefix + r.name().toLowerCase()))) {
				passBall(agName, r);
				execute = true;
			}
		}
		return execute;
	}
	
	private void clearBall(String sender) {
		clearPercepts(sender);
		IPlayer senderAgent = JaznUtils.getPlayerFromAgentName(this, sender);
		setPerceptsFor(senderAgent);
		
	}
	
	private void passBall(String sender, Role r) {

		clearBall(sender);

		IPlayer senderAgent = JaznUtils.getPlayerFromAgentName(this, sender);
		
		boolean intercepts = Utils.shouldInterceptBall() && r != Role.GOALKEEPER;
		
		for(Team t : Team.values()) {
			
			boolean isFromMyTeam = sender.startsWith(t.getShortName());
			
			if((isFromMyTeam && !intercepts) || (!isFromMyTeam && intercepts)){
				
				Role role = r;
				Role inverse = senderAgent.getRole().getInverse();
				if(intercepts && inverse != null) {
					role = inverse;
				}
				
				List<IPlayer> nextLine = JaznUtils.getPlayers(this, t, role);
				IPlayer p = Utils.randomIn(nextLine); // can also be a peer

				if(intercepts) {
					log.info("BALL WAS INTERCEPTED BY " + p.toString());
				} else {
					log.info("Passing ball to " +  p.toString());
				}
				
				addPercept(p.getName(), ball);
				return;
			}
		}
		
	}
	
	private void tryGoal(String agName, IPlayer player) {
		
		clearBall(agName);

		log.info("OMG " + player.toString() + " IS TRYING A GOAL...");
		
		if(Utils.shouldTryGoal()) {
			this.scored(player);
		} else {
			log.info("No way :(");
		}
		
		Team other = player.getTeam().getOtherTeam();
		IPlayer goalkeeper = JaznUtils.getPlayers(this, other, Role.GOALKEEPER).get(0);
		log.info("Ball is now of " + goalkeeper.toString());
		passBall(goalkeeper.getName(), goalkeeper.getRole());
	
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

		this.setPlayersPercepts(this.players);

		Team randomTeam = Utils.randomIn(Team.values());
		IPlayer kickoff = Utils.randomIn(this.players.get(randomTeam).get(Role.MIDFIELDER));
		System.out.println("The player assigned for kickoff is " + kickoff.toString());
		System.out.println("Referee, WHISTLE!");

		this.whistle(kickoff);
	}

	public boolean scored(IPlayer p) {
		this.goals.add(p);
		System.out.println("GOAAAAAL by " + p.toString());
		System.out.println("Current score: ");
		
		for(Team t : Team.values()) {
			int goals = 0;
			for(IPlayer player : this.goals) {
				if(player.getTeam() == t) {
					goals++;
				}
			}
			System.out.println(" - " + t.name() + " : " + goals);
		}
		return true;
	}

}
