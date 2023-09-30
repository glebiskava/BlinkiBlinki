import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestEncodeDecode {
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
}
