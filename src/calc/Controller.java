package calc;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public TextField upperField;
    @FXML
    public TextField lowerField;

    private double value = 0;
    private String lastCommand = "=";

    public void btnBack() {
        if (lowerField.getLength() > 0){
            lowerField.setText(lowerField.getText(0, lowerField.getLength() - 1));
        }
    }

    public void btnSeven() {
        lowerField.appendText("7");
    }

    public void btnFour() {
        lowerField.appendText("4");
    }

    public void btnOne() {
        lowerField.appendText("1");
    }

    public void btnClearAll() {
        upperField.clear();
        lowerField.clear();
        value = 0;
    }

    public void btnEight() {
        lowerField.appendText("8");
    }

    public void btnFive() {
        lowerField.appendText("5");
    }

    public void btnTwo() {
        lowerField.appendText("2");
    }

    public void btnClear() {
        lowerField.clear();
        if (upperField.getLength() == 0){
            btnClearAll();
        }
    }

    public void btnNine() {
        lowerField.appendText("9");
    }

    public void btnSix() {
        lowerField.appendText("6");
    }

    public void btnThree() {
        lowerField.appendText("3");
    }

    public void btnInverse() {
        if (lowerField.getLength() > 0){
            if (lowerField.getText(0,1).equals("-")){
                lowerField.deleteText(0, 1);
            } else{
                lowerField.insertText(0, "-");
            }
        }

    }

    public void btnDivision() {
        action();
        if (lowerField.getLength() > 0) {
            upperField.appendText(" / ");
            lowerField.clear();
        }
        lastCommand = "/";
    }

    public void btnMultiple() {
        action();
        if (lowerField.getLength() > 0) {
            upperField.appendText(" * ");
            lowerField.clear();
        }
        lastCommand = "*";
    }

    public void btnMinus() {
        action();
        if (lowerField.getLength() > 0) {
            upperField.appendText(" - ");
            lowerField.clear();
        }
        lastCommand = "-";
    }

    public void btnZero() {
        if (!lowerField.getText().equals("0")){
            lowerField.appendText("0");
        }
    }

    public void btnDecimal() {
        String temp = lowerField.getText();
        if (!temp.contains(".") && lowerField.getLength() > 0){
            lowerField.appendText(".");
        }
    }

    public void btnPlus() {
        action();
        if (lowerField.getLength() > 0) {
            upperField.appendText(" + ");
            lowerField.clear();
        }
        lastCommand = "+";
    }

    public void btnResult() {
        action();
        lowerField.setText("" + value);
        upperField.clear();
        lastCommand = "=";
    }

    public void action(){
        if (lowerField.getLength() > 0){
            if (lastCommand.equals("+")){
                value += Double.parseDouble(lowerField.getText());
            }
            if (lastCommand.equals("-")){
                value -= Double.parseDouble(lowerField.getText());
            }
            if (lastCommand.equals("*")){
                value *= Double.parseDouble(lowerField.getText());
            }
            if (lastCommand.equals("/")){
                value /= Double.parseDouble(lowerField.getText());
            }
            if (lastCommand.equals("=")){
                value = Double.parseDouble(lowerField.getText());
                upperField.setText(lowerField.getText());
            }

            upperField.setText(value + "");
            lowerField.clear();
        }

    }
}