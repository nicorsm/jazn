package env;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;

public class JaznEnvironment extends Environment {

	public static final Literal prepareField = Literal.parseLiteral("prepareField");
	private JaznGame gameManager;
	private Logger log = Logger.getLogger("jazn."+JaznEnvironment.class.getName());
    
	@Override
	public void init(String[] args) {
		log.info("Environment initialized successfully.");
		gameManager = new JaznGame(this);
		
	}
	
	public void whistle(IPlayer kickoff) {
		log.info("Adding a percept for whistle");
		addPercept("referee", Literal.parseLiteral("whistle(referee)"));
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
					
					String rolePercept = "role(" + p.getRole().toString() + ")";
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
		return execute;
	}
	
	
}
