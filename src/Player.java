import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
		try {
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
			username = fromClient.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setTool(){
		try {
			tool = fromClient.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setPlayAgain(){
		try {
			playAgain = fromClient.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}