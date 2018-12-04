// Agent player in project jazn

+ball <-  !handleBall.

+!handleBall : ball & role(forward) <- .print("Whoa I can try a goal..."); 
								 !wait_randomly;
								 .abolish(ball); 
								 !tryGoal.
								 
+!handleBall : ball & not role(forward) <- .print("I'll pass the ball..."); 
									 !wait_randomly; 
									 .abolish(ball);
									 !passBall;
									 !ackCoach.
									 
-!handleBall : not ball <- true.

+!tryGoal <- .print("I'll try a goal!");
			 ?shouldTryGoal;
			 .print("GOOOOAAAAALLLLL!!!!! I'll tell it to the referee...");
			 !notifyReferee;
			 .print("I'll pass back the ball to the midfielder");
			 passTo(midfielder).
			 
-!tryGoal <- .print("No way :(");
			 .print("I'll pass the ball to the goalkeeper of the other team.");
			 passTo(goalkeeper).

+!passBall : role(goalkeeper) <- passTo(defender).
+!passBall : role(defender) <- passTo(midfielder).
+!passBall : role(midfielder) <- passTo(forward).

+!notifyReferee : team(X) <- .send(referee, achieve, scored(X)).

+!ackCoach : team(X) <- if ( X == cesenaUnited ) {
							.send(cuCoach, achieve, ack);
						} elif ( X == forliCity ) {
							.send(fcCoach, achieve, ack);
						}.
			 

+!wait_randomly <-
	.random(R);
	.wait(R * 5000).

+?shouldTryGoal <- .random(R); R > 0.8.
+?shouldInterceptBall <- .random(R); R > 0.6 & (goalkeeper | defender | midfielder).
