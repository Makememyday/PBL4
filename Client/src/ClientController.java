public class ClientController {
    private ClientModel model;
    private ClientView view;

    public ClientController(ClientModel model) {
        this.model = model;
    }

    public void setView(ClientView view) {
        this.view = view;
    }

    public void runClient() {
        if (view != null) {
            view.displayMessage("Client is running");

            // while (true) {
            //     String message = view.getUserInput();
            //     if ("exit".equals(message)) {
            //         model.closeConnection();
            //         System.exit(0);
            //     }
            //     sendMessageToServer(message);
            // }
        }
    }

    public void sendMessageToServer(String message) {
        model.sendMessageToServer(message);
        StringBuilder response = new StringBuilder();

        response.append("Reponse from server: \n");
        String messageFromClient = model.receiveMessageFromServer();

        response.append(messageFromClient);
        if (view != null) {
            view.displayMessage(response.toString());
        }
    }
}
