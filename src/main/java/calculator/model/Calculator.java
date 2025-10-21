package calculator.model;

import java.util.List;

public class Calculator {

    public int sum(List<Integer> numbers) {
        int total = 0;
        for (Integer number : numbers) {
            try {
                total = Math.addExact(total, number);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("계산결과가 너무 큽니다.");
            }
        }
        return total;
    }
}
