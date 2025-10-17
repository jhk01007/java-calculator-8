package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberParserTest {

    NumberParser numberParser = new NumberParser();

    @Test
    @DisplayName("문자열로부터 구분자를 제거하고 숫자만 반환한다.")
    public void parseNumbers_success() throws Exception {
        // given
        String str = "1:2,3;4";
        Set<String> delimiters = Set.of(",", ":", ";");

        // when
        List<Integer> results = numberParser.parseNumbers(str, delimiters);

        // then
        assertThat(results).hasSize(4)
                .containsExactlyInAnyOrder(1, 2, 3, 4);
    }

    @Test
    @DisplayName("문자열에 음수가 있는 경우 에러가 발생한다.")
    public void parseNumbers_fail_1() throws Exception {
        // given
        String str = "1:2,-3;4";
        Set<String> delimiters = Set.of(",", ":", ";");

        // when // then
        assertThatThrownBy(() -> numberParser.parseNumbers(str, delimiters))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("양수만 가능합니다.");
    }

    @Test
    @DisplayName("문자열에 지정된 구분자외에 다른 구분자가 있는 경우 에러가 발생한다.")
    public void parseNumbers_fail_2() throws Exception {
        // given
        String str = "1:2,3;4";
        Set<String> delimiters = Set.of(",", ":");

        // when // then
        assertThatThrownBy(() -> numberParser.parseNumbers(str, delimiters))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력값이 잘못됐거나 지정된 구분자외에 다른 구분자는 사용할 수 없습니다.");
    }
}