package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    @DisplayName("커스텀_구분자_사용")
    void test_success_1() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    @DisplayName("빈_문자열_입력_시_0_반환")
    void test_success_2() {
        assertSimpleTest(() -> {
            run("\n");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    @DisplayName("기본 구분자(쉼표, 콜론) 혼합 사용")
    void test_success_3() {
        assertSimpleTest(() -> {
            run("7,8:9");
            assertThat(output()).contains("결과 : 24");
        });
    }

    @Test
    @DisplayName("기본 구분자(쉼표, 콜론) 혼합 사용")
    void test_success_4() {
        assertSimpleTest(() -> {
            run("//-\\n10-20-30");
            assertThat(output()).contains("결과 : 60");
        });
    }

    @Test
    @DisplayName("커스텀 구분자로 '\\n' 사용")
    void test_success_5() {
        assertSimpleTest(() -> {
            run("//\\n\\n1\\n2");
            assertThat(output()).contains("결과 : 3");
        });
    }

    @Test
    void test_fail_1() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("숫자 외 문자 포함 시 예외 발생")
    void test_fail_2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//#\n1#2,a"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
    @Test
    @DisplayName("숫자가 누락되는 경우 예외발생")
    void test_fail_3() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
