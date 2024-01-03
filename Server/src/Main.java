import java.net.*;
import java.io.*;

public class Main 
{
    public static void main(String[] args) throws IOException 
    {
    	try (ServerSocket server = new ServerSocket(7001)) 
		{
			System.out.println("Server is listening for requests");

			while (true) {
			    Socket socket = server.accept();
			    System.out.println("Client connected: " + socket.getInetAddress());

			    Thread clientThread = new Thread(new RequestHandler(socket));
			    clientThread.start();
			}
		}
    }
}
