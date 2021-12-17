package com.calc.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CalcController {

@FXML
private Label textLabel;

    private final List<String> textOutput = Arrays.asList("Infinity", "NaN", "Error: division by 0", "Ready");
    private final List<String> operations = Arrays.asList("+", "*", "/", "-");
    private boolean result = false; //"true" se il testo sulla label è un risultato, "false" altrimenti


    public void printButtonValue(ActionEvent actionEvent) {

        /* Se: 1. Il pulsante scelto non corrisponde a un'operazione
        *                           OPPURE
        *      2. Il pulsante corrisponde a un'operazione, ma non è l'inizio di una nuova espressione ("/3" non avrebbe
        *         senso come espressione)
        *                             E
        *      3. Il pulsante corrisponde al ".", e non ne è stato inserito uno già nel carattere precedente ("12...3"
        *         non avrebbe senso, se ne può mettere solo uno).
        *                             E
        *      4. Il pulsante corrisponde a un'operazione e l'ultimo carattere non è già un'operazione
        *
        *  Allora aggiunge al testo sulla label quello corrispondente al pulsante premuto
        * */
        if((!isAnOperation(actionEvent) || (isAnOperation(actionEvent) && !operationWhileTExtOnLabel(actionEvent))) && pointChecker(actionEvent) && multipleOperChecker(actionEvent))
            textLabel.setText(this.oldText(textLabel.getText(), actionEvent) + ((Button) actionEvent.getSource()).getText());

        this.result = false; //Aggiorna la variabile che tiene traccia del fatto che ciò che è stampato sulla label
                             // è un risultato oppure no


    }

    private boolean multipleOperChecker(ActionEvent actionEvent) { //Controlla se si stanno immettendo due operatori
                                                                   //consecutivi
        return !(lastCharIsAnOperation() && operations.contains(((Button)actionEvent.getSource()).getText()));
    }

    private boolean pointChecker(ActionEvent actionEvent) { //Controlla se si sta inserendo un punto dopo un altro punto
                                                            //(vedi "3." nel commento a printButtonValue() )
        char lastChar = textLabel.getText().charAt(textLabel.getText().length() -1);
        return lastChar != '.' || !Objects.equals(((Button) actionEvent.getSource()).getText(), ".");


    }

    private boolean operationWhileTExtOnLabel(ActionEvent actionEvent) { //controlla se si vuole inserire un'operazione
                                                                        //come primo carattere dell'espressione
       return textOutput.contains(textLabel.getText()) && isAnOperation(actionEvent);
    }

    private String oldText(String s, ActionEvent ae) { //valuta se fare un "append" al testo che c'è sulla label
                                                       // attualmente o se sovrascriverlo
        if (textOutput.contains(s) || isAResult() && !isAnOperation(ae)){return "";}
        else {return s;}
    }

    private boolean isAnOperation(ActionEvent ae) { //controlla se il tasto premuto corrisponde a un operatore
        return operations.contains(((Button)ae.getSource()).getText());
    }

    private boolean isAResult() {
        return result;
    }

    public void eraseAll() { //Reimposta la label
        textLabel.setText("Ready");
    }

    public void computeCalculation() { //Richiede al solver di computare l'espressione che si trova attualmente sulla
                                       //label
        /* Se: 1. La label contiene del testo (es.: "Error: division by 0")
        *      2. La stringa sulla label attualmente termina con un operatore (es: "21*")
        *
        *  Allora il risolutore non viene chiamato perchè l'input non è completo ancora
        *  */
        if (!textOutput.contains(textLabel.getText()) && !lastCharIsAnOperation()) {
            Solver solver = Solver.getGetSolverInstance();
            textLabel.setText(solver.solve(textLabel.getText()));
            this.result = true;
        }
    }

    private boolean lastCharIsAnOperation() { // true se l'ultimo carattere della label è un operatore (+,-,/,*)
        /* Il cast da char a String viene eseguito perchè l'Array operations<String> contiene stringhe e non char*/
        return operations.contains(Character.toString((textLabel.getText().charAt(textLabel.getText().length() -1))));
    }
}