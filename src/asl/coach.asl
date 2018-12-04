// Agent coach in project jazn

maxPlayers(11).
availableSwaps(3).

!start.

+!start <- .print("Hello, I'm ready!").

+!otherTeamHasScored(X) <- .print("Mmmh I'm thinking about swapping someone....");
						   ?areThereAvailableSwaps;
						   .print("Yeah it's better to do a swap.");
						   ?shouldIChangeSomeone(X);
						   -availableSwaps(S);
						   +availableSwaps(S-1);
						   coachAct.replaceAPlayer;
						   .print("Ok, now I have other ", S-1, " swaps available.").

-!otherTeamHasScored(_) <- true.

+!ack <- .random(R); R > 0.8; .print("Datevi una mossa!!").
-!ack <- true.

+?shouldIChangeSomeone(X) : X > 2 <- .random(R); R > 0.5.
-?shouldIChangeSomeone(X) <- .print("No it's not time").

+?areThereAvailableSwaps : availableSwaps(X) <- X > 0.
-?areThereAvailableSwaps <- .print("Ops I don't have available swaps, sorry.").

+swap(X) <- .print(X, " is out!"); 
			.kill_agent(X);
			.create_agent(X, "player.asl");
			.print(X, " is in!");
			-swap(X).
				   