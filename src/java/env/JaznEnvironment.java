/*
 dNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNd
mMd//////////////////////////////////dMm
mMy                                  yMm
mMy         `ooooo-     /oo:         yMm
mMy         `ssyMM+    -MMMN.        yMm
mMy            :MM+    mM+hMd        yMm
mMy            :MM+   oMm`-MM+       yMm
mMy       mNy  oMM:  -MMMMMMMN.      yMm
dMm:      NMMNMMN+   mMy   `mMh     :mMd
`oNMm:    .....`     ..     `..   :mMNo`
`oNMd:    ........    ..   `..    :dMNo`
mMm:      NMMMMMMM-  /MMh` +MM      :mMd
mMy          .hMN+   /MMMd`+MM       yMm
mMy         :NMd.    /MMyMdsMM       yMm
mMy        sMMs      /MM.oMMMM       yMm
mMy       dMMmyyyy-  /MM. oMMM       yMm
mMy       +ooooooo.  .oo`  /oo       yMm
mMy                                  yMm
mMd//////////////////////////////////dMm
dNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNd
 */

package env;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import model.impl.Player;
import model.impl.Role;
import model.impl.Team;
import model.interfaces.IPlayer;
import utils.Utils;

public class JaznEnvironment extends Environment {

	public static final Literal prepareField = Literal.parseLiteral("prepareField");
	public static final Literal ball = Literal.parseLiteral("ball");
	public static final Literal whistled = Literal.parseLiteral("whistled");
	public static final String passTo = "passTo";
	public static Date whistleDate;

	private static Map<Team, Map<Role, List<IPlayer>>> players = new HashMap<Team, Map<Role, List<IPlayer>>>();
	private Logger log = Logger.getLogger("jazn." + JaznEnvironment.class.getName());

	@Override
	public void init(String[] args) {
		log.info("Environment initialized successfully.");
	}

	@Override
	public boolean executeAction(String agName, Structure action) {
		boolean execute = false;

		if (action.equals(prepareField)) {
			log.info("Preparing field...");
			this.prepareField();
			execute = true;
		}

		if (action.getFunctor().equals(passTo)) {

			Date d = new Date();

			if (d.getTime() > whistleDate.getTime() + 5 * 60 * 1000) { // Stops after 5 mins.
				// Time elapsed, referee....
				log.info("Time elapsed!");
				removePercept("referee", whistled);
			} else {
				String term = action.getTerms().get(0).toString().toUpperCase();
				boolean sameTeam = Boolean.parseBoolean(action.getTerms().get(1).toString());
				Role r = Role.valueOf(term);
				passBall(agName, r, sameTeam);
			}

			execute = true;
		}

		return execute;
	}

	public static Map<Team, Map<Role, List<IPlayer>>> getPlayers() {
		return players;
	}

	private void passBall(String sender, Role r, boolean sameTeam) {

		removePercept(sender, ball);

		IPlayer senderAgent = JaznUtils.getPlayerFromAgentName(sender);
		Role inverse = senderAgent.getRole().getInverse();
		boolean intercepts = JaznUtils.shouldInterceptBall();

		if (!sameTeam) { // Rimessa

			intercepts = true;

			if (r == Role.GOALKEEPER) {
				inverse = Role.GOALKEEPER;
			} else if (r == Role.FORWARD) {
				inverse = Role.MIDFIELDER;
			}
		}

		for (Team t : Team.values()) {

			boolean isFromMyTeam = sender.startsWith(t.getShortName());

			if ((isFromMyTeam && !intercepts) || (!isFromMyTeam && intercepts)) {

				Role role = r;
				if (intercepts && inverse != null) {
					role = inverse;
				}

				List<IPlayer> nextLine = JaznUtils.getPlayers(t, role);
				IPlayer p = Utils.randomIn(nextLine); // can also be a peer

				if (intercepts) {
					if (sameTeam) {
						log.info("Ouch, ball was intercepted by " + p.toString());
					} else {
						log.info("Ball was passed to " + p.toString());
					}
				} else {
					log.info("Passing ball to " + p.toString());
				}

				removePercept(p.getName(), ball);
				addPercept(p.getName(), ball);
				return;
			}
		}

	}

	public void prepareField() {

		log.info("The referee is preparing the match...");

		this.buildSchema();

		Team randomTeam = Utils.randomIn(Team.values());
		IPlayer kickoff = Utils.randomIn(players.get(randomTeam).get(Role.MIDFIELDER));
		log.info("The player assigned for kickoff is " + kickoff.toString());
		log.info("Referee, WHISTLE!");

		addPercept("referee", whistled);
		whistleDate = new Date();
		addPercept(kickoff.getName(), ball);
	}

	private void buildSchema() {

		Team[] teams = Team.values();

		log.info("Players on the pitch for today's match between " + teams[0] + " and " + teams[1] + " are: ");

		for (Team t : teams) {
			int jersey = 1;

			HashMap<Role, List<IPlayer>> rls = new HashMap<Role, List<IPlayer>>();

			for (Role r : Role.values()) {
				ArrayList<IPlayer> pls = new ArrayList<IPlayer>();

				for (int i = 0; i < r.getCount(); i++) {
					IPlayer p = new Player(t, jersey, r);
					log.info(" - " + p.toString());
					pls.add(p);

					addPercept(p.getName(), Literal.parseLiteral("team(" + t.getShortName() + ")"));
					addPercept(p.getName(), Literal.parseLiteral("jersey(" + p.getJerseyNumber() + ")"));
					addPercept(p.getName(), Literal.parseLiteral("role(" + r.toString().toLowerCase() + ")"));

					jersey++;
				}

				rls.put(r, pls);
			}
			players.put(t, rls);
		}

	}

}
