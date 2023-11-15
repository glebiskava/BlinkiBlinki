import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class TestOnRequest {

    /**
     * Successful Tests for encoding OnRequests
     * @param encoded Encoded String
     * @param led switched on LED
     * @param time time for LED
     * @throws DecodingException Wrong encoded String
     */
    @MethodSource("onRequestEncodeValues")
    @ParameterizedTest(name = "Critical: Test ON Request encoded with: LED-{0} TIME-{1}")
    public void Test_Encode_ON_Request(String encoded, int led, int time) throws DecodingException {
        try {
            OnRequest onRequest = new OnRequest(encoded);
            OnRequest expected = new OnRequest(led, time);
            Assertions.assertEquals(onRequest, expected);
        } catch (DecodingException e) {
            fail("Error when decoding, wrong test parameter maybe ?");
        }
    }
    static Stream<Arguments> onRequestEncodeValues() {
        return Stream.of(
            Arguments.of("#On 0 1!", 0, 1),
            Arguments.of("#On 1 1!", 1, 1),
            Arguments.of("#On 2 2!", 2, 2),
            Arguments.of("#On 0 15!", 0, 15),
            Arguments.of("#On 1 20!", 1, 20),
            Arguments.of("#On 2 29!", 2, 29),
            Arguments.of("#On 0 5!", 0, 5),
            Arguments.of("#On 1 10!", 1, 10),
            Arguments.of("#On 2 25!", 2, 25),
            Arguments.of("#On 2 20!", 2, 20)
        );
    }

    /**
     * Error Test for wrong encoded On Request Values
     * @param led LED to be switched ON
     * @param time Time for LED
     */
    @ParameterizedTest(name = "Critical: Error Test ON Request encoded with: LED-{0} TIME-{1}")
    @MethodSource("onRequestEncodeErrorValues")
    public void ErrorTest_Encode_ON_Request(int led, int time) {
        assertThrows(IllegalArgumentException.class, () -> new OnRequest(led, time));
    }

    /**
     * Test to check Error Message for ON Request
     * @param led LED to be switched ON
     * @param time time for LED
     */
    @ParameterizedTest(name = "Critical: Test Error Message ON Request encoded with: LED-{0} TIME-{1}")
    @MethodSource("onRequestEncodeErrorValues")
    public void Test_Encode_Error_Message_ON_Request(int led, int time) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new OnRequest(led, time));
        assertEquals("Led Nr. muss zwischen 0 und 2 sein", exception.getMessage());
    }

    static Stream<Arguments> onRequestEncodeErrorValues() {
        return Stream.of(
                Arguments.of(-1, 10),
                Arguments.of(0, 0),
                Arguments.of(3, 10),
                Arguments.of(1, 31),
                Arguments.of(2, -5),
                Arguments.of(0, 35),
                Arguments.of(4, 25),
                Arguments.of(-3, -15),
                Arguments.of(3, 35),
                Arguments.of(4, 0)
        );
    }

    /*////////////////////////////////////////////////////////////////////////////////////////////*/

    /**
     * Successful Tests for encoding OnRequests
     * @param encoded encoded String
     * @param expectedLed LED to be switched ON
     * @param expectedTime   Time for LED
     * @throws DecodingException Error
     */
    @ParameterizedTest(name = "Critical: Test ON Request decoded with: LED-{0} TIME-{1}")
    @MethodSource("onRequestDecodeValues")
    public void Test_Decode_ON_Request(String encoded, int expectedLed, int expectedTime) throws DecodingException {
        assertDoesNotThrow(() -> {
            OnRequest onRequest = new OnRequest(encoded);
            assertEquals(expectedLed, onRequest.getLed());
            assertEquals(expectedTime, onRequest.getTime());
        });
    }

    static Stream<Arguments> onRequestDecodeValues() {
        return Stream.of(
                Arguments.of("#On 0 1!", 0, 1),
                Arguments.of("#On 1 10!", 1, 10),
                Arguments.of("#On 2 30!", 2, 30),
                Arguments.of("#On 0 2!", 0, 2),
                Arguments.of("#On 2 15!", 2, 15),
                Arguments.of("#On 1 5!", 1, 5),
                Arguments.of("#On 0 30!", 0, 30),
                Arguments.of("#On 2 5!", 2, 5),
                Arguments.of("#On 1 20!", 1, 20),
                Arguments.of("#On 0 15!", 0, 15)
        );
    }

        /**
         * Error Test for wrong decoded OnRequest
         * @param encoded Error String
         */
        @ParameterizedTest(name = "Critical: ErrorTest ON Request decoded with: {0}")
        @MethodSource("onRequestDecodeErrorValues")
        public void ErrorTest_Decode_On_Request(String encoded) {
            assertThrows(Exception.class, () -> new OnRequest(encoded));
        }

        /**
         * Test to check Error Message for decoded ON Request
         * @param encoded error String
         */
        @ParameterizedTest(name = "Critical: Test Error Message ON Request Decoded with: {0}")
        @MethodSource("onRequestDecodeErrorValues")
        public void Test_Decode_Error_Message_On_Request(String encoded) {
            Exception exception = assertThrows(Exception.class, () -> new OnRequest(encoded));
            if(exception.getMessage() == null || exception.getMessage().equals("Encoding Doesn't Match")){
                assertTrue(true, "Error Message was: " + exception.getMessage());
            } else {
                fail("Error Message was: " + exception.getMessage() + "expected was 'Encoding Doesn't Match' or null");
            }
        }

        static Stream<Arguments> onRequestDecodeErrorValues() {
            return Stream.of(
                    Arguments.of("#InvalidEncoding1"),
                    Arguments.of("#On 1 2 3!"),
                    Arguments.of("#Off -1!"),
                    Arguments.of("#On 3 31!"),
                    Arguments.of("#On 10 20!"),
                    Arguments.of("#InvalidEncoding2"),
                    Arguments.of("#On 4 0!"),
                    Arguments.of("#Off 1!"),
                    Arguments.of("#On 2 10 20!"),
                    Arguments.of("#On 0 35!"),
                    Arguments.of("#InvalidEncoding3")
            );
        }

}
