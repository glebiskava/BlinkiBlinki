import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class TestEncodeDecode {
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

    @ParameterizedTest(name = "Critical: On request decode with led-{1} time-{2}")
    @MethodSource("onRequestValues")
    public void on_request_decode(String encoded, int led, int time){
        try {
            OnRequest onRequest = new OnRequest(encoded);
            OnRequest expected = new OnRequest(led, time);
            assertEquals(onRequest, expected);
        } catch (DecodingException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    static Stream<Arguments> onRequestValues() {
        return Stream.of(
                Arguments.of("#On 1 1!", 1, 1),
                Arguments.of("#On 0 30!", 0, 30),
                Arguments.of("#On 2 10!", 2, 10)
        );
    }

    @ParameterizedTest(name = "Critical: Off request decode with led-{1}")
    @MethodSource("offRequestValues")
    public void off_request_decode(String encoded, int led){
        try {
            OffRequest offRequest = new OffRequest(encoded);
            OffRequest expected = new OffRequest(led);
            assertEquals(offRequest, expected);
        } catch (DecodingException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    static Stream<Arguments> offRequestValues() {
        return Stream.of(
                Arguments.of("#Off 1!", 1),
                Arguments.of("#Off 0!", 0),
                Arguments.of("#Off 2!", 2)
        );
    }
    
    @ParameterizedTest(name = "Critical: Test Decode everything all with {0}")
    @MethodSource("decodeAllValues")
    public void decode_all(String encoded, int led, int time, int r, int g, int b, String type){
        switch (type) {
            case "Off":
                try {
                    OffRequest offRequest = (OffRequest)Decoder.decode(encoded);
                    OffRequest expected = new OffRequest(led);
                    assertEquals(offRequest, expected);
                } catch (DecodingException e) {
                    throw new AssertionError(e.getMessage());
                }
                break;
            
            case "On":
                    try {
                    OnRequest onRequest = (OnRequest)Decoder.decode(encoded);
                    OnRequest expected = new OnRequest(led,time);
                    assertEquals(onRequest, expected);
                } catch (DecodingException e) {
                    throw new AssertionError(e.getMessage());
                }
                break;
            case "RGB":
                try {
                    RgbRequest rgbRequest = (RgbRequest)Decoder.decode(encoded);
                    RgbRequest expected = new RgbRequest(r,g,b);
                    assertEquals(rgbRequest, expected);
                } catch (DecodingException e) {
                    throw new AssertionError(e.getMessage());
                }
                break;
            case "Wrong":
                DecodingException thrown = assertThrows(DecodingException.class, () -> {
                    @SuppressWarnings("unused")
                    LedRequest ledRequest = Decoder.decode(encoded);
                });
                Assertions.assertEquals("Data Cannot be Decoded as it doesn't fit any criteria", thrown.getMessage());
                break;
            default:
                break;
        }
    }

    static Stream<Arguments> decodeAllValues() {
        return Stream.of(
                Arguments.of("#Off 1!", 1, 0,0,0,0, "Off"),
                Arguments.of("#On 1 20!", 1, 20, 0, 0, 0, "On"),
                Arguments.of("#R10G20B30!", 0, 0, 10, 20, 30, "RGB"),
                Arguments.of("Wrong encoding", 0, 0, 10, 20, 30, "Wrong")
        );
    }
    

    @ParameterizedTest(name = "Critical: Test Encode everything with {0}")
    @MethodSource("encodeAllValues")
    public void encode_all(String expected, int led, int time, int r, int g, int b, String type){
        switch (type) {
            case "Off":
                    OffRequest offRequest = new OffRequest(led);
                    String encodedOff = Encoder.encode(offRequest);
                    assertEquals(encodedOff, expected);
                break;
            case "On":
                    OnRequest onRequest = new OnRequest(led,time);
                    String encodedOn = Encoder.encode(onRequest);
                    assertEquals(encodedOn, expected);
                break;
            case "RGB":
                    RgbRequest rgbRequest = new RgbRequest(r,g,b);
                    String encodedRgb = Encoder.encode(rgbRequest);
                    assertEquals(encodedRgb, expected);
                break;
            default:
                break;
        }
    }

    static Stream<Arguments> encodeAllValues() {
        return Stream.of(
                Arguments.of("#Off 1!", 1, 0,0,0,0, "Off"),
                Arguments.of("#On 1 20!", 1, 20, 0, 0, 0, "On"),
                Arguments.of("#R10G20B30!", 0, 0, 10, 20, 30, "RGB")
        );
    }

    @MethodSource("onRequestEncValues")
    @ParameterizedTest (name = "Test Encode For OnRequest Led-{0} Time-{1}")
    public void OnRequest_Encode(int led, int time) {
        OnRequest onRequest = new OnRequest(led, time);
        String expectedResult = "#On " + led + " " + time + "!";
        Assertions.assertEquals(expectedResult, onRequest.encode());
    }

    static Stream<Arguments> onRequestEncValues() {
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