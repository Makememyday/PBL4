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
import Model.ClientModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class MainFunctionController {
    private ClientModel clientModel;
    private boolean isAutoConvert = true;
    private String languageSelect;
    private String functionalSelected;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private AllBaseConverterController allBaseController;
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
    private ComboBox<String> ChooseLanguageBox;

    public MainFunctionController() {
        clientModel = new ClientModel();
    }

    @FXML
    public void initialize() {
        setLanguageList();
        languageSelect = ChooseLanguageBox.getSelectionModel().getSelectedItem().toString();
    }

      //Function to set the language list for choose language box 
    public void setLanguageList() {
        ChooseLanguageBox.getItems().addAll("VI", "EN");
        ChooseLanguageBox.getSelectionModel().selectFirst();
    }

    //Function to handle when user click on a functional box
    public void functionalSelectionHandle(MouseEvent event) {
        HBox source = (HBox) event.getSource();
        functionalSelected = "";
        ObservableList<Node> children = source.getChildren();
        for (Node node : children) {
            if (node instanceof Label) {
                functionalSelected = ((Label) node).getText();
                break;
            }
        }
        FunctionalNameLabel.setText(functionalSelected);

        if (!functionalSelected.equals("Chuyển số thành chữ")) {
            if (functionalSelected.equals("Chuyển đổi cơ số tùy chọn")) {
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
            else {
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
        }
    }

    //Function to handle when user click on a sample box
    public void sampleLabelSelectedHandle(MouseEvent event) {
        Input.setText("1234");
        Output.setText("Một nghìn hai trăm ba mươi bốn\n");
        Output.appendText("One thousand two hundreds and thiry-four\n");
    }

    //Function to set auto convert
    public void autoConvertBoxHandle(MouseEvent event) {
        isAutoConvert = AutoConvertBox.isSelected();
    }

    public void sendRequest(String inputNumber, String language) {
        System.out.println("Sending request to server: " + inputNumber + " " + language);
        clientModel.sendMessageToServer("1");
        clientModel.sendMessageToServer(inputNumber);
        clientModel.sendMessageToServer(language);
    }

    //Function to handle when user entering input and auto convert is on
    public void userInputHandle(KeyEvent event) {
        if (functionalSelected == null) {
            return;
        }
        if (isAutoConvert) {
            String input = Input.getText();
            if (input.isEmpty()) {
                Output.setText("");
            } 
            else if (!input.matches("[0-9]+")) {
                Output.setText("Lỗi: Vui lòng nhập số nguyên dương");
            }
            else {
                sendRequest(input, languageSelect);
                clientModel.flushMessage();
                try {
                    String output = clientModel.receiveMessageFromServer();
                    Output.setText(output);
                } catch (IOException e) {
                    Output.setText("Lỗi: Không thể kết nối đến server");
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String modeSwitch (String functional) {
        String mode = "";
        switch (functional) {
            case "Chuyển số thành chữ":
                mode = "MODE1";
                break;
            default:
                mode = "MODE1";
                break;
        }
        return mode;
    }

    //Function to handle when user click on convert button
    public void convertButtonHandle(MouseEvent event) {
        String input = Input.getText();
        if (input.isEmpty()) {
            Output.setText("");
        } 
        else if (!input.matches("[0-9]+")) {
                Output.setText("Lỗi: Vui lòng nhập số");
        }
        else {
            sendRequest(input, languageSelect);

            try {
                String output = clientModel.receiveMessageFromServer();
                Output.setText(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Function to handle when user click on bin/clear icon
    public void clearButtonHandle(MouseEvent event) {
        Input.setText("");
        Output.setText("");
    }

    //Function to set the language user selected to convert
    public void chooseLanguageBoxHandle(ActionEvent event) {
        languageSelect = ChooseLanguageBox.getSelectionModel().getSelectedItem().toString();
    }

    //Function doSomething to print choose language box value
    public void doSomething(MouseEvent event) {
        //Set language for chooseLanguageBox
        //If chooseLanguageBox is null, set them
        if (ChooseLanguageBox.getItems().isEmpty()) {
            setLanguageList();
        }
    }

    public void setFunction(String function) {
        functionalSelected = function;
        FunctionalNameLabel.setText(functionalSelected);
    }
}