package calculator.model;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 문자열에서 구분자를 추출하는 클래스
 */
public class DelimiterParser {

    private static final String PREFIX = "//";
    private static final String SUFFIX = "\\n";
    private static final String CUSTOM_DELIMITER_REGEX = "^" + Pattern.quote(PREFIX) + ".+" + Pattern.quote(SUFFIX) + ".*"; // 커스텀 구분자 정규표현식

    /**
     * 커스텀 구분자를 추출하는 메서드
     *
     * @param str - 추출할 문자열
     * @return 커스텀 구분자
     */
    public Optional<String> parseCustomDelimiter(String str) {

        // 커스텀 구분자를 추가하는 입력값이라면 (정규 표현식 활용)
        if (str.matches(CUSTOM_DELIMITER_REGEX)) {
            String customDelimiter = str.substring(2, str.indexOf(SUFFIX, 3)); // 커스텀 구분자 추출

            validateCustomDelimiterIsNotNumber(customDelimiter); // 커스텀 구분자가 숫자인지 검증
//            validateCustomDelimiterIsNotDot(customDelimiter); // 커스텀 구분자가 마침표(.)인지 검증

            return Optional.of(Pattern.quote(customDelimiter)); // 문자그대로 입력되도록 이스케이프 처리
        } else {
            return Optional.empty();
        }
    }

    /**
     * 문자열로부터 커스텀 구분자 접두사를 제거하는 메서드
     *
     * @param str 제거할 문자열
     * @return 제거된 결과
     */
    public String removeCustomDelimiterPrefix(String str) {
        return str.matches(CUSTOM_DELIMITER_REGEX) ?
                str.substring(str.indexOf(SUFFIX, 3) + 2) : str;
    }

    // 커스텀 구분자 검증

    //    /**
//     * 커스텀 구분자가 마침표(.)이 아닌지 검증
//     * @param customDelimiter - 커스텀 구분자
//     */
//    private void validateCustomDelimiterIsNotDot(String customDelimiter) {
//        if (customDelimiter.equals(".")) {
//            throw new IllegalArgumentException("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
//        }
//    }

    private void validateCustomDelimiterIsNotNumber(String customDelimiter) {
        if (isNumber(customDelimiter)) {
            throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
        }
    }
    // 숫자인지 여부를 검사하는 유틸 메서드
    private boolean isNumber(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
