package env;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import model.impl.Referee;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;

public class JaznEnvironment extends Environment {

	public static final Literal prepareField = Literal.parseLiteral("prepareField");
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
		addPercept(kickoff.getName(), Literal.parseLiteral("ball"));
	}
	
	public void setPlayerPercepts(Map<Team, Map<Role, List<IPlayer>>> map) {
		
		log.info("Adding percepts for players...");
		
		for(Team t: map.keySet()) {
			
			Map<Role, List<IPlayer>> rose = map.get(t);
			
			for(Role r: rose.keySet()) {
				
				List<IPlayer> players = rose.get(r);
				
				for(IPlayer p: players) {
					String teamPercept = "team(" + p.getTeam().getShortName() + ")";
					addPercept(p.getName(), Literal.parseLiteral(teamPercept));

					String jerseyPercept = "jersey(" + p.getJerseyNumber() + ")";
					addPercept(p.getName(), Literal.parseLiteral(jerseyPercept));
					
					String rolePercept = p.getRole().toString().toLowerCase();
					addPercept(p.getName(), Literal.parseLiteral(rolePercept));
				}
			}
			
		}
		
		log.info("Percepts adding completed.");
		
	}
	
	@Override
	public boolean executeAction(String agName, Structure action) {
		boolean execute = false;
		if(action.equals(prepareField)) {
			log.info("Preparing field...");
			gameManager.prepareField();
			execute = true;
		}
		
		for(Role r: Role.values()) {

			if(action.equals(Literal.parseLiteral(passToPrefix + r.name().toLowerCase()))) {
				log.info("Passing ball to " + r.name());
				passBall(agName, r);
				execute = true;
			}
		}
		return execute;
	}
	
	private void passBall(String sender, Role r) {

		for(Team t : Team.values()) {
			if(sender.startsWith(t.getShortName())) {
				IPlayer p = Utils.randomIn(this.gameManager.getPlayers(t, r)); // can also be a peer
				
				addPercept(p.getName(), Literal.parseLiteral("ball"));
				//System.out.println(this.toString() + " says that the receiver will be " + p.toString());
				//p.receive(ball);
			}	
		}
		
	}
	
}
