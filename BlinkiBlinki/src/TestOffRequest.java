import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestOffRequest {

    /**
     * Successful Tests for encoding OFF Requests
     *
     * @param encoded Encoded String
     * @param led     switched off LED
     * @throws DecodingException Wrong encoded String
     */
    @ParameterizedTest(name = "Critical: Test OFF Request encoded with: LED-{0}")
    @MethodSource("offRequestEncodeValues")
    public void Test_Encode_OFF_Request(String encoded, int led) throws DecodingException {
        OffRequest offRequest = new OffRequest(encoded);
        OffRequest expected = new OffRequest(led);
        Assertions.assertEquals(offRequest, expected);
    }

    static Stream<Arguments> offRequestEncodeValues() {
        return Stream.of(
                Arguments.of("#Off 0!", 0),
                Arguments.of("#Off 1!", 1),
                Arguments.of("#Off 2!", 2),
                Arguments.of("#Off 0!", 0),
                Arguments.of("#Off 1!", 1),
                Arguments.of("#Off 2!", 2),
                Arguments.of("#Off 0!", 0),
                Arguments.of("#Off 1!", 1),
                Arguments.of("#Off 2!", 2),
                Arguments.of("#Off 0!", 0)
        );
    }

    /**
     * Error Test for wrong encoded Off Request Values
     * @param led LED to be switched OFF
     */
    @ParameterizedTest(name = "Critical: Error Test OFF Request encoded with: LED-{0}")
    @MethodSource("offRequestEncodeErrorValues")
    public void ErrorTest_Encode_Off_Request(int led) {
        assertThrows(DecodingException.class, () -> new OffRequest(led));
    }

    /**
     * Test to check Error Message for OFF Request
     * @param led LED to be switched OFF
     */
    @ParameterizedTest(name = "Critical: Test Error Message OFF Request encoded with: LED-{0}")
    @MethodSource("offRequestEncodeErrorValues")
    public void Test_Encode_Error_Message_OFF_Request(int led) {
        DecodingException exception = assertThrows(DecodingException.class, () -> new OffRequest(led));
        assertEquals("Led Nr. muss zwischen 0 und 2 sein!", exception.getMessage());
    }

    static Stream<Arguments> offRequestEncodeErrorValues() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(1000),
                Arguments.of(5),
                Arguments.of(-3),
                Arguments.of(6),
                Arguments.of(7),
                Arguments.of(-5),
                Arguments.of(-1000)
        );
    }

    /*//////////////////////////////////////////////////////////////////////////////*/

    /**
     * Successful Tests for decoding Off Requests
     * @param encoded Encoded String
     * @param expectedLed LED to be switched OFF
     * @throws DecodingException Error String
     */
    @ParameterizedTest(name = "Critical: Test OFF Request decoded with: LED-{0}")
    @MethodSource("offRequestDecodeValues")
    public void Test_Decode_OFF_Request(String encoded, int expectedLed) throws DecodingException {
        assertDoesNotThrow(() -> {
            OffRequest offRequest = new OffRequest(encoded);
            assertEquals(expectedLed, offRequest.getLed());
        });
    }

    static Stream<Arguments> offRequestDecodeValues() {
        return Stream.of(
                Arguments.of("#Off 0!", 0),
                Arguments.of("#Off 1!", 1),
                Arguments.of("#Off 2!", 2)
        );
    }

    /**
     * Error Test for wrong decoded OffRequest
     * @param encoded Error String
     */
    @ParameterizedTest(name = "Critical: ErrorTest OFF Request decoded with: {0}")
    @MethodSource("offRequestDecodeErrorValues")
    public void ErrorTest_Decode_Off_Request(String encoded) {
        assertThrows(DecodingException.class, () -> new OffRequest(encoded));
    }

    /**
     * Test to check Error Message for decoded OFF Request
     * @param encoded error String
     */
    @ParameterizedTest(name = "Critical: Test Error Message OFF Request Decoded with: {0}")
    @MethodSource("offRequestDecodeErrorValues")
    public void Test_Decode_Error_Message_Off_Request(String encoded) {
        DecodingException exception = assertThrows(DecodingException.class, () -> new OffRequest(encoded));
        assertEquals("Invalid encoding format for OffRequest", exception.getMessage());
    }

    static Stream<Arguments> offRequestDecodeErrorValues() {
        return Stream.of(
                Arguments.of("#InvalidEncoding1"),
                Arguments.of("#Off 3!"),
                Arguments.of("#Off -1!"),
                Arguments.of("#Off 4!"),
                Arguments.of("#Off 0 1!"),
                Arguments.of("#InvalidEncoding2"),
                Arguments.of("#Off 5!"),
                Arguments.of("#Off -3!"),
                Arguments.of("#Off 6!"),
                Arguments.of("#Off 0 30!")
        );
    }

}