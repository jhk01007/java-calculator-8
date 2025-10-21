package calculator.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String getInput() {
        // 입력
        System.out.println("덧셈할 문자열을 입력해주세요.");
        return Console.readLine();
    }
}
