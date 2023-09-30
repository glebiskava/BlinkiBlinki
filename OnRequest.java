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

    /**
     * Method modifies an existing Request using an encoded Request or creates a new one when called from the constructor
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
            throw new DecodingException("Encoding Doesn't Match");
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof OnRequest){
            OnRequest onRequest = (OnRequest)obj;
            if (onRequest.getLed() == this.getLed() && onRequest.getTime() == this.getTime()){
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
        return "On Led: "+led+"  Time: "+time;
    }
}