package calculator;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StringSumCalculator {

    private static final List<String> DEFAULT_DELIMITER_LIST = List.of(",", ":"); // 콤마와 콜론은 미리 초기화

    private final DelimiterParser delimiterParser;
    private final NumberParser numberParser;

    public StringSumCalculator() {
        delimiterParser = new DelimiterParser();
        numberParser = new NumberParser();
    }

    /**
     * 입력값으로 부터 구분자 및 숫자를 추출하여 더하는 메서드
     * @param input: 계산할 문자열
     * @return 계산결과
     */
    public double calculate(String input) {

        if (input == null || input.isBlank()) { // 값이 아무것도 들어오지 않는다면 0 반환
            return 0;
        }

        Set<String> delimiters = new HashSet<>(DEFAULT_DELIMITER_LIST);

        Optional<String> customDelimiter = delimiterParser.parseCustomDelimiter(input); // 입력값으로부터 커스텀 구분자 추출
        if(customDelimiter.isPresent()) {
            input = delimiterParser.removeCustomDelimiterPrefix(input); // 입력값에서 커스텀 구분자 접두사 제거
            delimiters.add(customDelimiter.get()); // 커스텀 구분자 추가
        }

        List<Double> numbers = numberParser.parseNumbers(input, delimiters); // 숫자만 추출

        // 계산
        double total = 0;
        for (Double number : numbers) {
            total += number;
        }
        return total;
    }

}
