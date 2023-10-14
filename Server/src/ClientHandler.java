import number_converter.*;
import java.net.*;
import java.io.*;

class ClientHandler implements Runnable 
{
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

            while (true) {
                String inputNumber = dis.readUTF().trim();

                if (inputNumber == "exit") {
                    break;
                }

                try {
                    dos.writeUTF(NumberToWordsConverter.convert(inputNumber, Language.VI));
                    dos.writeUTF(NumberToWordsConverter.convert(inputNumber, Language.EN));
                } catch (IllegalArgumentException excp) {
                    dos.writeUTF("Error: " + excp.getMessage());
                }

                dos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}