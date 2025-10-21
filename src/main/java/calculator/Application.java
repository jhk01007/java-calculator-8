package calculator;


import calculator.controller.CalculatorController;
import calculator.model.Calculator;
import calculator.model.DelimiterParser;
import calculator.model.NumberParser;
import calculator.model.StringSumCalculatorFacade;
import calculator.view.InputView;
import calculator.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        DelimiterParser delimiterParser = new DelimiterParser();
        NumberParser numberParser = new NumberParser();
        Calculator calculator = new Calculator();

        StringSumCalculatorFacade stringSumCalculatorFacade = new StringSumCalculatorFacade(delimiterParser, numberParser, calculator);

        CalculatorController calculatorController = new CalculatorController(inputView, stringSumCalculatorFacade, outputView);
        calculatorController.start();
    }
}
