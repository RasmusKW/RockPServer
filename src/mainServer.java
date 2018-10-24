import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class mainServer {
	public static void main(String[] args){
		
			new Thread( () -> {
				try {
					
					//----------------------SOCKET SET UP----------------------

					System.out.println("Server Started");
					
					 ServerSocket serverSocket = new ServerSocket(9300);
					 // Listen for a connection request
					 //--
					 Socket socket_player1 = serverSocket.accept();
					 System.out.println("Client 1: Connected");
							  
					 Socket socket_player2 = serverSocket.accept();
					 System.out.println("Client 2: Connected");
					 
					 Socket socket_player3 = serverSocket.accept();
					 System.out.println("Client 3: Connected");
					 
					 
				     new Thread(new HandleSession(socket_player1, socket_player2, socket_player3)).start();
				}catch(IOException ex) {
				} 
			}).start();
		
		
			} //main
}


class HandleSession implements Runnable{
	
	private Socket socket_player1;
	private Socket socket_player2;
	private Socket socket_player3;
	private static int result;
	private boolean playAgain;
	public HandleSession(Socket socket_player1, Socket socket_player2, Socket socket_player3){
		this.socket_player1 = socket_player1;
		this.socket_player2 = socket_player2;
		this.socket_player3 = socket_player3;
	}
	@Override
	public void run() {
		
		
try{
		
			
			System.out.println("All clients connected");
			Scanner qScan = new Scanner(System.in);
			
			// Create data input and output streams
			  DataInputStream inputFromClient1 = new DataInputStream(
			  socket_player1.getInputStream());
			  DataOutputStream outputToClient1 = new DataOutputStream(
			  socket_player1.getOutputStream());
			
			 // Create data input and output streams
			  DataInputStream inputFromClient2 = new DataInputStream(
			  socket_player2.getInputStream());
			  DataOutputStream outputToClient2 = new DataOutputStream(
			  socket_player2.getOutputStream());
		 
			  DataInputStream inputFromClient3 = new DataInputStream(
			socket_player3.getInputStream());
			DataOutputStream outputToClient3 = new DataOutputStream(
				socket_player3.getOutputStream());
			  
		 //--System.out.println("hello4 im after datainputstreams");
		 
		//--------------------------READING INPUTS--------------------------
		 
		 //reading yes or no want to start a game
		 String wantToStartGame = inputFromClient1.readUTF();
		 System.out.println("Client 1 wants to start game: " + wantToStartGame);
		 //outputToClient1.writeUTF("You are connected to server");
		 
		 String wantToStartGame2 = inputFromClient2.readUTF();
		 System.out.println("Client 2 wants to start game: " + wantToStartGame2);
		// outputToClient2.writeUTF("You are connected to server");
		 
		 String wantToStartGame3 = inputFromClient3.readUTF();
		 System.out.println("Client 3 wants to start game: " + wantToStartGame3);
		 //outputToClient3.writeUTF("You are connected to server");
		 
		 
		 //get username
		 String username1 = inputFromClient1.readUTF();
		 System.out.println("Client 1 username: " + username1 );
		 
		 String username2 = inputFromClient2.readUTF();
		 System.out.println("Client 2 username: " + username2 );
		
		 String username3 = inputFromClient3.readUTF();
		 System.out.println("Client 3 username: " + username3 );
		 
		 
	     outputToClient1.writeUTF(username2);
	     outputToClient1.writeUTF(username3);
	     outputToClient2.writeUTF(username1);
	     outputToClient2.writeUTF(username3);
	     outputToClient3.writeUTF(username1);
	     outputToClient3.writeUTF(username2);
	     
	     do {
			 //get tool
			 
		     int player1_tool = inputFromClient1.readInt();
			 System.out.println(username1 + "'s tool: " + player1_tool);
			 
		     int player2_tool = inputFromClient2.readInt();
			 System.out.println(username2 + "'s tool: " + player2_tool);
			 
			 int player3_tool = inputFromClient3.readInt();
			 System.out.println(username3 + "'s tool: " + player3_tool);
			 
		     outputToClient1.writeInt(player2_tool);
		     outputToClient1.writeInt(player3_tool);
		     
		     outputToClient2.writeInt(player1_tool);
		     outputToClient2.writeInt(player3_tool);
		
		     outputToClient3.writeInt(player1_tool);
		     outputToClient3.writeInt(player2_tool);
		     
		     
		     
		     if((player1_tool == 0 && player2_tool == 0 && player3_tool == 0) ||
			 	    	(player1_tool == 0 && player2_tool == 2 && player3_tool == 1) ||
			 	    	(player1_tool == 0 && player2_tool == 1 && player3_tool == 2) ||
			 	        (player1_tool == 1 && player2_tool == 1 && player3_tool == 1) ||
			 	        (player1_tool == 1 && player2_tool == 0 && player3_tool == 2) ||
			 	        (player1_tool == 1 && player2_tool == 2 && player3_tool == 0) ||
			 	        (player1_tool == 2 && player2_tool == 2 && player3_tool == 2) ||
			 	        (player1_tool == 2 && player2_tool == 1 && player3_tool == 0) ||
			 	        (player1_tool == 2 && player2_tool == 0 && player3_tool == 1) ) {
			 	    	 result = 0;
			 	    	outputToClient1.writeInt(result);
			 	    	outputToClient2.writeInt(result);
			 	    	outputToClient3.writeInt(result);
			 	     }
			 	     
			 	     //PLAYER 1 WIN
			 	     if((player1_tool == 0 && player2_tool == 2 && player3_tool == 2) ||
			 	    	(player1_tool == 1 && player2_tool == 0 && player3_tool == 0) ||
			 	    	(player1_tool == 2 && player2_tool == 1 && player3_tool == 1) ) {
			 	    	 result = 1;
			 	    	outputToClient1.writeInt(result);
			 	    	outputToClient2.writeInt(result);
			 	    	outputToClient3.writeInt(result);
			 	     }
			 	     
			 	     //PLAYER 2 WIN
			 	     if((player1_tool == 0 && player2_tool == 1 && player3_tool == 0) ||
			 	    	(player1_tool == 1 && player2_tool == 2 && player3_tool == 1) ||
			 	    	(player1_tool == 2 && player2_tool == 0 && player3_tool == 2) ) {
			 	    	 result = 2;
			 	    	outputToClient1.writeInt(result);
			 	    	outputToClient2.writeInt(result);
			 	    	outputToClient3.writeInt(result);
			 	    	
			 	     }
			 	     
			 	     //PLAYER 3 WIN
			 	     if((player1_tool == 0 && player2_tool == 0 && player3_tool == 1) ||
			 	 	    (player1_tool == 1 && player2_tool == 1 && player3_tool == 2) ||
			 	 	    (player1_tool == 2 && player2_tool == 2 && player3_tool == 0) ) {
			 	 	    	 result = 3;
			 	 	    	outputToClient1.writeInt(result);
				 	    	outputToClient2.writeInt(result);
				 	    	outputToClient3.writeInt(result);
			 	 	 }
			 	 	     
			 	     //PLAYER 2 & PLAYER 3 TIE
			 	     if((player1_tool == 0 && player2_tool == 1 && player3_tool == 1) ||
			 	 	 	(player1_tool == 1 && player2_tool == 2 && player3_tool == 2) ||
			 	 	 	(player1_tool == 2 && player2_tool == 0 && player3_tool == 0) ) {
			 	 	 	    	 result = 4;
			 	 	 	    	outputToClient1.writeInt(result);
					 	    	outputToClient2.writeInt(result);
					 	    	outputToClient3.writeInt(result);
			 	 	 	 }
			 	     
			 	     //PLAYER 1 & PLAYER 2 TIE
			 	     if((player1_tool == 0 && player2_tool == 0 && player3_tool == 2) ||
			 	 	 	(player1_tool == 1 && player2_tool == 1 && player3_tool == 0) ||
			 	 	 	(player1_tool == 2 && player2_tool == 2 && player3_tool == 1) ) {
			 	 	 	    	 result = 5;
			 	 	 	    	outputToClient1.writeInt(result);
					 	    	outputToClient2.writeInt(result);
					 	    	outputToClient3.writeInt(result);
			 	 	 	 }
			 	     
			 	     //PLAYER 1 & PLAYER 3 TIE
			 	     if((player1_tool == 0 && player2_tool == 2 && player3_tool == 0) ||
			 	 	 	(player1_tool == 1 && player2_tool == 0 && player3_tool == 1) ||
			 	 	 	(player1_tool == 2 && player2_tool == 1 && player3_tool == 2) ) {
			 	 	 	    	 result = 6;
			 	 	 	    	outputToClient1.writeInt(result);
					 	    	outputToClient2.writeInt(result);
					 	    	outputToClient3.writeInt(result);
			 	 	 	 }
			
			String playAgain1 = inputFromClient1.readUTF();
			String playAgain2 = inputFromClient2.readUTF();
			String playAgain3 = inputFromClient3.readUTF();
			
			System.out.println("users output" + playAgain1 + playAgain2 + playAgain3);
		     
		    if(playAgain1.equals("y") && playAgain2.equals("y") && playAgain3.equals("y") ) {
		    	
		    	playAgain = true;
		    	 System.out.println("inside if " + playAgain);
		    }
		    else if(playAgain1.equals("n") || playAgain2.equals("n") || playAgain3.equals("n")){
		    	playAgain = false;
		    }
		    
		   
		   
		   if(playAgain == true){
		   outputToClient1.writeUTF("Everybody wants to play again! Let's do it");
		   outputToClient2.writeUTF("Everybody wants to play again! Let's do it");
		   outputToClient3.writeUTF("Everybody wants to play again! Let's do it");
		  
		   outputToClient1.writeBoolean(playAgain);
		   outputToClient2.writeBoolean(playAgain);
		   outputToClient3.writeBoolean(playAgain);
		   
		   }
		   
		   if(playAgain == false){
			   outputToClient1.writeUTF("A player selected no to play again, ending game");
			   outputToClient2.writeUTF("A player selected no to play again, ending game");
			   outputToClient3.writeUTF("A player selected no to play again, ending game");
			  
			   outputToClient1.writeBoolean(playAgain);
			   outputToClient2.writeBoolean(playAgain);
			   outputToClient3.writeBoolean(playAgain);
			   
			   }
			   
			}while(playAgain == true);
			
			System.exit(0);

}catch(IOException ex) {
		
	
	}
	
	
}
}