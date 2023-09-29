public class RgbRequest extends LedRequest implements EncodeDecode<RgbRequest> {
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

    public String encode(RgbRequest request){
        String encoded = "#R"+ request.getR() + "G" + request.getG() + "B" + request.getB() + "!";
        return encoded;
    }

    public void decode(String encoded) throws DecodingException{
        if (encoded.matches("^#R\\d{1,3}G\\d{1,3}B\\d{1,3}!$")){{}
            encoded = encoded.replace("#", "");
            encoded = encoded.replace("!", "");
            String[] out = new String[3];
            String[] splitted = encoded.split("R");
            splitted = splitted[1].split("G");
            String[] splitted2 = splitted[1].split("B");
            out[0] = splitted[0];
            out[1] = splitted2[0];
            out[2] = splitted2[1];
            this.r = Integer.parseInt(out[0]);
            this.g = Integer.parseInt(out[1]);
            this.b = Integer.parseInt(out[2]);
        } else  {
            throw new DecodingException();
        }
    }

    public String toString(){
        return "R"+this.r+"G"+this.g+"B"+this.b;
    }

    public static void main(String[] args) throws DecodingException {
        RgbRequest test = new RgbRequest(12, 15, 46);
        test.decode("#R15G64B15!");
        System.out.println(test);
    }
}
