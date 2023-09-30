public class RgbRequest extends LedRequest {
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

    public RgbRequest(String encoded) throws DecodingException{
        decode(encoded);
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

    public String encode(){
        String encoded = "#R"+ this.getR() + "G" + this.getG() + "B" + this.getB() + "!";
        return encoded;
    }

    public void decode(String encoded) throws DecodingException{
        if (encoded.matches("^#R\\d{1,3}G\\d{1,3}B\\d{1,3}!$")){{}
            encoded = encoded.replace("#", "");
            encoded = encoded.replace("!", "");
            Integer[] out = new Integer[3];
            String[] splitted = encoded.split("R");
            splitted = splitted[1].split("G");
            String[] splitted2 = splitted[1].split("B");
            out[0] = Integer.parseInt(splitted[0]);
            out[1] = Integer.parseInt(splitted2[0]);
            out[2] = Integer.parseInt(splitted2[1]);
            if (0 <= out[0] && out[0] <= 255 && 0 <= out[1] && out[2] <= 255 && 0 <= out[2] && out[2] <= 255){
                this.r = out[0];
                this.g = out[1];
                this.b = out[2];
            } else {
                throw new DecodingException();
            }
        } else  {
            throw new DecodingException();
        }
    }

    public String toString(){
        return "R"+this.r+"G"+this.g+"B"+this.b;
    }
}
