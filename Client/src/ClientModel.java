import java.io.*;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientModel() {
        try {
            socket = new Socket("localhost", 7001);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToServer(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessageFromServer() {
        try {
            StringBuilder messageFromServer = new StringBuilder();
            while (true) {
                String message = "";
                message = dataInputStream.readUTF();
                if (message.equals("%END%"))
                    break;
                messageFromServer.append(message);
            }
            
            return messageFromServer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void closeConnection() {
        try {
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
