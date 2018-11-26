// Agent coach in project jazn

/* Initial beliefs and rules */

maxPlayers(11).
availableSwaps(3).

/* Initial goals */

!start.

/* Plans */

+!start <- .print("Hello, I'm ready!").

+!otherTeamHasScored(X) <- .print("Mmmh I'm thinking about swapping someone....");
						   ?shouldIChangeSomeone(X);
						   .print("Yeah it's better to do a swap.");
						   ?areThereAvailableSwaps;
						   -availableSwaps(S);
						   +availableSwaps(S-1);
						   .print("Ok, now I have other ", S-1, " swaps available.").

+!blamePlayersRandomly <- .print("Ma che cazzo faaaaaaiiiiiii").

+?shouldIChangeSomeone(X) : X > 2 <- .random(R); R > 0.5.
-?shouldIChangeSomeone(X) <- .print("No it's not time").

+?areThereAvailableSwaps : availableSwaps(X) <- X > 0.
-?areThereAvailableSwaps <- .print("Ops I don't have available swaps, sorry.").