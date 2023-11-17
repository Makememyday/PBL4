public class ClientMain {
    public static void main(String[] args) {
        ClientModel model = new ClientModel();
        ClientView view = new ClientView();
        ClientController controller = new ClientController(model);
        
        view.setController(controller);
        controller.setView(view);
                
        controller.runClient();
    }
}
