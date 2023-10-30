public class RgbRequest extends LedRequest {
    private int r;
    private int g;
    private int b;
    public RgbRequest(int r, int g, int b) throws DecodingException {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255){
            throw new DecodingException("Die RGB Werte müssen zwischen 0 und 255 sein");
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
        return "#R"+ this.getR() + "G" + this.getG() + "B" + this.getB() + "!";
    }

    /**
     * Method modifies an existing Request using an encoded Request or creates a new one when called from the constructor
     *
     * @param encoded the Serialized version of a Request
     * @throws DecodingException when String doesn't match the specific encoding protocol for this request
     * @throws IllegalArgumentException if the String matches the protocol but contains invalid values
     */
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
            if (0 <= out[0] && out[0] <= 255 && 0 <= out[1] && out[2] <= 255 && 0 <= out[2]){
                this.r = out[0];
                this.g = out[1];
                this.b = out[2];
            } else {
                throw new DecodingException();
            }
        } else  {
            throw new DecodingException("Invalid encoding format for RGB request");
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof RgbRequest rgbRequest){
            return rgbRequest.getR() == this.getR() && rgbRequest.getG() == this.getG() && rgbRequest.getB() == this.getB();
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "R"+this.r+"G"+this.g+"B"+this.b;
    }
}