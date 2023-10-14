import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
			Socket socket = new Socket("localhost", 7001);
			
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			
			Scanner scan = new Scanner(System.in);
			
			System.out.println("Client is running");

			while(true) {
				System.out.print("Client: ");

				String msg = scan.nextLine();
				if (msg == "exit") break;

				dos.writeUTF(msg);
				dos.flush();

				System.out.println("Result:");
				
				// System.out.println('\t' + dis.readUTF());
				// System.out.println('\t' + dis.readUTF());

				String response = "";
				do {
					response = dis.readUTF(); // sẽ chờ cho đến khi dis có nội dung
											  // cx là lúc server đã phản hồi lại
					System.out.println("\t" + response);
				} while (dis.available() > 0);
				
			}
			socket.close();
			scan.close();
	}
}
