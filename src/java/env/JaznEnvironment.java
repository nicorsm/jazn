package env;

import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

public class JaznEnvironment extends Environment {

	public static final Literal prepareField = Literal.parseLiteral("prepareField");
	private JaznGame gameManager;
	private Logger log = Logger.getLogger("jazn."+JaznEnvironment.class.getName());
    
	@Override
	public void init(String[] args) {
		log.info("Environment initialized successfully.");
		gameManager = new JaznGame();
		
	}
	
	public void whistle() {
		addPercept(p.getName(),Literal.parseLiteral("gameEnded"));
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
