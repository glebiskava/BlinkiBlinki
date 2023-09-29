public class OnRequest extends LedRequest implements EncodeDecode<OnRequest>{
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

    public String encode(OnRequest onRequest){
        if (onRequest instanceof LedRequest){
            String encoded = "#";
            encoded += "On ";
            encoded += onRequest.getLed()+" ";
            encoded += onRequest.getTime();
            return encoded += "!";
        } else {
            throw new IllegalArgumentException("Die Request muss eine OnRequest sein!");
        }
    }

    public void decode(String encoded) throws DecodingException {
        if(encoded.matches("^#On [0-2] [1-30]!$")){
            encoded = encoded.replace("#","");
            encoded = encoded.replace("!", "");
            String[] splitted = encoded.split(" ");
        } else {
            throw new DecodingException();
        }
    }
}