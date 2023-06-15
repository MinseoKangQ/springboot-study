public class Main {

    public static void main(String[] args) {

        // Encoding을 위한 url
        String url = "www.naver.com/books/it?page=10&size=20&name=spring-boot";

        // DI(외부에서 주입을 받는다)
        // Encoder 생성자의 매개변수를 new UrlEncoder() 로 바꾸면 해당 Encoder 주입받음
        Encoder encoder = new Encoder(new Base64Encoder());
        String result = encoder.encode(url);
        System.out.println(result);

    }

}