// Agent player in project jazn

/* Initial beliefs and rules */


whistled(_).

//ballIsMine :- .my_name(name) & ballOwnership(owner) & (ball == owner).

!beginPlaying.

/* Initial goals */


/* Plans */

+!beginPlaying : whistled(referee) <- true.


+whistled(referee): true <- .print("HOORAY!").



//+!startMatch : not gameEnded <- !kickBall.


/* 

+!handleBall <- !receiveBall;
				!move | !passBall.
				
+!move <- 

*/

//+!start : true <- .print("hello world.").

