package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import Model.ClientModel;

public class BaseConverterController extends Controller {
    private boolean isAutoConvert = true;
    private String functionalSelected;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String fromBase;
    private String toBase;

    private MainFunctionController mainFunctionController;
    private AllBaseConverterController allBaseController;

    @FXML
    private VBox MainContainer;
    @FXML
    private Label FunctionalNameLabel;
    @FXML
    private TextArea Input;
    @FXML
    private TextArea Output;
    @FXML
    private CheckBox AutoConvertBox;

    public BaseConverterController() {
    }

    @FXML
    public void initialize() {
    }

    // Function to handle when user click on a functional box
    public void functionalSelectionHandle(MouseEvent event) {
        HBox source = (HBox) event.getSource();
        functionalSelected = source.getChildren()
                .stream()
                .filter(node -> node instanceof Label)
                .map(node -> ((Label) node).getText())
                .findFirst()
                .orElse("");
        FunctionalNameLabel.setText(functionalSelected);

        if (!functionalSelected.equals("Chuyển số thành chữ")) {
            if (functionalSelected.equals("Chuyển đổi cơ số tùy chọn")) {
                loadAllBaseConverterView(event);
            }
        } else {
            loadMainFunctionalView(event);
        }
    }

    // Function to handle when user click on a sample box
    public void sampleLabelSelectedHandle(MouseEvent event) {
         //Sample for each mode
        switch (functionalSelected) {
            case "Thập phân sang nhị phân":
                Input.setText("1234");
                Output.setText("10011010010");
                break;
            case "Thập phân sang bát phân":
                Input.setText("1234");
                Output.setText("2322");
                break;
            case "Thập phân sang thập lục phân":
                Input.setText("1234");
                Output.setText("4D2");
                break;
            case "Nhị phân sang thập phân":
                Input.setText("10011010010");
                Output.setText("1234");
                break;
            case "Bát phân sang thập phân":
                Input.setText("2322");
                Output.setText("1234");
                break;
            case "Thập lục phân sang thập phân":
                Input.setText("4D2");
                Output.setText("1234");
                break;
            default:
                break;
        }
    }

    // Function to set auto convert
    public void autoConvertBoxHandle(MouseEvent event) {
        isAutoConvert = AutoConvertBox.isSelected();
    }

    // Function to handle when user entering input and auto convert is on
    public void userInputHandle(KeyEvent event) {
        if (functionalSelected == null || !isAutoConvert) {
            return;
        }

        String input = Input.getText();
        if (input.isEmpty()) {
            Output.setText("");
            return;
        }

        if (getClientModel() == null) return;

        String functional = modeSwitch(functionalSelected);
        if (isValidInput(functional, input)) {
            setBase(functional);
            sendRequest(input, fromBase, toBase);
            getClientModel().flushMessage();
            try {
                String output = getClientModel().receiveMessageFromServer();
                Output.setText(output);
            } catch (IOException e) {
                showErrorMessage("Error: " + e.getMessage());
            }
        } else {
            Output.setText("Lỗi: Vui lòng nhập chuẩn định dạng");
        }
    }

    public String modeSwitch(String functional) {
        String mode = "";
        switch (functional) {
            case "Thập phân sang nhị phân":
                mode = "MODE2";
                break;
            case "Thập phân sang bát phân":
                mode = "MODE3";
                break;
            case "Thập phân sang thập lục phân":
                mode = "MODE4";
                break;
            case "Nhị phân sang thập phân":
                mode = "MODE5";
                break;
            case "Bát phân sang thập phân":
                mode = "MODE6";
                break;
            case "Thập lục phân sang thập phân":
                mode = "MODE7";
                break;
            default:
                mode = "MODE2";
                break;
        }
        return mode;
    }

    public void sendRequest(String inputNumber, String fromBase, String toBase) {
        try {
            System.out.println("Sending request to server: " + inputNumber);
            getClientModel().sendMessageToServer("2");
            getClientModel().sendMessageToServer(inputNumber);
            getClientModel().sendMessageToServer(fromBase);
            getClientModel().sendMessageToServer(toBase);
        } catch (IOException e) {
            showErrorMessage("Error: " + e.getMessage());
        }
    }

    public void renderRecievedMessageFromServer() {
        try {
            String output = getClientModel().receiveMessageFromServer();
            Output.setText(output);
        } catch (IOException e) {
            showErrorMessage("Lỗi: Không thể kết nối đến server");
            e.printStackTrace();
        }
    }

    // Function Set fromBase and toBase from mode
    public void setBase(String mode) {
        switch (mode) {
            case "MODE2":
                fromBase = "10";
                toBase = "2";
                break;
            case "MODE3":
                fromBase = "10";
                toBase = "8";
                break;
            case "MODE4":
                fromBase = "10";
                toBase = "16";
                break;
            case "MODE5":
                fromBase = "2";
                toBase = "10";
                break;
            case "MODE6":
                fromBase = "8";
                toBase = "10";
                break;
            case "MODE7":
                fromBase = "16";
                toBase = "10";
                break;
            default:
                fromBase = "10";
                toBase = "2";
                break;
        }
    }

    // Function to handle when user click on convert button
    public void convertButtonHandle(MouseEvent event) {
        String input = Input.getText();
        if (input.isEmpty()) {
            Output.setText("");
            return;
        }

        if (getClientModel() == null) return;

        String functional = modeSwitch(functionalSelected);
        if (isValidInput(functional, input)) {
            setBase(functional);
            sendRequest(input, fromBase, toBase);
            getClientModel().flushMessage();

            renderRecievedMessageFromServer();
        } else {
            Output.setText("Lỗi: Vui lòng nhập chuẩn định dạng");
        }
    }

    public boolean isValidInput(String mode, String input) {
        boolean isValid = true;
        switch (mode) {
            case "MODE2":
            case "MODE3":
            case "MODE4":
                if (!input.matches("[0-9]+")) {
                    isValid = false;
                }
                break;
            case "MODE5":
                // bin to dec
                if (!input.matches("[0-1]+")) {
                    isValid = false;
                }
                break;
            case "MODE6":
                if (!input.matches("[0-7]+")) {
                    isValid = false;
                }
                break;
            case "MODE7":
                if (!input.matches("[0-9A-Fa-f]+")) {
                    isValid = false;
                }
                break;
            default:
                isValid = false;
                break;
        }
        return isValid;
    }

    // Function to handle when user click on bin/clear icon
    public void clearButtonHandle(MouseEvent event) {
        Input.setText("");
        Output.setText("");
    }

    public void setFunction(String function) {
        functionalSelected = function;
        FunctionalNameLabel.setText(functionalSelected);
    }

    private void loadAllBaseConverterView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AllBaseConverter.fxml"));
            root = loader.load();

            allBaseController = loader.getController();
            allBaseController.setFunction(functionalSelected);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMainFunctionalView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/MainFunctionalGUI.fxml"));
            root = loader.load();
            mainFunctionController = loader.getController();
            mainFunctionController.setFunction(functionalSelected);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
