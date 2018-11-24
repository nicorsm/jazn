package playerAct;

import java.util.logging.Logger;

import env.JaznUtils;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;

public class playerToPass extends DefaultInternalAction {

	private static Literal gkLit = Literal.parseLiteral(Role.GOALKEEPER.name().toLowerCase());
	private static Literal mfLit = Literal.parseLiteral(Role.MIDFIELDER.name().toLowerCase());
	@Override
    public Object execute( TransitionSystem ts,
                           Unifier un,
                           Term[] args ) throws Exception {

		if(!args[0].isLiteral()) {
			return null;
		}
		
		Literal lit = (Literal)args[0];
		IPlayer sender = JaznUtils.getPlayerFromAgentName(ts.getUserAgArch().getAgName());
		IPlayer receiver = null;
		
		if(lit.equals(gkLit)) {
			receiver = JaznUtils.getPlayers(sender.getTeam().getOtherTeam(), Role.GOALKEEPER).get(0);
		} else if (lit.equals(mfLit)) {
			receiver = Utils.randomIn(JaznUtils.getPlayers(sender.getTeam(), Role.MIDFIELDER));
		} else { 
			return null;
		}
		return Literal.parseLiteral(receiver.getName());
	}
}
