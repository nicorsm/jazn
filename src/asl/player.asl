// Agent player in project jazn

/* Initial beliefs and rules */

//!beginPlaying.

/* Initial goals */

//+startGame(referee) : true <- .print("OMG HAS WHISTLED!"); !handleBall.

+ball : true <-  !handleBall. //.print("GOT THE BALL, THANKS BRO!");

+!handleBall : ball & forward <- .print("CAN TRY GOAL"); !wait_randomly; .abolish(ball); tryGoal. //
+!handleBall : ball & not forward <- .print("SHOULD PASS BALL"); !wait_randomly; .abolish(ball); !passBall. //
+!handleBall : not ball <- true.

+!passBall : goalkeeper <- passTo_defender.
+!passBall : defender <- passTo_midfielder.
+!passBall : midfielder <- passTo_forward.


+!wait_randomly <-
	.random(R);
	.wait(R * 5000).
	
	
/* Plans */

//+!beginPlaying : whistled(referee) <- .print("URRA!").


//+!whistled(referee): true <- .print("HOORAY!").



//+!startMatch : not gameEnded <- !kickBall.
/*
 * 
 * 
	public void receive(IBall ball) {

		Role roleToPass = null;
		boolean tryGoal = false;
		ball.setOwner(this);
		System.out.println(this.toString() + " is deciding who will be the receiver of the ball");
		
		switch(this.role){
			case DEFENDER: 		roleToPass = Role.MIDFIELDER; 	break;
			case FORWARD:  		tryGoal = true; 				break;
			case GOALKEEPER:	roleToPass = Role.DEFENDER;		break;
			case MIDFIELDER:	roleToPass = Role.FORWARD;		break;
			default:											break;
		}

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(tryGoal) {
			System.out.println(this.toString() + " is trying to score...");
			Referee.getInstance().scored(this);
		} else if(roleToPass != null) {
			IPlayer p = Utils.randomIn(Referee.getInstance().getPlayers(this.team, roleToPass)); // can also be a peer
			System.out.println(this.toString() + " says that the receiver will be " + p.toString());
			p.receive(ball);
		}
		
		
	}
	
 * 
 */

/* 

+!handleBall <- !receiveBall;
				!move | !passBall.
				
+!move <- 

*/

//+!start : true <- .print("hello world.").

