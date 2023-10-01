final class Decoder {
    /**
     * Tries each method for decoding and waits for a DecodingException to try another method.
     *
     * @param encoded the encoded Request as a String
     * @return a child of LedRequest corresponding to the encoded String
     * @throws DecodingException if the String doesn't fit any of the requests or has wrong values;
     */
    static public LedRequest decode(String encoded) throws DecodingException{
        try {
            return new OnRequest(encoded);
        } catch (DecodingException ignored) {
        }
        try {
            return new OffRequest(encoded);
        } catch (DecodingException ignored) {
        }
        try {
            return new RgbRequest(encoded);
        } catch (DecodingException ignored) {
        }
        throw new DecodingException("Data Cannot be Decoded as it doesn't fit any criteria");
    }
}