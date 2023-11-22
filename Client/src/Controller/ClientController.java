package Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import Model.ClientModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ClientController {
    private ClientModel clientModel;
    private boolean isAutoConvert = true;
    private String languageSelect;

    @FXML
    private HBox numberToWordFunctionalBox;
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

    public ClientController() {
        clientModel = new ClientModel();
    }

    //Function to set the language list for choose language box 
    public void setLanguageList() {
        ChooseLanguageBox.getItems().addAll("VI", "EN");
        ChooseLanguageBox.getSelectionModel().selectFirst();
    }

    //Function to handle when user click on a functional box
    public void functionalSelectionHandle(MouseEvent event) {
        HBox source = (HBox) event.getSource();
        String functionalSelected = "";
        ObservableList<Node> children = source.getChildren();
        for (Node node : children) {
            if (node instanceof Label) {
                functionalSelected = ((Label) node).getText();
                break;
            }
        }
        FunctionalNameLabel.setText(functionalSelected);
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

    //Function to handle when user entering input and auto convert is on
    public void userInputHandle(KeyEvent event) {
        if (isAutoConvert) {
            String input = Input.getText();
            if (input.isEmpty()) {
                Output.setText("");
            } else {
                //Add language information to input
                if (languageSelect.equals("VI")) {
                    input += " VI";
                } else if (languageSelect.equals("EN")){
                    input += " EN";
                }
                clientModel.sendMessageToServer(input);
                String output = clientModel.receiveMessageFromServer();
                Output.setText(output);
            }
        }
    }

    //Function to handle when user click on convert button
    public void convertButtonHandle(MouseEvent event) {
        String input = Input.getText();
        if (input.isEmpty()) {
            Output.setText("");
        } else {
            //check if string language selected is Vietnamese, plus a VI, if English, plus a EN to input
            if (languageSelect.equals("VI")) {
                input += " VI";
            } else if (languageSelect.equals("EN")){
                input += " EN";
            }
            clientModel.sendMessageToServer(input);
            String output = clientModel.receiveMessageFromServer();
            Output.setText(output);
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

}