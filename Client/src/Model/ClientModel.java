package Model;
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

    public void sendMessageToServer(String message) throws IOException {
        if (dataOutputStream != null)
            dataOutputStream.writeUTF(message);
        else 
            throw new IOException("Không thể gửi tin nhắn đến server");
    }

    public void flushMessage() {
        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessageFromServer() throws IOException {
        return dataInputStream.readUTF();
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
