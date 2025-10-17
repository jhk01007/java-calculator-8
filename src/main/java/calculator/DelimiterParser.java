package calculator;

import java.util.Optional;

/**
 * 문자열에서 구분자를 추출하는 클래스
 */
public class DelimiterParser {

    private static final String CUSTOM_DELIMITER_REGEX = "^//.+\\\\n.*"; // 커스텀 구분자 정규표현식

    /**
     * 커스텀 구분자를 추출하는 메서드
     * @param str - 추출할 문자열
     * @return 커스텀 구분자
     */
    public Optional<String> parseCustomDelimiter(String str) {

        // 커스텀 구분자를 추가하는 입력값이라면 (정규 표현식 활용)
        if (str.matches(CUSTOM_DELIMITER_REGEX)) {
            String customDelimiter = str.substring(2, str.indexOf("\\n", 3)); // 커스텀 구분자 추출

            validateCustomDelimiterIsNotNumber(customDelimiter); // 커스텀 구분자가 숫자인지 검증
//            validateCustomDelimiterIsNotDot(customDelimiter); // 커스텀 구분자가 마침표(.)인지 검증

            return Optional.of(customDelimiter);
        } else {
            return Optional.empty();
        }
    }

    /**
     * 문자열로부터 커스텀 구분자 접두사를 제거하는 메서드
     * @param str 제거할 문자열
     * @return 제거된 결과
     */
    public String removeCustomDelimiterPrefix(String str) {
        return str.matches(CUSTOM_DELIMITER_REGEX) ?
                str.substring(str.indexOf("\\n", 3) + 2) : str;
    }

//    /**
//     * 커스텀 구분자가 마침표(.)이 아닌지 검증
//     * @param customDelimiter - 커스텀 구분자
//     */
//    private void validateCustomDelimiterIsNotDot(String customDelimiter) {
//        if (customDelimiter.equals(".")) {
//            throw new IllegalArgumentException("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
//        }
//    }

    /**
     * 커스텀 구분자가 숫자가 아닌지 검증
     * @param customDelimiter
     */
    private void validateCustomDelimiterIsNotNumber(String customDelimiter) {
        try {
            Double.parseDouble(customDelimiter);
            // 변환이 성공하면 예외 발생
            throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
        } catch (NumberFormatException ignored) {
        }
    }
}
