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
		
		
		
		
		
	}
	
	
}