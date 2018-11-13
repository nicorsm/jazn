// Agent player in project jazn

/* Initial beliefs and rules */

//ballIsMine :- .my_name(name) & ballOwnership(owner) & (ball == owner).

//!beginPlaying.

/* Initial goals */


/* Plans */

//+!beginPlaying <- ?hasWhistled(referee).




//+!startMatch : not gameEnded <- !kickBall.


/* 

+!handleBall <- !receiveBall;
				!move | !passBall.
				
+!move <- 

*/

//+!start : true <- .print("hello world.").

