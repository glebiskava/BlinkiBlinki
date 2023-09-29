public class OffRequest extends LedRequest implements EncodeDecode<OffRequest>{
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

    public String encode(OffRequest onRequest){
        String encoded = "#";
        encoded += "Off ";
        encoded += onRequest.getLed();
        return encoded += "!";
    }

    public void decode(String encoded) throws DecodingException {
        if (encoded.matches("^#Off [0-2]!$")){
            encoded = encoded.replace("#","");
            encoded = encoded.replace("!", "");
            String[] splitted = encoded.split(" ");
        } else {
            throw new DecodingException();
        }
    }
}
