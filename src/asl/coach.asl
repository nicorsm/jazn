// Agent coach in project jazn

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start <- .print("Hello, I'm ready!").

+!otherTeamHasScored(X) <- .print("Mmmh I'm thinking about swapping someone....");
						   ?shouldIChangeSomeone(X);
						   .print("Yeah it's better to do a swap.").

-!otherTeamHasScored(X) <- .print("No it's not time").

+!blamePlayersRandomly <- .print("Ma che cazzo faaaaaaiiiiiii").

+?shouldIChangeSomeone(X) <- .random(R); .print(X, " --- ", R); X > 2 & R <= 0.5.