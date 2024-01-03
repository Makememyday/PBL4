package Controller;

import javax.swing.JOptionPane;

import Model.ClientModel;

public class Controller {
    public void showDialogMessage(String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, "Thông báo", messageType);
    }

    public void showErrorMessage(String message) {
        showDialogMessage(message, JOptionPane.ERROR_MESSAGE);
    }

    protected ClientModel clientModel;

    public ClientModel getClientModel() {
        if (clientModel == null) {
            try {
                clientModel = new ClientModel();
            } catch (Exception e) {
                showErrorMessage("Không thể kết nối đến server");
            }
        }
        return clientModel;
    }
}
