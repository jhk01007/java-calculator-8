# **🧮 문자열 계산기**

## ✅ 문제 요구사항

### **1. 쉼표(,) 콜론(:) 구분자**

- 구분자가 쉼표(,) 또는 콜론(:)인 경우, 해당 구분자를 기준으로 분리한 각 숫자의 합을 반환해야 한다.
- 이때 숫자는 양수이어야 한다.

### **2. 커스텀 구분자**

- 쉼표, 콜론 외에도 커스텀 구분자를 사용할 수 있어야 한다.
- 커스텀 구분자는 문자열 앞 부분의 "//"와 "\n" 사이에 위치한다.

### **3. 예외 처리**

- 사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`이 발생하며 애플리케이션이 종료돼야 한다.

### **⎡ Edge Case**

- 빈문자열이 들어오는 경우: 0

---

## ✏️ 풀이 과정

### 1. 쉼표(,) 콜론(:) 구분자

처음에는 각 구분자를 기준으로 `split()`함수를 이용해 문자열을 나누고, 나눠진 요소가 숫자라면 더하고 그렇지 않다면 다음 구분자로 다시 나누는 방식을 시도했다.

하지만 이 방식은 구분자가 많아질수록 재귀 호출이 필요해 비효율적이라고 판단했다.

그래서 더 효율적인 방법을 고민하던 중, 모든 구분자가 서로 다른 기능을 하는 것이 아니라 **모두 더하기 역할을 한다는 점**에 주목했다.

즉, 구분자들이 동일한 의미를 가지므로 **하나의 공통 구분자로 통일한 뒤 `split()`을 한 번만 수행하면 모든 숫자를 한 번에 추출할 수 있다**는 결론에 도달했다.

```java
// 각 구분자를 통일
for (String s : SEPARATOR_LIST) {
    input = input.replace(s, " ");
}

// 숫자들만 뽑아냄
String[] numbers = input.split(" ");
```

### 2. 커스텀 구분자

커스텀 구분자는 문자열 앞부분의 "`//`"와 "`\n`"사이에 위치한다.

정규표현식은 다음과 같다.

커스텀 구분자를 뽑아내려면 우선 입력받은 문자열이 해당 형식으로 되어 있는지를 알아야 하는데 정규표현식이 가장 적합하다고 생각했다.

```
^//[^\\n]+\\n.*
```

여기선 “\n”이 개행문자가 아닌 실제 문자로 인식돼야 하므로 정규표현식에선 “`\\n`”으로 써야 한다.

이를 자바 문자열 리터럴에 쓰려면 역슬래시(`/`)를 한 번 더 써야 한다.

```java
String regex = "^//[^\\\\n]+\\\\n.*";
```

자바에서 역슬래시(`\`) 문자를 사용하려면 두번(`\\`) 써야 하기 때문이다.

이를 통해 커스텀 구분자를 뽑아내고 커스텀 구분자와 관련된 부분을 제거해주었다.

```java
// 커스텀 구분자를 새로 지정하는 문자열인지(정규 표현식 활용)
if(input.matches("^//[^\\\\n]+\\\\n.*")) {
    // 커스텀 구분자 추출
    String customSeparator = input.substring(2, input.indexOf("\\n"));
    SEPARATOR_LIST.add(customSeparator);

    // 추출 후 커스텀 구분자 관련 부분은 제거
    input = input.substring(input.indexOf("\\n") + 2);
}
```

### 3. 예외 처리

우선, 발생할 수 있는 예외 상황부터 정리해보았다.

**⚠️ 예외 상황**

1. 지정된 구분자(콤마, 콜론, 커스텀 구분자)가 아닌 다른 구분자가 들어오는 경우
2. 숫자가 음수가 들어오는 경우
3. 커스텀 구분자가 숫자인 경우
4. 커스텀 구분자로 마침표(.)이 들어오는 경우 (숫자가 실수 일수도 있음)

하나하나 구현해보았다.

1. 지정된 구분자(콤마, 콜론, 커스텀 구분자)가 아닌 다른 구분자가 들어오는 경우

    <aside>

   현재 코드에서는 모든 지정된 구분자를 통일하고 `split()`으로 통일된 구분자를 통해 숫자들을 뽑아낸다.

    ```java
    // 각 구분자를 통일
    for (String s : SEPARATOR_LIST) {
        input = input.replace(s, " ");
    }
    
    // 숫자들만 뽑아냄
    String[] numbers = input.split(" ");
    ```

   그렇다면 여기서 뽑아낸 배열들 중 숫자가 아닌 것이 있으면 예외를 발생시키면 될 것이다.

   코드는 다음과 같다.

    ```java
    // 모든 요소들이 지정된 구분자외에 다른 구분자인지 검증
    for (int i = 0; i < numbers.length; i++) {
        try {
            double num = Double.parseDouble(numbers[i]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("지정된 구분자외에 다른 구분자는 사용할 수 없습니다.");
        }
    }
    ```

    </aside>

2. 숫자가 음수가 들어오는 경우

    <aside>

   위에서 구현했던 코드에 양수인지만 판단하는 코드만 추가하면 된다.

    ```java
    // 모든 요소들이 지정된 구분자외에 다른 구분자이거나 양수가 아닌지 검증
    for (int i = 0; i < numbers.length; i++) {
        try {
            double num = Double.parseDouble(numbers[i]);
            if(num <= 0) {
                throw new IllegalArgumentException("양수만 가능합니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("지정된 구분자외에 다른 구분자는 사용할 수 없습니다.");
        }
    }
    
    ```

    </aside>

3. 커스텀 구분자가 숫자인 경우

    <aside>

   커스텀 구분자를 추출하고 해당 구분자를 숫자로 변환해본 뒤 성공한다면 예외를 발생시킨다.

    ```java
    // 커스텀 구분자 추출
    String customSeparator = input.substring(2, input.indexOf("\\n"));
    
    // 커스텀 구분자가 숫자인지 검증
    try {
        double value = Double.parseDouble(customSeparator);
        // 변환이 성공하면 예외 발생
        throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
    } catch (NumberFormatException ignored) {
    }
    ```

    </aside>

4. 커스텀 구분자로 마침표(.)이 들어오는 경우 (숫자가 실수 일수도 있음)

    <aside>

   숫자가 아니라면 마침표인지 검증한다.

    ```java
    // 커스텀 구분자 추출
    String customSeparator = input.substring(2, input.indexOf("\\n"));
    
    // 커스텀 구분자가 숫자인지 검증
    try {
        double value = Double.parseDouble(customSeparator);
        // 변환이 성공하면 예외 발생
        throw new IllegalArgumentException("커스텀 구분자로 숫자는 사용할 수 없습니다.");
    } catch (NumberFormatException ignored) {
    }
    
    // 커스텀 구분자가 마침표(.)인지 검증
    if (customSeparator.equals(".")) {
        throw new IllegalArgumentException("커스텀 구분자로 마침표(.)은 사용할 수 없습니다.");
    }
    ```

    </aside>




## 🪠 리팩토링

### 1. 메서드로 분리

메인 메서드를 깔끔하게 하기 위해 다음 부분을 메서드로 분리하였다.

- 커스텀 구분자를 추출하는 부분을 메서드로 분리

    ```java
    // 커스텀 구분자 추출
    // 커스텀 구분자를 새로 지정하는 문자열인지(정규 표현식 활용)
    if (input.matches(CUSTOM_SEPARATOR_REGEX)) {
        String customSeparator = extractCustomSeparator(input); // 커스텀 구분자 추출
        SEPARATOR_LIST.add(customSeparator); // 커스텀 구분자 추가
    
        input = input.substring(input.indexOf("\\n") + 2); // 추출 후 커스텀 구분자 관련 부분은 제거
    }
    ```

  [분리된 메서드]

    ```java
    private static String extractCustomSeparator(String input) {
        String customSeparator = input.substring(2, input.indexOf("\\n")); // 커스텀 구분자 추출
    
        isCustomSeparatorNumber(customSeparator); // 커스텀 구분자가 숫자인지 검증
        isCustomSeparatorDot(customSeparator); // 커스텀 구분자가 마침표(.)인지 검증
    
        return customSeparator;
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
    ```

- 뽑아낸 숫자가 담긴 문자열 형태의 리스트들의 각 요소를 숫자로 변환하는 부분을 메서드로 분리

    ```java
    for (int i = 0; i < numbers.length; i++) {
        // 문자열을 숫자로 변환
        total += convertStringToNumber(numbers[i]);
    }
    ```

  [분리된 메서드]

    ```java
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
    ```


### 2. 클래스로 분리 (캡슐화)

문자열 계산기를 하나의 객체라고 생각하고 분리해보았다.

객체를 사용하는 사용자 입장에서는 입력 문자열을 주고 결과값만 받으면 된다. 그 안에서 일어나는 커스텀 구분자 추출 및 추가, 구분자 통일, 숫자 추출, 숫자 변환 등의 일련의 과정은 몰라도 된다.

분리한 결과는 다음과 같다.

```java
public class StringSumCalculator {
    private static final List<String> DEFAULT_SEPARATOR_LIST = List.of(",", ";"); // 콤마와 콜론은 미리 초기화
    private static final String CUSTOM_SEPARATOR_REGEX = "^//[^\\\\n]+\\\\n.*"; // 커스텀 구분자 정규표현식

    private final Set<String> separators;

    public StringSumCalculator() {
        separators = new HashSet<>(DEFAULT_SEPARATOR_LIST);
    }
    
    // 계산
    public double calculate(String str) {
        // 내부 로직 생략
    }
    

    // 커스텀 구분자 추출
    private String extractCustomSeparator(String str) {
        // 내부 로직 생략
    }

    private void isCustomSeparatorDot(String customSeparator) {
        // 내부 로직 생략
    }

    private void isCustomSeparatorNumber(String customSeparator) {
        // 내부 로직 생략
    }

    // 문자를 숫자로 변환
    private double convertStringToNumber(String numbers) {
        // 내부 로직 생략
}

```

메인 메서드에서는 해당 객체만 선언해 `calculate()`만 호출하면 된다.

```java
public static void main(String[] args) {
    // 입력
    System.out.println("덧셈할 문자열을 입력해주세요.");
    String input = Console.readLine();

    // 계산
    StringSumCalculator stringSumCalculator = new StringSumCalculator();
    double result = stringSumCalculator.calculate(input);

    // 출력
    System.out.printf("결과 : %f\n", result);
}
```