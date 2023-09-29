import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class RgbRequestTest {

//    @ParameterizedTest
//    @ValueSource(ints = {15, 64, 12})
//    public void RGBTest(int r, int g, int b) {
//        RgbRequest rgb = new RgbRequest(r, g, b);
//        String expectedResult = "#R" + r + "G" + g + "B" + b + "!";
//
//        Assertions.assertEquals(expectedResult, rgb.encode(rgb));
//    }

    @ParameterizedTest
    @MethodSource("rgbValues")
    public void RGBTest(int r, int g, int b) {
        RgbRequest rgb = new RgbRequest(r, g, b);
        String expectedResult = "#R" + r + "G" + g + "B" + b + "!";
        Assertions.assertEquals(expectedResult, rgb.encode(rgb));
    }

    static Stream<Arguments> rgbValues() {
        return Stream.of(
                Arguments.of(15, 64, 12),
                Arguments.of(64, 64, 12),
                Arguments.of(12, 64, 12)
        );
    }


}
