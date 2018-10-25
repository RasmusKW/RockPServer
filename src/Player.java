import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
// a separate Player class 
public class Player {
	
	private Socket socket;	
	private String wantToStartGame;
	
	public String username;
	public int tool;
	public DataInputStream fromClient;
	public DataOutputStream toClient;
	public String playAgain;
	
	
	void setSocket(ServerSocket serverSocket){
		try {
			socket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Socket getSocket(){
		return socket;
	}
	
	void setDataStreams(){
		try {
			fromClient = new DataInputStream(socket.getInputStream());
			toClient = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void askWantToStartGame(){
		
		//using a try/catch exception handling
		try {
			//receives the players' answers on whether they want to start a game
			 wantToStartGame = fromClient.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String getWantToStartGame(){
		return wantToStartGame;
	}
	
	void setUsername(){
		try {
			//reads what the users set as their username
			username = fromClient.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setTool(){
		try {
			//reads what tools each client chooses
			tool = fromClient.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setPlayAgain(){
		try {
			//reads users' choice on whether they want to continue playing the game
			playAgain = fromClient.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}