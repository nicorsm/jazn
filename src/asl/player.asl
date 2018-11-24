// Agent player in project jazn

/* Initial beliefs and rules */

//!beginPlaying.

/* Initial goals */
!tryGoal.
!handleBall.
!passBall.
!wait_randomly.
//+startGame(referee) : true <- .print("OMG HAS WHISTLED!"); !handleBall.

+ball : true <-  !handleBall. //.print("GOT THE BALL, THANKS BRO!");

+!handleBall : ball & forward <- .print("CAN TRY GOAL"); !wait_randomly; .abolish(ball); !tryGoal. //
+!handleBall : ball & not forward <- .print("SHOULD PASS BALL"); !wait_randomly; .abolish(ball); !tryGoal. ////!passBall. //
-!handleBall : not ball <- true.

+!tryGoal <- playerAct.tryGoal.
+!tryGoal <- playerAct.tryGoal.

+!passBall : goalkeeper <- passTo_defender.
+!passBall : defender <- passTo_midfielder.
+!passBall : midfielder <- passTo_forward.


+!wait_randomly <-
	.random(R);
	.wait(R * 5000).
	
	
/* Plans */
