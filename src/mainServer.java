import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class mainServer{
//private static ServerSocket serverSocket;

public static void main(String[] args){
		
		
		
		new Thread( () -> {
			try {
				System.out.println("Server Started");
				//----------------------SOCKET SET UP----------------------
				 
				
				ServerSocket serverSocket = new ServerSocket(9900);
				 // Listen for a connection request
				
				Player[] players = new Player[3];
				for(int i=0; i<players.length; i++){
					players[i] = new Player();
				}
				
				 //--
				for(int i=0; i<players.length; i++){
					players[i].setSocket(serverSocket);
					System.out.println("Client " + i + " is connected");
				}
				 
			     new Thread(new HandleSession(players)).start();
			}catch(IOException ex) {
			} 
		}).start();
	
	
		} //main
}

class HandleSession implements Runnable{
	
	private Player[] players;	
	private boolean playAgain;	

	public HandleSession(Player[] players){
		this.players = players;		
	}
	
	@Override
	public void run() {
	
		try{
		
			
			System.out.println("All clients connected");
			Scanner qScan = new Scanner(System.in);
			
			// Create data input and output streams
			for(int i=0; i<players.length;  i++){
				players[i].setDataStreams();
			}
		 
		//--------------------------READING INPUTS--------------------------
		 
			
			for(int i=0; i<players.length;  i++){
				players[i].askWantToStartGame();
				System.out.println("Client " + i + " wants to start game: " + players[i].getWantToStartGame());
				//players[i].toClient.writeUTF("You are connected to server");
			}

			for(int i=0; i<players.length;  i++){
				players[i].setUsername();
				System.out.println("Client " + i + " username: " + players[i].username );
			}


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
			
		players[0].toClient.writeInt(players[1].tool);
		players[0].toClient.writeInt(players[2].tool);
		
		players[1].toClient.writeInt(players[0].tool);
		players[1].toClient.writeInt(players[2].tool);
		
		players[2].toClient.writeInt(players[0].tool);
		players[2].toClient.writeInt(players[1].tool);
		
	     
	     //everyone tie
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
		 	     
	     //PLAYER 1 WIN
		 	     if((players[0].tool == 0 && players[1].tool == 2 && players[2].tool == 2) ||
		 	    	(players[0].tool == 1 && players[1].tool == 0 && players[2].tool == 0) ||
		 	    	(players[0].tool == 2 && players[1].tool == 1 && players[2].tool == 1) ) {
		 	    	
		 	    	players[0].toClient.writeUTF(players[0].username + "wins");
		 	    	players[1].toClient.writeUTF(players[0].username + "wins");
		 	    	players[2].toClient.writeUTF(players[0].username + "wins");
		 	     }
		 	     
		 	     //PLAYER 2 WIN
		 	     if((players[0].tool == 0 && players[1].tool == 1 && players[2].tool == 0) ||
		 	    	(players[0].tool == 1 && players[1].tool == 2 && players[2].tool == 1) ||
		 	    	(players[0].tool == 2 && players[1].tool == 0 && players[2].tool == 2) ) {
		 	    	
		 	    	players[0].toClient.writeUTF(players[1].username + "wins");
		 	    	players[1].toClient.writeUTF(players[1].username + "wins");
		 	    	players[2].toClient.writeUTF(players[1].username + "wins");
		 	     }
		 	     
		 	     //PLAYER 3 WIN
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
		
		 	     
		for(int i=0; i<players.length; i++){
			players[i].setPlayAgain();
			System.out.println("player "+i+ " wants to play again: "+ players[i].playAgain);
		}
		
	    
	    if(players[0].playAgain.equals("y") && players[1].playAgain.equals("y") && players[2].playAgain.equals("y") ) {
	    	
	    	playAgain = true;
	    	
	    }
	    else if(players[0].playAgain.equals("n") || players[1].playAgain.equals("n")  || players[2].playAgain.equals("n") ){
	    	playAgain = false;
	    }
	    
	    
	   if(playAgain == true){
	   players[0].toClient.writeUTF("Everybody wants to play again! Lets do it");
	   players[1].toClient.writeUTF("Everybody wants to play again! Lets do it");
	   players[2].toClient.writeUTF("Everybody wants to play again! Lets do it");
	  
	   players[0].toClient.writeBoolean(playAgain);
	   players[1].toClient.writeBoolean(playAgain);
	   players[2].toClient.writeBoolean(playAgain);
	   }
	   
	   if(playAgain == false) {
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


