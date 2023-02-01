import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Car;
import dto.User;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User();
        user.setName("홍길동");
        user.setAge(10);

        Car car1 = new Car();
        car1.setName("K5");
        car1.setCarNumber("11가 1111");
        car1.setType("sedan");

        Car car2 = new Car();
        car2.setName("Q5");
        car2.setCarNumber("22가 2222");
        car2.setType("SUV");

        List<Car> carList = Arrays.asList(car1, car2);
        user.setCars(carList);

//        System.out.println(user);

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

        JsonNode jsonNode = objectMapper.readTree(json);
        // 하나씩 Parsing
        String _name = jsonNode.get("name").asText(); // name이라는 field로 가져오기
        int _age = jsonNode.get("age").asInt();
        System.out.println("name : " + _name);
        System.out.println("age : " + _age);

        // 배열의 node 표현 및 가져오기(구조를 알고 있는 경우)
        JsonNode cars = jsonNode.get("cars");
        ArrayNode arrayNode = (ArrayNode)cars;

        // 원하는 타입으로 바꾸기
        // object 넣고, 우리가 원하는 타입 넣어서 Mapping
        List<Car> _cars = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>() {});
        System.out.println(_cars);

        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "steve");
        objectNode.put("age", 20);

        System.out.println(objectNode.toPrettyString());
    }
}
