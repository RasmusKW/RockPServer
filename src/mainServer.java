import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//creating the public class for the server code used to accept the sockets and players
public class mainServer{


public static void main(String[] args){
		
		
		//creating a new Thread
		new Thread( () -> {
			
			//using the try/catch exception handling
			try {
				
				//printing out a message to show that the server is now running 
				System.out.println("Server Started");
				//----------------------SOCKET SET UP----------------------
				 
				// Listen for a connection request
				ServerSocket serverSocket = new ServerSocket(9900);
				 
				
				//Creating a Players array to store all players 
				//The length of the array is equals to 3 since we only have 3 clients to connect to the server
				
				Player[] players = new Player[3];
				//for loop used to set the current position in the array to a new player
				for(int i=0; i<players.length; i++){
					players[i] = new Player();
				}
				
				 //for loop for filling in the Player array with players, assigns the socket to specific clients/players
				for(int i=0; i<players.length; i++){
					players[i].setSocket(serverSocket);
					//Prints out a message whenever every client is connected
					System.out.println("Client " + i + " is connected");
				}
				 //creates a new thread for the connected client
			     new Thread(new HandleSession(players)).start();
			}catch(IOException ex) {
			} 
			//starts the thread 
		}).start();
	
	
		} //end of server class
}


//HandleSession class starts and implements the Runnable interface
//Runnable is needed to run threads that causes objects' run method to be called in the specific thread 
class HandleSession implements Runnable{
	
	//initialize the array in which we store the players 
	private Player[] players;
	//initializing a variable that keeps track of whether a game is being played or on the verge to start 
	private boolean playAgain;	
    //assigning the array from the server to the array in the HandleSession 
	//to tell which specific players want to play
	public HandleSession(Player[] players){
		this.players = players;		
	}
	
	@Override
	public void run() {
	
		try{
		
			//prints out a message whenever all the players are connected
			System.out.println("All clients connected");
			Scanner qScan = new Scanner(System.in);
			
			// Create data input and output streams
			for(int i=0; i<players.length;  i++){
				players[i].setDataStreams();
			}
		 
		//--------------------------READING INPUTS--------------------------
		 
			//asks each player if they want to start the game
			for(int i=0; i<players.length;  i++){
				players[i].askWantToStartGame();
				System.out.println("Client " + i + " wants to start game: " + players[i].getWantToStartGame());
				//players[i].toClient.writeUTF("You are connected to server");
			}

			//asks every player to submit a username
			for(int i=0; i<players.length;  i++){
				players[i].setUsername();
				System.out.println("Client " + i + " username: " + players[i].username );
			}


			//each player selects their username
		 players[0].toClient.writeUTF(players[1].username);
		 players[0].toClient.writeUTF(players[2].username);
		 
		 players[1].toClient.writeUTF(players[0].username);
		 players[1].toClient.writeUTF(players[2].username);
		 
		 players[2].toClient.writeUTF(players[0].username);
		 players[2].toClient.writeUTF(players[1].username);
		 

		do {
		 //get tool
		 
		for(int i=0; i<players.length; i++){
			players[i].setTool();
			System.out.println(players[i].username + "'s tool: " + players[i].tool);
		}
			//each player selects their tool 
		players[0].toClient.writeInt(players[1].tool);
		players[0].toClient.writeInt(players[2].tool);
		
		players[1].toClient.writeInt(players[0].tool);
		players[1].toClient.writeInt(players[2].tool);
		
		players[2].toClient.writeInt(players[0].tool);
		players[2].toClient.writeInt(players[1].tool);
		
	     
	     //the combination of tools selected by players that would result in a tie
	     if((players[0].tool == 0 && players[1].tool == 0 && players[2].tool == 0) ||
		 	    	(players[0].tool == 0 && players[1].tool == 2 && players[2].tool == 1) ||
		 	    	(players[0].tool == 0 && players[1].tool == 1 && players[2].tool == 2) ||
		 	        (players[0].tool == 1 && players[1].tool == 1 && players[2].tool == 1) ||
		 	        (players[0].tool == 1 && players[1].tool == 0 && players[2].tool == 2) ||
		 	        (players[0].tool == 1 && players[1].tool == 2 && players[2].tool == 0) ||
		 	        (players[0].tool == 2 && players[1].tool == 2 && players[2].tool == 2) ||
		 	        (players[0].tool == 2 && players[1].tool == 1 && players[2].tool == 0) ||
		 	        (players[0].tool == 2 && players[1].tool == 0 && players[2].tool == 1) ) {
		 	  
		 	    	players[0].toClient.writeUTF("All players are tied");
		 	    	players[1].toClient.writeUTF("All players are tied");
		 	    	players[2].toClient.writeUTF("All players are tied");
		 	 
	
		 	     }
		 	     
	     //the combination of tools selected by players that would result in player 1 winning
		 	     if((players[0].tool == 0 && players[1].tool == 2 && players[2].tool == 2) ||
		 	    	(players[0].tool == 1 && players[1].tool == 0 && players[2].tool == 0) ||
		 	    	(players[0].tool == 2 && players[1].tool == 1 && players[2].tool == 1) ) {
		 	    	
		 	    	players[0].toClient.writeUTF(players[0].username + "wins");
		 	    	players[1].toClient.writeUTF(players[0].username + "wins");
		 	    	players[2].toClient.writeUTF(players[0].username + "wins");
		 	     }
		 	     
		 	     //the combination of tools selected by players that would result in player 2 winning
		 	     if((players[0].tool == 0 && players[1].tool == 1 && players[2].tool == 0) ||
		 	    	(players[0].tool == 1 && players[1].tool == 2 && players[2].tool == 1) ||
		 	    	(players[0].tool == 2 && players[1].tool == 0 && players[2].tool == 2) ) {
		 	    	
		 	    	players[0].toClient.writeUTF(players[1].username + "wins");
		 	    	players[1].toClient.writeUTF(players[1].username + "wins");
		 	    	players[2].toClient.writeUTF(players[1].username + "wins");
		 	     }
		 	     
		 	     //the combination of tools selected by players that would result in player 3 winning
		 	     if((players[0].tool == 0 && players[1].tool == 0 && players[2].tool == 1) ||
		 	 	    (players[0].tool == 1 && players[1].tool == 1 && players[2].tool == 2) ||
		 	 	    (players[0].tool == 2 && players[1].tool == 2 && players[2].tool == 0) ) {
		 	 	    	
		 	    	players[0].toClient.writeUTF(players[2].username + "wins");
		 	    	players[1].toClient.writeUTF(players[2].username + "wins");
		 	    	players[2].toClient.writeUTF(players[2].username + "wins");
		 	 	 }
		 	 	     
		 	     //PLAYER 2 & PLAYER 3 TIE
		 	     if((players[0].tool == 0 && players[1].tool == 1 && players[2].tool == 1) ||
		 	 	 	(players[0].tool == 1 && players[1].tool == 2 && players[2].tool == 2) ||
		 	 	 	(players[0].tool == 2 && players[1].tool == 0 && players[2].tool == 0) ) {
		 	 	 	    	
		 	    	players[0].toClient.writeUTF(players[1].username + "&" + players[2].username + "are tied");
		 	    	players[1].toClient.writeUTF(players[1].username + "&" + players[2].username + "are tied");
		 	    	players[2].toClient.writeUTF(players[1].username + "&" + players[2].username + "are tied");
		 	 	 	 }
		 	     
		 	     //PLAYER 1 & PLAYER 2 TIE
		 	     if((players[0].tool == 0 && players[1].tool == 0 && players[2].tool == 2) ||
		 	 	 	(players[0].tool == 1 && players[1].tool == 1 && players[2].tool == 0) ||
		 	 	 	(players[0].tool == 2 && players[1].tool == 2 && players[2].tool == 1) ) {
		 	 	 	    	
		 	    	players[0].toClient.writeUTF(players[1].username + "&" + players[0].username + "are tied");
		 	    	players[1].toClient.writeUTF(players[1].username + "&" + players[0].username + "are tied");
		 	    	players[2].toClient.writeUTF(players[1].username + "&" + players[0].username + "are tied");
		 	 	 	 }
		 	     
		 	     //PLAYER 1 & PLAYER 3 TIE
		 	     if((players[0].tool == 0 && players[1].tool == 2 && players[2].tool == 0) ||
		 	 	 	(players[0].tool == 1 && players[1].tool == 0 && players[2].tool == 1) ||
		 	 	 	(players[0].tool == 2 && players[1].tool == 1 && players[2].tool == 2) ) {
		 	 	 	    	
		 	    	players[0].toClient.writeUTF(players[0].username + "&" + players[2].username + "are tied");
		 	    	players[1].toClient.writeUTF(players[0].username + "&" + players[2].username + "are tied");
		 	    	players[2].toClient.writeUTF(players[0].username + "&" + players[2].username + "are tied");
		 	 	 	 }
		
		 	//asks each of the players if they want to play again     
		for(int i=0; i<players.length; i++){
			players[i].setPlayAgain();
			System.out.println("player "+i+ " wants to play again: "+ players[i].playAgain);
		}
		
	    //the condition in which players start a new game
	    if(players[0].playAgain.equals("y") && players[1].playAgain.equals("y") && players[2].playAgain.equals("y") ) {
	    	
	    	playAgain = true;
	    	
	    }
	    
	    //the condition in which players terminate the game
	    else if(players[0].playAgain.equals("n") || players[1].playAgain.equals("n")  || players[2].playAgain.equals("n") ){
	    	playAgain = false;
	    }
	    
	    
	   if(playAgain == true){ //if everyone wants to start a new game, this message gets printed to every player 
	   players[0].toClient.writeUTF("Everybody wants to play again! Lets do it");
	   players[1].toClient.writeUTF("Everybody wants to play again! Lets do it");
	   players[2].toClient.writeUTF("Everybody wants to play again! Lets do it");
	  
	   players[0].toClient.writeBoolean(playAgain);
	   players[1].toClient.writeBoolean(playAgain);
	   players[2].toClient.writeBoolean(playAgain);
	   }
	   
	   if(playAgain == false) { //if everyone wants to terminate the game, this message gets printed to every player 
		   players[0].toClient.writeUTF("A player selected no to play again, ending game");
		   players[1].toClient.writeUTF("A player selected no to play again, ending game");
		   players[2].toClient.writeUTF("A player selected no to play again, ending game");
		  
		   players[0].toClient.writeBoolean(playAgain);
		   players[1].toClient.writeBoolean(playAgain);
		   players[2].toClient.writeBoolean(playAgain);
	   }
	   
		}while(playAgain == true);
		System.exit(0);
		}catch(IOException ex) {
		} 
	}
	
}


