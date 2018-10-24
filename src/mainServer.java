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

}catch(IOException ex) {
		
	
	}
	
	
}
}