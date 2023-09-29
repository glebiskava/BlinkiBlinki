public class OffRequest extends LedRequest{
    private int led;
    public OffRequest(int led){
        if(led < 0 || led > 2){
            throw new IllegalArgumentException("Led Nr. muss zwischen 0 und 2 sein");
        }
        this.led = led;
    }

    public OffRequest(String encoded) throws DecodingException{
        decode(encoded);
    }

    public int getLed() {
        return led;
    }

    public String encode(){
        String encoded = "#";
        encoded += "Off ";
        encoded += this.getLed();
        return encoded += "!";
    }

    public void decode(String encoded) throws DecodingException {
        if (encoded.matches("^#Off [0-2]!$")){
            encoded = encoded.replace("#","");
            encoded = encoded.replace("!", "");
            String[] splitted = encoded.split(" ");
            this.led = Integer.parseInt(splitted[1]);
        } else {
            throw new DecodingException();
        }
    }

}
