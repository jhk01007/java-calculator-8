package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        // 입력
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();

        // 계산
        StringSumCalculator stringSumCalculator = new StringSumCalculator();
        double result = stringSumCalculator.calculate(input);

        // 출력
        System.out.printf("결과 : %f\n", result);
    }
}
