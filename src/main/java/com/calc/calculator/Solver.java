package com.calc.calculator;

import com.fathzer.soft.javaluator.DoubleEvaluator;


public class Solver {

    private static Solver solverInstance = null;

    public static Solver getGetSolverInstance() {
        if (solverInstance == null) {Solver.solverInstance = new Solver();}
        return Solver.solverInstance;
    }

    private Solver() {}

    public String solve(String expression){

        DoubleEvaluator de = new DoubleEvaluator();
        double result = de.evaluate(expression);

        return this.refactor(Double.toString(result));
    }

    private String refactor(String stringResult) {
        int lenOfString = stringResult.length();
        char c1 = stringResult.charAt(lenOfString-1);
        char c2 = stringResult.charAt(lenOfString-2);

        if (c1 == '0' && c2 == '.'){
            return stringResult.substring(0, stringResult.length() - 2); //il risolutore ritorna sempre un numero con la virgola, anche se è un intero
            //Perciò si rimuovono il ".0" se è un numero intero.
            //es.: 12.0 -> 12

        }

        else if (stringResult.equals("Infinity"))
        { //Il risolutore fornisce "Infinity" come risultato per la divisione per ZERO. Ritengo sia più
            return("Error: division by 0"); //corretto mostrare un errore
        }

        else {
            return(String.valueOf(stringResult));
        }
    }


}
