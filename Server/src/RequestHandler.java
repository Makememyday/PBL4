import number_converter.*;
import java.net.*;

import base_converter.BaseConverter;

import java.io.*;

class RequestHandler implements Runnable 
{
    private Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

            while (true) {
                int mode = Integer.parseInt(dis.readUTF());
                
                String inputNumber = dis.readUTF().trim();

                String response = "";
                if (mode == 1) {
                    String language = dis.readUTF();
                    
                    try {
                         if (language.equalsIgnoreCase("VI")) {
                            response = NumberToWordsConverter.convert(inputNumber, Language.VI);
                        } else if (language.equalsIgnoreCase("EN")) {
                            response = NumberToWordsConverter.convert(inputNumber, Language.EN);
                        }
                    } catch (IllegalArgumentException excep) {
                        response = "Error: " + excep.getMessage();
                    } catch (Exception excep) {
                        response = "Error: Something went wrong at server";
                    }

                } else if (mode == 2) {
                    int fromBase = Integer.parseInt(dis.readUTF());
                    int toBase = Integer.parseInt(dis.readUTF());

                    try {
                        response = BaseConverter.convertBase(inputNumber, fromBase, toBase);
                    } catch (NumberFormatException excep) {
                        response = "Error: Invalid input number";
                    } catch (IllegalArgumentException excep) {
                        response = "Error: " + excep.getMessage();
                    } catch (Exception excep) {
                        response = "Error: Something went wrong at server";
                    }
                }

                dos.writeUTF(response);
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