package playerAct;

import java.util.logging.Logger;

import env.JaznEnvironment;
import env.JaznUtils;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;

public class tryGoal extends DefaultInternalAction {


	 
	@Override
    public Object execute( TransitionSystem ts,
                           Unifier un,
                           Term[] args ) throws Exception {

		String agName = ts.getUserAgArch().getAgName();

		Logger log = Logger.getLogger("jazn." + agName);
		
		log.info(agName);
		
		
		
		if(!args[0].isVar()) {
			return null;
		}
		
		System.out.println("ARGS 0 is " + args[0]);

		IPlayer player = JaznUtils.getPlayerFromAgentName(agName);
		
		//clearBall(agName);

		log.info("OMG " + player.toString() + " IS TRYING A GOAL...");
		

		if(Utils.shouldTryGoal()) {
			//this.scored(player);
		} else {
			log.info("No way :(");
		}
		
		Team other = player.getTeam().getOtherTeam();
		IPlayer goalkeeper = JaznUtils.getPlayers(other, Role.GOALKEEPER).get(0);
		log.info("ACTION Ball is now of " + goalkeeper.toString());
		//passBall(goalkeeper.getName(), goalkeeper.getRole());
	
		return args;
		
		
	}
	
}
