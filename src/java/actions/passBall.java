package actions;

import java.util.List;

import env.JaznGame;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;

public class passBall extends DefaultInternalAction {
	
    @Override
    public Object execute( TransitionSystem ts, Unifier un, Term[] args ) throws Exception {
 
		List<Literal> bels = ts.getAg().getInitialBels();
		
		Role target = null;
		
		for(Literal l: bels) {
			for(Role r: Role.values()) {
				if(l.toString() == "role("+r.name()+")") {
					
					switch(r) {
					case DEFENDER: target = Role.MIDFIELDER; break;
					case FORWARD: break;
					case GOALKEEPER: target = Role.DEFENDER; break;
					case MIDFIELDER: target = Role.FORWARD; break;
					default:
						break;
					}
				}
			}
		}
		

		for(Team t : Team.values()) {
			if(ts.getAg().toString().startsWith(t.getShortName())) {
				List<IPlayer> players = JaznGame.getInstance().getPlayers(t, target);
				IPlayer p = Utils.randomIn(players); // can also be a peer
				return p.getName();
				
				//addPercept(p.getName(), Literal.parseLiteral("ball"));
				//System.out.println(this.toString() + " says that the receiver will be " + p.toString());
				//p.receive(ball);
			}	
		}
		
		return "";
    }

}
