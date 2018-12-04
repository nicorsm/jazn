package env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;

public class JaznUtils {

	public static List<IPlayer> getPlayers(Team t, Role r) {
		return JaznEnvironment.getPlayers().get(t).get(r);
	}

	public static IPlayer getPlayerFromAgentName(String agentName) {
		for (Team t : Team.values()) {
			if (agentName.startsWith(t.getShortName())) {
				int jersey = Integer.parseInt(agentName.replace(t.getShortName(), ""));

				Map<Role, List<IPlayer>> rose = JaznEnvironment.getPlayers().get(t);

				for (Role r : rose.keySet()) {
					for (IPlayer p : rose.get(r)) {
						if (p.getJerseyNumber() == jersey) {
							return p;
						}
					}
				}
			}
		}
		return null;
	}

	public static List<IPlayer> getFlatPlayers(Map<Team, Map<Role, List<IPlayer>>> players) {
		return getFlatPlayers(players, null);
	}
	
	public static List<IPlayer> getFlatPlayers(Map<Team, Map<Role, List<IPlayer>>> players, Team team) {
		List<IPlayer> plist = new ArrayList<IPlayer>();

		for(Team t: players.keySet()) {
			
			if(team == null || team == t) {
				Map<Role, List<IPlayer>> rose = players.get(t);
				
				for(Role r: rose.keySet()) {
					
					List<IPlayer> pls = rose.get(r);
						
					for(IPlayer p: pls) {
						plist.add(p);
					}
				}
			}
			
		}
		
		return plist;
	}

	public static boolean shouldInterceptBall() {
		return Math.random() > 0.60;
	}
}
