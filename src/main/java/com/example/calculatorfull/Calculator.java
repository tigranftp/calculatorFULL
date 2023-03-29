package com.example.calculatorfull;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Calculator extends Application {
    private enum Operation {
        ADD, SUB, MUL, DIV, EQ
    }

    private Double curDig;
    Operation curOperation = Operation.EQ;

    private Button longButton(String nameBtn) {
        Button btn = new Button(nameBtn);
        btn.setStyle(
                "-fx-background-radius: 10em; " +
                        "-fx-min-width: 135px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 135px; " +
                        "-fx-max-height: 50px;"
        );
        btn.setMinWidth(60);
        btn.setFont(new Font(20));
        return btn;
    }

    private Button shortButton(String nameBtn) {
        Button btn = new Button(nameBtn);
        btn.setStyle(
                "-fx-background-radius: 10em; " +
                        "-fx-min-width: 60px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 60px; " +
                        "-fx-max-height: 50px;"
        );
        btn.setFont(new Font(20));
        return btn;
    }

    private void setDigitAction(Button btn, TextField text, String dig) {
        btn.setOnAction(event -> {
            String curText = text.getText();
            if (curText.startsWith("0") && curText.length() == 1) {
                text.setText(dig);
                return;
            }

            text.setText(curText + dig);
        });
    }

    private void setOperationAction(Button btn, TextField text, Operation operation) {
        btn.setOnAction(event -> {
            if (text.getText().isEmpty()) {
                return;
            }
            if (curOperation != Operation.EQ) {
                equalAction(text);
            }

            curDig = Double.parseDouble(text.getText());
            curOperation = operation;
            text.setText("");
        });
    }

    private void equalAction(TextField text) {

        if (text.getText().isEmpty()) {
            return;
        }

        double result = 0.0;

        double secondDig = Double.parseDouble(text.getText());
        if (curOperation == Operation.DIV && secondDig == 0.0) {
            text.setText("");
            curDig=0.0;
            curOperation = Operation.EQ;
        }

        switch (curOperation) {
            case SUB -> result = curDig - secondDig;
            case DIV -> result = curDig / secondDig;
            case MUL -> result = curDig * secondDig;
            case ADD -> result = curDig + secondDig;
            case EQ -> {
                return;
            }
        }

        curOperation = Operation.EQ;
        curDig = result;

        if (curDig == Math.round(curDig)) {
            text.setText(Math.round(curDig) + "");
            return;
        }

        text.setText(curDig + "");
    }

    @Override
    public void start(Stage stage) throws IOException {
        int marg = 12;
        HBox row1 = new HBox(marg);
        row1.setAlignment(Pos.CENTER);
        Button button1 = shortButton("1");
        Button button2 = shortButton("2");
        Button button3 = shortButton("3");
        Button buttonDiv = shortButton("/");
        row1.getChildren().addAll(button1, button2, button3, buttonDiv);

        HBox row2 = new HBox(marg);
        row2.setAlignment(Pos.CENTER);
        Button button4 = shortButton("4");
        Button button5 = shortButton("5");
        Button button6 = shortButton("6");
        Button buttonMul = shortButton("*");
        row2.getChildren().addAll(button4, button5, button6, buttonMul);

        HBox row3 = new HBox(marg);
        row3.setAlignment(Pos.CENTER);
        Button button7 = shortButton("7");
        Button button8 = shortButton("8");
        Button button9 = shortButton("9");
        Button buttonMinus = shortButton("-");
        row3.getChildren().addAll(button7, button8, button9, buttonMinus);

        HBox row4 = new HBox(marg);
        row4.setAlignment(Pos.CENTER);
        Button button0 = shortButton("0");
        Button buttonEq = shortButton("=");
        Button buttonPlus = shortButton("+");
        Button buttonDelete = shortButton("Del");
        row4.getChildren().addAll(button0, buttonEq, buttonPlus, buttonDelete);

        TextField text = new TextField();
        text.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (!".0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
                if ((Objects.equals(keyEvent.getCharacter(), ".")) && (text.getText().contains(".") || Objects.equals(text.getText(), ""))) {
                    System.out.print(keyEvent.getCharacter());
                    keyEvent.consume();
                }
                if ((Objects.equals(keyEvent.getCharacter(), "0")) && (Objects.equals(text.getText(), "0"))) {
                    System.out.print(keyEvent.getCharacter());
                    keyEvent.consume();
                }
                if (Objects.equals(keyEvent.getCharacter(), "*"))
                {
                    buttonMul.fire();
                }
                if (Objects.equals(keyEvent.getCharacter(), "+"))
                {
                    buttonPlus.fire();
                }
                if (Objects.equals(keyEvent.getCharacter(), "-"))
                {
                    buttonMinus.fire();
                }
                if (Objects.equals(keyEvent.getCharacter(), "/"))
                {
                    buttonDiv.fire();
                }
                if (Objects.equals(keyEvent.getCharacter(), "="))
                {
                    buttonEq.fire();
                }
            }
        });
        text.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        text.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        text.setMinWidth(button1.getMinWidth() * 4 + marg * 3);
        text.setMinHeight(65);
        text.setFont(new Font(23));
        text.setAlignment(Pos.CENTER_RIGHT);

        button0.setOnAction(event -> {
            if (text.getText().startsWith("0") && text.getText().length() == 1) {
                return;
            }

            text.setText(text.getText() + "0");
        });

        buttonDelete.setOnAction(event -> {
            curDig = 0.0;
            curOperation = Operation.EQ;
            text.setText("0");

        });

        setDigitAction(button1, text, "1");
        setDigitAction(button2, text, "2");
        setDigitAction(button3, text, "3");
        setDigitAction(button4, text, "4");
        setDigitAction(button5, text, "5");
        setDigitAction(button6, text, "6");
        setDigitAction(button7, text, "7");
        setDigitAction(button8, text, "8");
        setDigitAction(button9, text, "9");

        setOperationAction(buttonPlus, text, Operation.ADD);
        setOperationAction(buttonMinus, text, Operation.SUB);
        setOperationAction(buttonMul, text, Operation.MUL);
        setOperationAction(buttonDiv, text, Operation.DIV);

        buttonEq.setOnAction(event -> {
            equalAction(text);
        });

        VBox root = new VBox(marg);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(text, row1, row2, row3, row4);
        Scene scene = new Scene(root, 300, 380, Color.KHAKI);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        scene.getWidth();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}