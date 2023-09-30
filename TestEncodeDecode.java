import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class TestEncodeDecode {

//    @ParameterizedTest
//    @ValueSource(ints = {15, 64, 12})
//    public void RGBTest(int r, int g, int b) {
//        RgbRequest rgb = new RgbRequest(r, g, b);
//        String expectedResult = "#R" + r + "G" + g + "B" + b + "!";
//
//        Assertions.assertEquals(expectedResult, rgb.encode(rgb));
//    }

@MethodSource("rgbValues")
@ParameterizedTest (name = "Test Encode For RGBRequest R-{0} G-{1} B-{2}")
    public void RGBRequest_Encode(int r, int g, int b) {
        RgbRequest rgb = new RgbRequest(r, g, b);
        String expectedResult = "#R" + r + "G" + g + "B" + b + "!";
        Assertions.assertEquals(expectedResult, rgb.encode());
    }

    static Stream<Arguments> rgbValues() {
        return Stream.of(
                Arguments.of(15, 54, 68),
                Arguments.of(64, 236, 57),
                Arguments.of(32, 64, 34)
        );
    }

    @MethodSource("onRequestValues")
    @ParameterizedTest (name = "Test Encode For OnRequest Led-{0} Time-{1}")
    public void OnRequest_Encode(int led, int time) {
        OnRequest onRequest = new OnRequest(led, time);
        String expectedResult = "#On " + led + " " + time + "!";
        Assertions.assertEquals(expectedResult, onRequest.encode());
    }

    static Stream<Arguments> onRequestValues() {
        return Stream.of(
                Arguments.of(0, 64),
                Arguments.of(2, 35),
                Arguments.of(1, 24)
        );
    }

    @MethodSource("rgbRequestValues")
    @ParameterizedTest (name = "Test Decode For RGBRequest R-{1} G-{2} B-{3}")
    public void RGBRequest_Decode(String encoded, int r, int g, int b) {
        RgbRequest rgbRequest;
        try {
            rgbRequest = new RgbRequest(encoded);
        } catch (DecodingException e) {
            throw new AssertionError(e);
        }
        RgbRequest expectedResult = new RgbRequest(r, g, b);
        Assertions.assertEquals(expectedResult.toString(), rgbRequest.toString());
    }

    static Stream<Arguments> rgbRequestValues() {
        return Stream.of(
                Arguments.of("#R20G10B60!",20,10,60),
                Arguments.of("#R10G120B60!",10,120,60),
                Arguments.of("#R210G130B65!",210,130,65)
        );
    }

    @MethodSource("onRequest_D_Values")
    @ParameterizedTest (name = "Test Decode For OnRequest Led-{0} Time-{1}")
    public void OnRequest_Decode(String encoded, int led, int time) {
        OnRequest onRequest;
        try {
            onRequest = new OnRequest(encoded);
        } catch (DecodingException e) {
            throw new AssertionError(e);
        }
        OnRequest expectedResult = new OnRequest(led, time);
        Assertions.assertEquals(expectedResult.toString(), onRequest.toString());
    }

    static Stream<Arguments> onRequest_D_Values() {
        return Stream.of(
                Arguments.of("#On 0 15!", 0, 15),
                Arguments.of("#On 1 20!", 1, 20),
                Arguments.of("#On 2 30!", 2, 30)
        );
    }
}