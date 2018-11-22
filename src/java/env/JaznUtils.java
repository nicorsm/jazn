package env;

import java.util.List;
import java.util.Map;

import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;

public class JaznUtils {

	public static List<IPlayer> getPlayers(JaznEnvironment context, Team t, Role r) {
		return context.getPlayers().get(t).get(r);
	}

	public static IPlayer getPlayerFromAgentName(JaznEnvironment context, String agentName) {
		for (Team t : Team.values()) {
			if (agentName.startsWith(t.getShortName())) {
				int jersey = Integer.parseInt(agentName.replace(t.getShortName(), ""));

				Map<Role, List<IPlayer>> rose = context.getPlayers().get(t);

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

	public static int goalsForTeam(List<IPlayer> goals, Team t) {
		int scored = 0;
		for (IPlayer p : goals) {
			if (p.getTeam() == t) {
				scored++;
			}
		}
		return scored;
	}
	

}
