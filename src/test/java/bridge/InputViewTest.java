package bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class InputViewTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class BridgeSizeTest {
        InputView inputView = new InputView();
        Method validateBridgeSize;

        {
            try {
                validateBridgeSize = inputView.getClass().getDeclaredMethod("validateBridgeSize", String.class);
                validateBridgeSize.setAccessible(true);
            } catch (Exception e) {

            }
        }

        @ParameterizedTest(name="{index} : {0} ==> No Exception ")
        @MethodSource("bridgeSizeValidTestArgument")
        @DisplayName("3에서 20사이의 정수 값 입력시 ")
        void bridgeSizeValidTest(String inputString, int value) {

            assertThatNoException().isThrownBy(() -> {
                validateBridgeSize.invoke(inputView, inputString);
            });
            try {
                int result = (int) validateBridgeSize.invoke(inputView,inputString);
                assertThat(result).isEqualTo(value);
            }catch (Exception e){
                //Do Nothing
            }
        }

        Stream<Arguments> bridgeSizeValidTestArgument() {
            return Stream.of(
                    arguments("3", 3),
                    arguments("4", 4),
                    arguments("19", 19),
                    arguments("20", 20)
            );
        }

        @ParameterizedTest(name="{index} : {0} ==> Exception ")
        @MethodSource("bridgeSizeInvalidTestArgument")
        @DisplayName("3에서 20 사이의 정수 이외의 값 입력시 ")
        void bridgeSizeInvalidTest(String inputString) {
            assertThatThrownBy(() -> {
                validateBridgeSize.invoke(inputView, inputString);
            });
        }

        Stream<Arguments> bridgeSizeInvalidTestArgument() {
            return Stream.of(
                    arguments("0"),
                    arguments("1"),
                    arguments("2"),
                    arguments("s4"),
                    arguments("10s"),
                    arguments("21"),
                    arguments("22")
            );
        }

    }

    @Nested
    class MovingTest {

    }

    @Nested
    class GameCommandTest {

    }
}
