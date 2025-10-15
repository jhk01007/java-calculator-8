package calculator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringSumCalculator {
    private static final List<String> DEFAULT_SEPARATOR_LIST = List.of(",", ";"); // 콤마와 콜론은 미리 초기화
    private static final String CUSTOM_SEPARATOR_REGEX = "^//[^\\\\n]+\\\\n.*"; // 커스텀 구분자 정규표현식

    private final Set<String> separators;

    public StringSumCalculator() {
        separators = new HashSet<>(DEFAULT_SEPARATOR_LIST);
    }

    // 계산
    public double calculate(String str) {

        // 값이 아무것도 들어오지 않는다면 0 반환
        if(str.isBlank()) {
            return 0;
        }
        // 커스텀 구분자 추출
        // 커스텀 구분자를 새로 지정하는 문자열인지(정규 표현식 활용)
        if (str.matches(CUSTOM_SEPARATOR_REGEX)) {
            String customSeparator = extractCustomSeparator(str); // 커스텀 구분자 추출
            separators.add(customSeparator); // 커스텀 구분자 추가

            str = str.substring(str.indexOf("\\n") + 2); // 추출 후 커스텀 구분자 관련 부분은 제거
        }

        double total = 0;
        // 각 구분자를 통일
        for (String s : separators) {
            str = str.replace(s, " ");
        }

        // 숫자들만 뽑아냄
        String[] numbers = str.split(" ");

        // 계산
        for (int i = 0; i < numbers.length; i++) {
            // 문자열을 숫자로 변환
            total += convertStringToNumber(numbers[i]);
        }
        
        return total;
    }

    // 커스텀 구분자 추출
    private String extractCustomSeparator(String str) {
        String customSeparator = str.substring(2, str.indexOf("\\n")); // 커스텀 구분자 추출

        isCustomSeparatorNumber(customSeparator); // 커스텀 구분자가 숫자인지 검증
        isCustomSeparatorDot(customSeparator); // 커스텀 구분자가 마침표(.)인지 검증

        return customSeparator;
    }

    private void isCustomSeparatorDot(String customSeparator) {
        if (customSeparator.equals(".")) {
            throw new IllegalArgumentException("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
        }
    }

    private void isCustomSeparatorNumber(String customSeparator) {
        try {
            double value = Double.parseDouble(customSeparator);
            // 변환이 성공하면 예외 발생
            throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
        } catch (NumberFormatException ignored) {
        }
    }

    // 문자를 숫자로 변환
    private double convertStringToNumber(String numbers) {
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

}
