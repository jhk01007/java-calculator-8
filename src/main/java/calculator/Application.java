package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final List<String> SEPARATOR_LIST = new ArrayList<>(List.of(",", ";")); // 콤마와 콜론은 미리 초기화

    public static void main(String[] args) {
        // 입력
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();

        // 커스텀 구분자 추출
        // 커스텀 구분자를 새로 지정하는 문자열인지(정규 표현식 활용)
        if (input.matches("^//[^\\\\n]+\\\\n.*")) {
            // 커스텀 구분자 추출
            String customSeparator = input.substring(2, input.indexOf("\\n"));

            // 커스텀 구분자가 숫자인지 검증
            try {
                double value = Double.parseDouble(customSeparator);
                // 변환이 성공하면 예외 발생
                throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
            } catch (NumberFormatException ignored) {
            }

            // 커스텀 구분자가 마침표(.)인지 검증
            if (customSeparator.equals(".")) {
                throw new IllegalArgumentException("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
            }

            SEPARATOR_LIST.add(customSeparator);

            // 추출 후 커스텀 구분자 관련 부분은 제거
            input = input.substring(input.indexOf("\\n") + 2);
        }


        double total = 0;

        // 빈문자열이 아닌 경우
        if (!input.isBlank()) {
            // 각 구분자를 통일
            for (String s : SEPARATOR_LIST) {
                input = input.replace(s, " ");
            }

            // 숫자들만 뽑아냄
            String[] numbers = input.split(" ");

            // 모든 요소들이 지정된 구분자외에 다른 구분자이거나 양수가 아닌지 검증
            for (int i = 0; i < numbers.length; i++) {
                try {
                    double num = Double.parseDouble(numbers[i]);
                    if (num <= 0) {
                        throw new IllegalArgumentException("양수만 가능합니다.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("지정된 구분자외에 다른 구분자는 사용할 수 없습니다.");
                }
            }

            // 타입변환 후 각 숫자의 합 계산
            for (int i = 0; i < numbers.length; i++) {
                total += Double.parseDouble(numbers[i]);
            }
        }

        // 출력
        System.out.printf("결과 : %f", total);
    }
}
