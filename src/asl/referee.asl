// Agent referee in project jazn

cu_goals(0).
fc_goals(0).

!startMatch.

+!startMatch : true <- prepareField.

+whistled : whistled <- .print("WHISTLING..."); 
						.broadcast(tell, startGame(referee)).
						+whistleTime(.time).
						
-whistled <- .print("WHISTLED! GAME ENDED!"); 
						!printCompleteScore;
						.wait(10000);
						.stopMAS.

+!scored(cesenaUnited) <- -cu_goals(X); 
					      +cu_goals(X+1);
					      .print("Cesena United has scored: ", X+1);
					      .send(fcCoach, achieve, otherTeamHasScored(X+1));
					      !printCompleteScore.
				
+!scored(forliCity) <- -fc_goals(X);
					   +fc_goals(X+1);
					   .print("Forli City has scored: ", X+1);
					   .send(cuCoach, achieve, otherTeamHasScored(X+1));
					   !printCompleteScore.

+!printCompleteScore : fc_goals(X) & cu_goals(Y) <- .print("\n - Forli City: ", X, "\n - Cesena United: ", Y).
