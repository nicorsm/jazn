package coachAct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import env.JaznEnvironment;
import env.JaznUtils;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;


public class replaceAPlayer extends DefaultInternalAction {
 
	@Override
    public Object execute( TransitionSystem ts,
                           Unifier un,
                           Term[] args ) throws Exception {

		String agName = ts.getUserAgArch().getAgName();
		Logger log = Logger.getLogger("jazn." + agName);
		
		Map<String,Team> coachNames = new HashMap<String,Team>();
		for(Team t : Team.values()) {
			coachNames.put(t.getCoachName(), t);
		}
		
		Team t = coachNames.get(ts.getUserAgArch().getAgName());
		
		List<IPlayer> players = JaznUtils.getFlatPlayers(JaznEnvironment.getPlayers(), t);
		IPlayer playerToKill = Utils.randomIn(players);
		ts.getAg().addBel(Literal.parseLiteral("swap(" + playerToKill.getName() + ")"));
		return true;
	}
}
