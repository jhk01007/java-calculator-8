package calculator;


import calculator.controller.StringSumCalculatorController;
import calculator.model.CalculatorService;
import calculator.model.DelimiterParser;
import calculator.model.NumberParserService;
import calculator.model.StringSumCalculatorFacade;
import calculator.view.InputView;
import calculator.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        DelimiterParser delimiterParser = new DelimiterParser();
        NumberParserService numberParserService = new NumberParserService();
        CalculatorService calculatorService = new CalculatorService();

        StringSumCalculatorFacade stringSumCalculatorFacade = new StringSumCalculatorFacade(delimiterParser, numberParserService, calculatorService);

        StringSumCalculatorController stringSumCalculatorController = new StringSumCalculatorController(inputView, stringSumCalculatorFacade, outputView);
        stringSumCalculatorController.start();
    }
}
