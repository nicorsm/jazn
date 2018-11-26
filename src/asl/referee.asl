// Agent referee in project jazn

/* Initial beliefs and rules */

cu_goals(0).
fc_goals(0).

/* Initial goals */

!startMatch.

/* Plans */

+!startMatch : true <- prepareField.

+whistled : whistled <- !tellWhistle.

+!tellWhistle: whistled <- .print("WHISTLING..."); .broadcast(tell, startGame(referee)).


+cuHasScored <- -cu_goals(X); 
				+cu_goals(X+1); 
				.print("Cesena United has scored: ", X+1);
				!printCompleteScore.
				
+fcHasScored <- -fc_goals(X); 
				+fc_goals(X+1); 
				.print("Forli City has scored: ", X+1);
				!printCompleteScore.

+!printCompleteScore : fc_goals(X) & cu_goals(Y) <- .print("Current score is \n - Forli City: ", X, "\n - Cesena United: ", Y).
