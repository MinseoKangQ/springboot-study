import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String url = "www.naver.com/books/it?page=10&size=20&name=spring-boot";

        // Base64Encoder 객체 생성
        Base64Encoder base64Encoder = new Base64Encoder();
        String result = base64Encoder.encode(url);
        System.out.println("Base64Encoder : " + result);

        // URLEncoder 객체 생성
        UrlEncoder urlEncoder = new UrlEncoder();
        String urlResult = urlEncoder.encode(url);
        System.out.println("UrlEncoder : " + urlResult);
    }
}