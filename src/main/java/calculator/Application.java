package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final List<String> SEPARATOR_LIST = new ArrayList<>(List.of(",", ";")); // 콤마와 콜론은 미리 초기화
    public static final String CUSTOM_SEPARATOR_REGEX = "^//[^\\\\n]+\\\\n.*"; // 커스텀 구분자 정규표현식

    public static void main(String[] args) {
        // 입력
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();

        // 커스텀 구분자 추출
        // 커스텀 구분자를 새로 지정하는 문자열인지(정규 표현식 활용)
        if (input.matches(CUSTOM_SEPARATOR_REGEX)) {
            String customSeparator = extractCustomSeparator(input); // 커스텀 구분자 추출
            SEPARATOR_LIST.add(customSeparator); // 커스텀 구분자 추가

            input = input.substring(input.indexOf("\\n") + 2); // 추출 후 커스텀 구분자 관련 부분은 제거
        }

        // 숫자 계산
        double total = 0;

        // 빈문자열이 아닌 경우
        if (!input.isBlank()) {
            // 각 구분자를 통일
            for (String s : SEPARATOR_LIST) {
                input = input.replace(s, " ");
            }

            // 숫자들만 뽑아냄
            String[] numbers = input.split(" ");

            for (int i = 0; i < numbers.length; i++) {
                // 문자열을 숫자로 변환
                total += convertStringToNumber(numbers[i]);
            }
        }

        // 출력
        System.out.printf("결과 : %f", total);
    }

    private static String extractCustomSeparator(String input) {
        String customSeparator = input.substring(2, input.indexOf("\\n")); // 커스텀 구분자 추출

        isCustomSeparatorNumber(customSeparator); // 커스텀 구분자가 숫자인지 검증
        isCustomSeparatorDot(customSeparator); // 커스텀 구분자가 마침표(.)인지 검증

        return customSeparator;
    }

    private static double convertStringToNumber(String numbers) {
        try {
            double num = Double.parseDouble(numbers);
            // 양수가 아닌 경우
            if (num <= 0) {
                throw new IllegalArgumentException("양수만 가능합니다.");
            }
            return num;
        } catch (NumberFormatException e) {
            // 숫자가 아닌 문자가 오는 경우(= 지정된 구분자외에 다른 구분자가 포함되어 있는 경우)
            throw new IllegalArgumentException("지정된 구분자외에 다른 구분자는 사용할 수 없습니다.");
        }
    }

    private static void isCustomSeparatorDot(String customSeparator) {
        if (customSeparator.equals(".")) {
            throw new IllegalArgumentException("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
        }
    }

    private static void isCustomSeparatorNumber(String customSeparator) {
        try {
            double value = Double.parseDouble(customSeparator);
            // 변환이 성공하면 예외 발생
            throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
        } catch (NumberFormatException ignored) {
        }
    }
}
