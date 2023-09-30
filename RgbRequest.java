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
            throw new DecodingException("Encoding Doesn't Match");
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof RgbRequest){
            RgbRequest rgbRequest = (RgbRequest)obj;
            if (rgbRequest.getR() == this.getR() && rgbRequest.getG() == this.getG() && rgbRequest.getB() == this.getB()){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "R"+this.r+"G"+this.g+"B"+this.b;
    }
}
