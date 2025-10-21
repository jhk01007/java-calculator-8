package calculator.controller;

import calculator.view.InputView;
import calculator.view.OutputView;
import calculator.StringSumCalculatorFacade;

public class CalculatorController {

    private final InputView inputView;
    private final StringSumCalculatorFacade stringSumCalculatorFacade;
    private final OutputView outputView;

    public CalculatorController(InputView inputView, StringSumCalculatorFacade stringSumCalculatorFacade, OutputView outputView) {
        this.inputView = inputView;
        this.stringSumCalculatorFacade = stringSumCalculatorFacade;
        this.outputView = outputView;
    }

    public String start() {
        String input = inputView.getInput();

        int result = stringSumCalculatorFacade.calculate(input);

        outputView.print(result);

        return "";
    }
}
