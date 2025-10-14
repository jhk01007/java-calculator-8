package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final List<String> SEPARATOR_LIST = new ArrayList<>(List.of(",", ";"));

    public static void main(String[] args) {
        // 입력
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();

        // 커스텀 구분자 추출
        // 커스텀 구분자를 새로 지정하는 문자열인지(정규 표현식 활용)
        if(input.matches("^//[^\\\\n]+\\\\n.*")) {
            // 커스텀 구분자 추출
            String customSeparator = input.substring(2, input.indexOf("\\n"));
            SEPARATOR_LIST.add(customSeparator);

            // 추출 후 커스텀 구분자 관련 부분은 제거
            input = input.substring(input.indexOf("\\n") + 2);
        }


        double total = 0;

        // 빈문자열이 아닌 경우
        if(!input.isBlank()) {
            // 각 구분자를 통일
            for (String s : SEPARATOR_LIST) {
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
