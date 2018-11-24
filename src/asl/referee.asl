// Agent referee in project jazn

/* Initial beliefs and rules */

/* Initial goals */

!startMatch.

/* Plans */

+!startMatch : true <- prepareField.

+whistled : whistled <- !tellWhistle.

+!tellWhistle: whistled <- .print("WHISTLING..."); .broadcast(tell, startGame(referee)).


+scored <- .print("Someone has scored").

//+whistled(_): ~whistled(referee) <- -~whistled(referee).

//if not whistled, do no-op.

