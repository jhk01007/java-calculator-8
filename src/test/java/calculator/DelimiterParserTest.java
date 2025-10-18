package calculator;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DelimiterParserTest {

    DelimiterParser delimiterParser = new DelimiterParser();

    @Test
    @DisplayName("문자열에서 커스텀 구분자를 추출한다.")
    public void parseCustomDelimiter_success_1() throws Exception {
        // given
        String input = "//:\\n1:2,3";
        
        // when
        Optional<String> result = delimiterParser.parseCustomDelimiter(input);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(Pattern.quote(":"));
    }

    @Test
    @DisplayName("문자열에 커스텀 구분자가 없는 경우 아무것도 출력되지 않는다.")
    public void parseCustomDelimiter_success_2() throws Exception {
        // given
        String input = "1:2,3";

        // when
        Optional<String> result = delimiterParser.parseCustomDelimiter(input);

        // then
        assertThat(result.isPresent()).isFalse();
    }

//    @Test
//    @DisplayName("커스텀 구분자가 마침표(.)인 경우 에러가 발생한다.")
//    public void parseCustomDelimiter_fail_1() throws Exception {
//        // given
//        String input = "//.\\n1:2,3";
//
//        // when // then
//        assertThatThrownBy(() -> delimiterParser.parseCustomDelimiter(input))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
//
//    }


    @Test
    @DisplayName("커스텀 구분자가 숫자인 경우 에러가 발생한다.")
    public void parseCustomDelimiter_fail_2() throws Exception {
        // given
        String input = "//3\\n1:2,3";

        // when // then
        assertThatThrownBy(() -> delimiterParser.parseCustomDelimiter(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커스텀 구분자로 숫자는 사용할 수 없습니다.");
    }
    
    //
    
    @Test
    @DisplayName("문자열에서 커스텀 구분자 접두사를 제거한다.")
    public void removeCustomDelimiterPrefix_success_1() throws Exception {
        // given
        String input = "//:\\n1:2,3";
        
        // when
        String result = delimiterParser.removeCustomDelimiterPrefix(input);

        // then
        assertThat(result).isEqualTo("1:2,3");
    }

    @Test
    @DisplayName("문자열에서 커스텀 구분자 접두사가 없으면 아무일도 일어나지 않는다.")
    public void removeCustomDelimiterPrefix_success_2() throws Exception {
        // given
        String input = "1:2,3";

        // when
        String result = delimiterParser.removeCustomDelimiterPrefix(input);

        // then
        assertThat(result).isEqualTo(input);
    }
}