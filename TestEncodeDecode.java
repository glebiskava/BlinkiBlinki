import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class TestEncodeDecode {
    @ParameterizedTest(name = "Testing Different Decodes")
    @CsvSource(value = {
            "15, 30, 10",
            "94, 16, 48",
            "46, 12, 14"
    })
    @Test
    public void test_Decode_RGB(int a, int b, int c){
        RgbRequest rgbRequest = new RgbRequest(a,b,c);
        Assertions.assertEquals(rgbRequest.encode(), "#R"+a+"G"+b+"B"+c+"!");
    }
}
