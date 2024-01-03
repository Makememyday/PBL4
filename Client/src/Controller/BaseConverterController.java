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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import FXML.FunctionModel;
import Model.ClientModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class BaseConverterController {
    private ClientModel clientModel;
    private boolean isAutoConvert = true;
    private String languageSelect;
    private FunctionModel selectedFunction;
    private String functionalSelected;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private MainFunctionController mainFunctionController;
    private AllBaseConverterController allBaseController;

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

    public BaseConverterController() {
        clientModel = new ClientModel();
    }

    @FXML
    public void initialize() {
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
            else {}
        }
        else {
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
                if (languageSelect == null) {
                    languageSelect = "VI";
                }
                //Add language information to input
                if (languageSelect.equals("VI")) {
                    input += " VI";
                } else if (languageSelect.equals("EN")){
                    input += " EN";
                }

                System.out.println(functionalSelected);
                //input += " " + functionalSelected;

                clientModel.sendMessageToServer(input);
                String output = clientModel.receiveMessageFromServer();
                Output.setText(output);
            }
        }
    }

    public String modeSwitch (String functional) {
        String mode = "";
        switch (functional) {
            case "Chức năng 2":
                mode = "MODE2";
                break;
            case "Chức năng 3":
                mode = "MODE3";
                break;
            case "Chức năng 4":
                mode = "MODE4";
                break;
            case "Chức năng 5":
                mode = "MODE5";
                break;
            case "Chức năng 6":
                mode = "MODE6";
                break;
            case "Chức năng 7":
                mode = "MODE7";
                break;
            default:
                mode = "MODE2";
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
        else {
            //check if string language selected is Vietnamese, plus a VI, if English, plus a EN to input
            if (languageSelect == null) {
                languageSelect = "VI";
            }
            if (languageSelect.equals("VI")) {
                input += " VI";
            } else if (languageSelect.equals("EN")){
                input += " EN";
            }

            String functional = modeSwitch(functionalSelected);
            //input += " " + functional;
            
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

    public void setFunction(String function) {
        functionalSelected = function;
        FunctionalNameLabel.setText(functionalSelected);
    }
}