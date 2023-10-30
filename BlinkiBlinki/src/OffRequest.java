public class OffRequest extends LedRequest{
    private int led;
    public OffRequest(int led) throws DecodingException {
        if(led < 0 || led > 2){
            throw new DecodingException("Led Nr. muss zwischen 0 und 2 sein!");
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
        if (encoded.matches("^#Off [0-2]!$")){
            encoded = encoded.replace("#","");
            encoded = encoded.replace("!", "");
            String[] splitted = encoded.split(" ");
            int tmpled = Integer.parseInt(splitted[1]);
            if( tmpled < 0 ||tmpled >2){
                throw new IllegalArgumentException(); //DecodingException mit Text Interval ungueltig
            } else {
                this.led = tmpled;
            }
        } else {
            throw new DecodingException("Invalid encoding format for OffRequest");
        }
    }

    @Override
    public String toString(){
        return "Off Led "+led;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof OffRequest offRequest){
            return offRequest.getLed() == this.getLed();
        } else {
            return false;
        }
    }


}