package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import Model.ClientModel;
import javafx.collections.ObservableList;

public class AllBaseConverterController {

    private ClientModel clientModel;
    private boolean isAutoConvert = true;
    private String functionalSelected;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private MainFunctionController mainFunctionController;
    private BaseConverterController baseController;

    @FXML
    private VBox MainContainer;
    @FXML
    private Label FunctionalNameLabel;
    @FXML
    private Label SampleLabel;
    @FXML
    private TextArea Input;
    @FXML
    private TextArea Output;
    @FXML
    private CheckBox AutoConvertBox;
    @FXML
    private ComboBox<String> ToBase;
    @FXML
    private ComboBox<String> FromBase;

    public AllBaseConverterController() {
        clientModel = new ClientModel();
    }

    @FXML
    public void initialize() {
        setBaseList();
    }

    public void setBaseList() {
        // Set base for ToBase and FromBase with range [2,36]
        ArrayList<String> baseList = new ArrayList<>();
        for (int i = 2; i <= 36; i++) {
            baseList.add(String.valueOf(i));
        }
        ObservableList<String> base = ToBase.getItems();
        base.addAll(baseList);
        base = FromBase.getItems();
        base.addAll(baseList);
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
            if (functionalSelected.equals("Chuyển đổi cơ số tùy chọn")) {}
            else {
                loadBaseConverterView(event);
            }
        }
        else {
            loadMainFunctionalView(event);
        }
    }

    // Function to handle when user click on a sample box
    public void sampleLabelSelectedHandle(MouseEvent event) {
        Input.setText("1234");
        Output.setText("Một nghìn hai trăm ba mươi bốn\n");
        Output.appendText("One thousand two hundreds and thiry-four\n");
    }

    // Function to set auto convert
    public void autoConvertBoxHandle(MouseEvent event) {
        isAutoConvert = AutoConvertBox.isSelected();
    }

    public void sendRequest(String inputNumber, String fromBase, String toBase) {
        System.out.println("Sending request to server: " + inputNumber + " " + fromBase + " " + toBase);
        try {
            System.out.println("Sending request to server: " + inputNumber);
            clientModel.sendMessageToServer("2");
            clientModel.sendMessageToServer(inputNumber);
            clientModel.sendMessageToServer(fromBase);
            clientModel.sendMessageToServer(toBase);
        } catch (IOException e) {
            Output.setText(e.getMessage());
        }
    }

    // Function to handle when user entering input and auto convert is on
    public void userInputHandle(KeyEvent event) {
        if (functionalSelected == null || !isAutoConvert) {
            return;
        }
        
        String input = Input.getText();
        if (input.isEmpty()) {
            Output.setText("");
        } else {
            String fromBase = FromBase.getValue();
            String toBase = ToBase.getValue();
            if (fromBase == null || toBase == null) {
                Output.setText("Vui lòng chọn cơ số");
                return;
            }
            sendRequest(input, fromBase, toBase);
            
            try {
                String output = clientModel.receiveMessageFromServer();
                Output.setText(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Function to handle when user click on convert button
    public void convertButtonHandle(MouseEvent event) {
        String input = Input.getText();
        if (input.isEmpty()) {
            Output.setText("");
        } else {
            String fromBase = FromBase.getValue();
            String toBase = ToBase.getValue();
            if (fromBase == null || toBase == null) {
                Output.setText("Please select base");
                return;
            }
            sendRequest(input, fromBase, toBase);
            
            try {
                String output = clientModel.receiveMessageFromServer();
                Output.setText(output);
            } catch (IOException e) {
                Output.setText("Lỗi: Không thể kết nối đến server");
            }
        }
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

    private void loadBaseConverterView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/BaseConverter.fxml"));
            root = loader.load();

            baseController = loader.getController();
            baseController.setFunction(functionalSelected);

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
