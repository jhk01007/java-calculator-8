package calculator;

import java.util.*;

/**
 * 문자열에서 숫자를 추출하는 클래스
 */
public class NumberParser {
    /**
     * 문자열로부터 구분자를 제거하고 숫자만 반환
     * @param str - 계산할 문자열
     * @param delimiters - 구분자 셋
     * @return - 추출된 숫자 리스트
     */
    public List<Integer> parseNumbers(String str, Set<String> delimiters) {

        // 구분자들을 정규표현식으로 변환
        String delimiterRegex = String.join("|", delimiters);

        // 숫자들만 뽑아냄
        String[] parsedNumbers = str.split(delimiterRegex, -1);

        // 뽑아낸 숫자들을 Double로 형 변환
        List<Integer> convertedNumbers = new ArrayList<>();
        for (int i = 0; i < parsedNumbers.length; i++) {
            convertedNumbers.add(convertStringToNumber(parsedNumbers[i]));
        }

        return convertedNumbers;
    }

    /**
     * 문자열을 숫자로 변환
     * @param number - 변환할 문자열
     * @return - 변환된 숫자
     */
    private int convertStringToNumber(String number) {

        if(number.isEmpty()) {
            throw new IllegalArgumentException("빈 피연산자가 포함되어 있습니다.");
        }

        try {
            int num = Integer.parseInt(number);

            // 양수인지 검증
            validatePositiveNumber(num);

            return num;
        } catch (NumberFormatException e) {
            // 숫자가 아닌 문자가 오는 경우(= 지정된 구분자외에 다른 구분자가 포함되어 있는 경우)
            throw new IllegalArgumentException("입력값이 잘못됐거나 지정된 구분자외에 다른 구분자는 사용할 수 없습니다.");
        }
    }

    /**
     * 해당 숫자가 양수인지 검증
     * @param num - 검증할 숫자
     */
    private void validatePositiveNumber(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("양수만 가능합니다.");
        }
    }
}
