package env;

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
	private JaznGame gameManager;
	private Logger log = Logger.getLogger("jazn."+JaznEnvironment.class.getName());
    
	@Override
	public void init(String[] args) {
		log.info("Environment initialized successfully.");
		gameManager = new JaznGame(this);
		
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
			gameManager.prepareField();
			execute = true;
		}
		
		if(action.equals(tryGoal)) {
			IPlayer player = this.gameManager.getPlayerFromAgentName(agName);
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
		IPlayer senderAgent = this.gameManager.getPlayerFromAgentName(sender);
		setPerceptsFor(senderAgent);
		
	}
	
	private void passBall(String sender, Role r) {

		clearBall(sender);

		IPlayer senderAgent = this.gameManager.getPlayerFromAgentName(sender);
		
		boolean intercepts = Utils.shouldInterceptBall() && r != Role.GOALKEEPER;
		
		for(Team t : Team.values()) {
			
			boolean isFromMyTeam = sender.startsWith(t.getShortName());
			
			if((isFromMyTeam && !intercepts) || (!isFromMyTeam && intercepts)){
				
				Role role = r;
				Role inverse = senderAgent.getRole().getInverse();
				if(intercepts && inverse != null) {
					role = inverse;
				}
				
				List<IPlayer> nextLine = this.gameManager.getPlayers(t, role);
				IPlayer p = Utils.randomIn(nextLine); // can also be a peer

				if(intercepts) {
					log.info("BALL WAS INTERCEPTED BY " + p.toString());
				} else {
					log.info("Passing ball to " +  p.toString());
				}
				
				//log.info("Adding ball percept to " + p.getName());
				addPercept(p.getName(), ball);
				//System.out.println(this.toString() + " says that the receiver will be " + p.toString());
				//p.receive(ball);
				return;
			}
		}
		
	}
	
	private void tryGoal(String agName, IPlayer player) {
		
		clearBall(agName);

		log.info("OMG " + player.toString() + " IS TRYING A GOAL...");
		
		if(Utils.shouldTryGoal()) {
			this.gameManager.scored(player);
		} else {
			log.info("No way :(");
		}
		
		Team other = player.getTeam().getOtherTeam();
		IPlayer goalkeeper = this.gameManager.getPlayers(other, Role.GOALKEEPER).get(0);
		log.info("Ball is now of " + goalkeeper.toString());
		passBall(goalkeeper.getName(), goalkeeper.getRole());
	
	}
	
}
