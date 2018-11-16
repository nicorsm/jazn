// Agent player in project jazn

/* Initial beliefs and rules */

//!beginPlaying.

/* Initial goals */

//+!refereeHasWhistled : not playing <- -playing; .print("OMG HAS WHISTLED!").
+startGame(referee) : true <- .print("OMG HAS WHISTLED!"); !handleBall.

+!handleBall : ball & role(FORWARD) <- .print("CAN TRY GOAL").// !tryGoal.
+!handleBall : ball & ~role(FORWARD)<- .print("SHOULD PASS BALL").// !passBall.
+!handleBall : ~ball <- .print("NO BALL"); true.

+!tryGoal <- true.
+!passBall <- true.


/* Plans */

//+!beginPlaying : whistled(referee) <- .print("URRA!").


//+!whistled(referee): true <- .print("HOORAY!").



//+!startMatch : not gameEnded <- !kickBall.


/* 

+!handleBall <- !receiveBall;
				!move | !passBall.
				
+!move <- 

*/

//+!start : true <- .print("hello world.").

