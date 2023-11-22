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
                    //If inputNumber contains "VI", convert to Vietnamese, else convert to English
                    if (inputNumber.contains("VI")) {
                        inputNumber = inputNumber.substring(0, inputNumber.length() - 3);
                        dos.writeUTF("Bằng Tiếng Việt: " + NumberToWordsConverter.convert(inputNumber, Language.VI) + '\n');
                    } else if (inputNumber.contains("EN")) {
                        inputNumber = inputNumber.substring(0, inputNumber.length() - 3);
                        dos.writeUTF("By English: " + NumberToWordsConverter.convert(inputNumber, Language.EN) + '\n');
                    } 
                    dos.writeUTF("%END%");
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