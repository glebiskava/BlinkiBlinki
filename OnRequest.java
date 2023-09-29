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

    public OnRequest(String encoded) throws DecodingException{
        decode(encoded);
    }

    public int getLed() {
        return led;
    }
    public int getTime() {
        return time;
    }

    public String encode(){
            String encoded = "#";
            encoded += "On ";
            encoded += this.getLed()+" ";
            encoded += this.getTime();
            return encoded += "!";
    }

    public void decode(String encoded) throws DecodingException {
        if(encoded.matches("^#On [0-2] [1-30]!$")){
            encoded = encoded.replace("#","");
            encoded = encoded.replace("!", "");
            String[] splitted = encoded.split(" ");
            this.led = Integer.parseInt(splitted[1]);
            this.time = Integer.parseInt(splitted[2]);
        } else {
            throw new DecodingException();
        }
    }
}