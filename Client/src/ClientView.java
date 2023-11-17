import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientView extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton sendButton;
    private ClientController controller;
    private boolean isRequestEnabled = false;

    public ClientView() {
        super("Client Application");

        inputField = new JTextField(20);
        outputArea = new JTextArea(10, 40);
        sendButton = new JButton("Send");

        outputArea.setEditable(false);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToServer();
            }
        });

        setLayout(new BorderLayout());
        add(inputField, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void displayMessage(String message) {
        outputArea.append(message + '\n');
    }

    public String getClientInput() {
        return inputField.getText();
    }

    private void sendMessageToServer() {
        if (controller != null) {
            String message = inputField.getText();

            if (message.isEmpty()) {
                isRequestEnabled = false;
            }
            else {
                isRequestEnabled = true;
            }

            if (isRequestEnabled) {
                inputField.setText("");
                controller.sendMessageToServer(message);  
            } 
        }
    }
}
