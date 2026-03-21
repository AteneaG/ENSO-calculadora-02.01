/**
 * @name        Calculator Controller class
 * @package     calculator
 * @file        Controller.java
 * @description Controller class that implements EventHandler interface to call suitable
 * model methods, and updates the View accordingly.
 */


package calculator;

import calculator.domain.BinaryOperatorModes;
import calculator.domain.UnaryOperatorModes;

public class Controller implements EventHandler {
    
    private final CalculatorModel model;
    private final View view;
    private StringBuilder displayBuffer;
    private boolean resetingInput = false;

    public Controller(CalculatorModel model, View view) {
        this.model = model;
        this.view = view;
        this.displayBuffer = new StringBuilder();
        view.setActionListener(this);
    }
    
    @Override
    public void onNumberPressed(int number) {

        // After a user presses equals and gets a result, 
        // the next number press should start a new input
        if (resetingInput) {
            displayBuffer = new StringBuilder();
            view.clearDisplay();
            resetingInput = false;
        }

        displayBuffer.append(number);
        view.setDisplay(displayBuffer.toString());
    }
    
    @Override
    public void onDecimalPressed() {

        // After a user presses equals and gets a result, 
        // the next decimal press should start a new input
        if (resetingInput) {
            displayBuffer = new StringBuilder();
            view.clearDisplay();
            resetingInput = false;
        }
       
        // Prevent multiple decimal points in the current number
        if (!displayBuffer.toString().contains(",")) {
            // Handle leading decimal point by prepending a "0"
            if (displayBuffer.length() == 0) {
                displayBuffer.append("0");
            }
            displayBuffer.append(",");
            view.setDisplay(displayBuffer.toString());
        }
    }

    @Override
    public void onPiPressed() {
        setConstant(Math.PI);
    }

    @Override
    public void onEPressed() {
        setConstant(Math.E);
    }
    
    @Override
    public void onBinaryOperatorPressed(BinaryOperatorModes mode) {

        if (displayBuffer.length() > 0) {

            Double num = view.getDisplayValue();
            Double result = model.calculateBinary(mode, num);

            displayBuffer = new StringBuilder();
            if (result != null) {
                view.setDisplay(formatResult(result));
                displayBuffer.append(formatResult(result));
            }
            resetingInput = true;
        }
    }
    
    @Override
    public void onUnaryOperatorPressed(UnaryOperatorModes mode) {

        if (displayBuffer.length() > 0) {

            Double num = view.getDisplayValue();
            Double result = model.calculateUnary(mode, num);

            displayBuffer = new StringBuilder();
            view.setDisplay(formatResult(result));
            displayBuffer.append(formatResult(result));
            resetingInput = true;
        }
    }
    
    @Override
    public void onEqualsPressed() {

        if (displayBuffer.length() > 0) {

            Double num = view.getDisplayValue();
            Double result = model.calculateEqual(num);

            displayBuffer = new StringBuilder();
            view.setDisplay(formatResult(result));
            displayBuffer.append(formatResult(result));
            resetingInput = true;
        }
    }
    
    @Override
    public void onClearPressed() {
        displayBuffer = new StringBuilder();
        model.reset();
        view.clearDisplay();
        resetingInput = false;
    }

    private void setConstant(Double value) {
        String constantText = formatResult(value);
        displayBuffer = new StringBuilder(constantText);
        view.setDisplay(constantText);
        // After inserting a constant, number input should start a new value.
        resetingInput = true;
    }
    
    private String formatResult(Double result) {
        if (Double.isNaN(result)) {
            return "NaN";
        }
        else if (Double.isInfinite(result)) {
            if (result > 0) {
                return "Inf";
            } else {
                return "-Inf";
            }
        }
        else {
            //String formatted = String.format(java.util.Locale.US, "%.10f", result);
            String formatted = String.format(new java.util.Locale("es", "ES"), "%.10f", result);
            formatted=formatted.replaceAll("0*$", "").replaceAll("[\\.,]$", "");
            formatted = formatted.replace('.', ',');
            return formatted;
        }
    }

    @Override
    public void onBotonRetroceso(){
        // Si acabamos de mostrar un resultado (resetingInput es true), 
        // sincronizamos el buffer con el número formateado antes de empezar a borrar.
        if (resetingInput) {
            Double valorActual = view.getDisplayValue();
            if (valorActual != null) {
                // Usamos formatResult para que el buffer tenga, por ejemplo, "567" y no "567.0"
                displayBuffer = new StringBuilder(formatResult(valorActual));
            }
            resetingInput = false; // Pasamos a modo edición
        }

        // Comprobamos que haya algo que borrar
        if (displayBuffer.length() > 0) {
            // ELIMINA SOLO EL ÚLTIMO CARÁCTER
            displayBuffer.setLength(displayBuffer.length() - 1);

            // Actualizamos la pantalla con lo que queda en el buffer
            if (displayBuffer.length() == 0) {
                view.clearDisplay();
            } else {
                view.setDisplay(displayBuffer.toString());
            }
        }
    }
}
