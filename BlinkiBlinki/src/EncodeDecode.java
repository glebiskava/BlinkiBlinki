public interface EncodeDecode{
    String encode();
    void decode(String encoded) throws DecodingException;
}