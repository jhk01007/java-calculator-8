package calculator.controller;

import calculator.view.InputView;
import calculator.view.OutputView;
import calculator.model.StringSumCalculatorFacade;

public class StringSumCalculatorController {

    private final InputView inputView;
    private final StringSumCalculatorFacade stringSumCalculatorFacade;
    private final OutputView outputView;

    public StringSumCalculatorController(InputView inputView, StringSumCalculatorFacade stringSumCalculatorFacade, OutputView outputView) {
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
