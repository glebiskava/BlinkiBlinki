import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRgbRequest {

    /**
     * Successful Tests for encoding RGBRequests
     * @param r red value
     * @param g green value
     * @param b blue value
     * @throws DecodingException Error
     */
    @ParameterizedTest(name = "Critical: Test RGB Request encoded with: R-{0} G-{1} B-{2}")
    @MethodSource("rgbRequestEncodeValues")
    public void Test_Encode_RGB_Request(int r, int g, int b) throws DecodingException {
        RgbRequest rgb = new RgbRequest(r, g, b);
        String expectedResult = "#R" + r + "G" + g + "B" + b + "!";
        assertEquals(expectedResult, rgb.encode());
    }

    static Stream<Arguments> rgbRequestEncodeValues() {
        return Stream.of(
            Arguments.of(15, 54, 68),
            Arguments.of(64, 236, 57),
            Arguments.of(255, 0, 255),
            Arguments.of(24, 42, 49),
            Arguments.of(77, 29, 28),
            Arguments.of(37, 0, 1),
            Arguments.of(0, 0, 0),
            Arguments.of(255, 255, 255),
            Arguments.of(50, 50, 50),
            Arguments.of(127, 127, 127)
        );
    }

    /**
     * Error Test for wrong encoded RGB Request Values
     * @param r wrong red value
     * @param g wrong green value
     * @param b wrong blue value
     */
    @ParameterizedTest(name = "Critical: Error Test RGB Request encoded with: R-{0} G-{1} B-{2}")
    @MethodSource("rgbRequestEncodeErrorValues")
    public void ErrorTest_Encode_RGB_Request(int r, int g, int b) {
        assertThrows(IllegalArgumentException.class, () -> new RgbRequest(r, g, b));
    }

    /**
     * Test to check the Error Message for encoded RGB Request values
     * @param r red value
     * @param g green value
     * @param b blue value
     */
    @ParameterizedTest(name = "Critical: Test Error Message RGB Request encoded with: R-{0} G-{1} B-{2}")
    @MethodSource("rgbRequestEncodeErrorValues")
    public void Test_Encode_Error_Message_RGB_Request(int r, int g, int b) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new RgbRequest(r, g, b));
        assertEquals("Die RGB values müssen zwischen 0 und 255 sein", exception.getMessage());
    }

    static Stream<Arguments> rgbRequestEncodeErrorValues() {
        return Stream.of(
            Arguments.of(-1, 0, 0),
            Arguments.of(0, -1, 0),
            Arguments.of(0, 0, -1),
            Arguments.of(256, 0, 0),
            Arguments.of(0, 256, 0),
            Arguments.of(0, 0, 256),
            Arguments.of(-1, -1, -1),
            Arguments.of(256, 256, 256),
            Arguments.of(300, 400, 500),
            Arguments.of(-100, -200, -300)
        );
    }

    /*/////////////////////////////////////////////////////////////////////////////////////////////////*/

    /**
     * Successful Tests for decoding RGB Requests
     * @param encoded encoded String to be decoded
     * @param r red value
     * @param g green value
     * @param b blue value
     * @throws DecodingException Error
     */
    @ParameterizedTest (name = "Critical: Test RGB Request decoded with: {0}")
    @MethodSource("rgbRequestDecodeValues")
    public void Test_Decode_RGB_Request(String encoded, int r, int g, int b) throws DecodingException {
        RgbRequest rgbRequest = new RgbRequest(encoded);
        RgbRequest expectedRgbRequest = new RgbRequest(r, g, b);
        Assertions.assertEquals(expectedRgbRequest.toString(), rgbRequest.toString());
    }

    static Stream<Arguments> rgbRequestDecodeValues() {
        return Stream.of(
            Arguments.of("#R20G10B60!", 20, 10, 60),
            Arguments.of("#R10G120B60!", 10, 120, 60),
            Arguments.of("#R210G130B65!", 210, 130, 65),
            Arguments.of("#R0G0B0!", 0, 0, 0),
            Arguments.of("#R255G255B255!", 255, 255, 255),
            Arguments.of("#R128G64B32!", 128, 64, 32),
            Arguments.of("#R200G100B0!", 200, 100, 0),
            Arguments.of("#R50G50B50!", 50, 50, 50),
            Arguments.of("#R127G127B127!", 127, 127, 127),
            Arguments.of("#R255G0B0!", 255, 0, 0)
        );
    }

    /**
     * Error Test for wrong decoded RGB Request
     * @param encoded Error String
     */
    @ParameterizedTest(name = "Critical: Error Test RGB Request decoded with: {0}")
    @MethodSource("rgbRequestDecodeErrorValues")
    public void ErrorTest_Decode_RGB_Request(String encoded) {
        assertThrows(DecodingException.class, () -> new RgbRequest(encoded));
    }

    /**
     * Test to check Error Message for decoded RGB Request
     * @param encoded encoded String
     */
    @ParameterizedTest(name = "Critical: Test Error Message RGB Request decoded with: {0}")
    @MethodSource("rgbRequestDecodeErrorValues")
    public void Test_Decode_Error_Message_RGB_Request(String encoded) {
        DecodingException exception = assertThrows(DecodingException.class, () -> new RgbRequest(encoded));
        assertEquals("Encoding Doesn't Match", exception.getMessage());
    }

    static Stream<Arguments> rgbRequestDecodeErrorValues() {
        return Stream.of(
            Arguments.of("#InvalidEncoding1"),
            Arguments.of("#R256G100B50!1"),
            Arguments.of("#R-1G0B0!1"),
            Arguments.of("#InvalidEncoding2"),
            Arguments.of("#R256G100B50!2"),
            Arguments.of("#R-1G0B0!2"),
            Arguments.of("#InvalidEncoding3"),
            Arguments.of("#R256G100B50!3"),
            Arguments.of("#R-1G0B0!3"),
            Arguments.of("#InvalidEncoding4")
        );
    }
}
