package Model;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientModel() throws IOException {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("192.168.248.25", 7001), 1000);
            
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new IOException("Không thể kết nối đến server", e);
        }
    }

    public void sendMessageToServer(String message) throws IOException {
        dataOutputStream.writeUTF(message);
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
