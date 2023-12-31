public class OnRequest extends LedRequest{
    private int led;
    private int time;
    public OnRequest(int led, int time) throws DecodingException {
        if(led < 0 || led > 2 || time < 1 || time > 30){
            throw new DecodingException("Led Nr. muss zwischen 0 und 2 sein und die Zeit zwischen 0 und 29!");
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
        return encoded + "!";
    }

    /**
     * Method modifies an existing Request using an encoded Request or
     * creates a new one when called from the constructor
     *
     * @param encoded the Serialized version of a Request
     * @throws DecodingException when String doesn't match the specific encoding protocol for this request
     * @throws IllegalArgumentException if the String matches the protocol but contains invalid values
     */
    public void decode(String encoded) throws DecodingException {
        if(encoded.matches("^#On [0-2] [0-9]{1,2}!$")){
            encoded = encoded.replace("#","");
            encoded = encoded.replace("!", "");
            String[] splitted = encoded.split(" ");
            int tmpled = Integer.parseInt(splitted[1]);
            int tmptime = Integer.parseInt(splitted[2]);
            if( tmpled < 0 ||tmpled >2 || tmptime < 1 || tmptime >30){
                throw new IllegalArgumentException();
            } else {
                this.led = tmpled;
                this.time = tmptime;
            }
        } else {
            throw new DecodingException("Invalid encoding format for ON request");
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof OnRequest onRequest){
            return onRequest.getLed() == this.getLed() && onRequest.getTime() == this.getTime();
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "On Led: "+led+"  Time: "+time;
    }
}