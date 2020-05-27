package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Controller {
    @FXML
    public TextField printText;
    @FXML
    public TextArea chat;

    public void sendButton() {
        if (!isEmpty()) {
            send();
        }
    }

    public void textFieldClicked(MouseEvent mouseEvent) {
        if (printText.getText().equals("Введите текст...")) {
            printText.clear();
        }
    }


    public void pressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER) && !isEmpty()) {
            send();
        }
    }

    private boolean isEmpty(){
        if (printText.getText().equals("")){
            return true;
        }
        return false;
    }

    private void send(){
        chat.appendText(printText.getText() + "\n");
        printText.clear();
    }

}
