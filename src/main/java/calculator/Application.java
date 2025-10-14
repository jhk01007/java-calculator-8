package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.List;

public class Application {

    private static List<String> separator = Arrays.asList(",", ":");

    public static void main(String[] args) {
        // 입력
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();

        double total = 0;

        // 빈문자열이 아닌 경우
        if(!input.isBlank()) {
            // 각 구분자를 통일
            for (String s : separator) {
                input = input.replace(s, " ");
            }

            // 숫자들만 뽑아냄
            String[] numbers = input.split(" ");

            // 타입변환 후 각 숫자의 합 계산
            for (int i = 0; i < numbers.length; i++) {
                total += Double.parseDouble(numbers[i]);
            }
        }

        // 출력
        System.out.printf("결과 : %f", total);
    }
}
