// Agent player in project jazn

/* Initial beliefs and rules */

//!beginPlaying.

/* Initial goals */
//!tryGoal.
//!handleBall.
//!wait_randomly.

//+startGame(referee) : true <- .print("OMG HAS WHISTLED!"); !handleBall.

+ball <-  !handleBall. //.print("GOT THE BALL, THANKS BRO!");

+!handleBall : ball & forward <- .print("CAN TRY GOAL"); 
								 !wait_randomly;
								 .abolish(ball); 
								 !tryGoal. //
								 
+!handleBall : ball & not forward <- .print("SHOULD PASS BALL"); 
									 !wait_randomly; 
									 .abolish(ball);
									 !passBall. //
-!handleBall : not ball <- true.

+!tryGoal <- .print("I'll try a goal!");
			 ?shouldTryGoal;
			 .print("GOOOOAAAAALLLLL!!!!! I'll tell it to the referee...");
			 .send(referee, tell, scored);
			 .print("I'll pass back the ball to the midfielder");
			 passTo_midfielder.
			 
-!tryGoal <- .print("No way :(");
			 .print("I'll pass the ball to the goalkeeper of the other team.");
			 passTo_goalkeeper.

+!passBall : goalkeeper <- passTo_defender.
+!passBall : defender <- passTo_midfielder.
+!passBall : midfielder <- passTo_forward.

+!passBallAfterGoal(recipient) <- .print(recipient); .send(recipient, tell, ball).

+!wait_randomly <-
	.random(R);
	.wait(R * 0).

+?shouldTryGoal <- .random(R); R > 0.8.
+?shouldInterceptBall <- .random(R); R > 0.6 & (goalkeeper | defender | midfielder).
