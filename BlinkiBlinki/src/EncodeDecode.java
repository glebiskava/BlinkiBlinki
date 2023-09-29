public interface EncodeDecode<T extends LedRequest>{
    String encode(T request);
    void decode(String string) throws DecodingException;
}