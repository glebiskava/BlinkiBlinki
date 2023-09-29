public class BlinkiBlinki {
    abstract class LedRequest{
    }
    public class OnRequest extends LedRequest{
        private int led;
        private int time;
        public OnRequest(int led, int time){
            if(led < 0 || led > 2){
                throw new IllegalArgumentException("Led Nr. muss zwischen 0 und 2 sein");
            }
            this.led = led;
            this.time = time;
        }

        public int getLed() {
            return led;
        }
        public int getTime() {
            return time;
        }
    }

    public class OffRequest extends LedRequest{
        private int led;
        public OffRequest(int led){
            if(led < 0 || led > 2){
                throw new IllegalArgumentException("Led Nr. muss zwischen 0 und 2 sein");
            }
            this.led = led;
        }

        public int getLed() {
            return led;
        }
    }

    public class RgbRequest extends LedRequest{
        private int r;
        private int g;
        private int b;
        public RgbRequest(int r, int g, int b){
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255){
                throw new IllegalArgumentException("Die RGB values müssen zwischen 0 und 255 sein");
            }
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getR() {
            return r;
        }
        public int getG() {
            return g;
        }
        public int getB() {
            return b;
        }
    }


    public String encode(LedRequest request){
        String encoded = "#";
        if (request instanceof OnRequest){
            OnRequest onRequest = (OnRequest)request;
            encoded += "On ";
            encoded += onRequest.getLed()+" ";
            encoded += onRequest.getTime();
        }
        if (request instanceof OffRequest){
            OffRequest offRequest = (OffRequest)request;
            encoded += "Off ";
            encoded += offRequest.getLed();
        }
        if (request instanceof RgbRequest){
            RgbRequest rgbRequest = (RgbRequest)request;
            encoded += "R"+ rgbRequest.getR() + "G" + rgbRequest.getG() + "B" + rgbRequest.getB();
        }
        encoded += "!";
        return encoded;
    }
}
