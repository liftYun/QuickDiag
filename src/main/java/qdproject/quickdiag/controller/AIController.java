package qdproject.quickdiag.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import qdproject.quickdiag.service.DataService;
import qdproject.quickdiag.service.UserService;


@Controller
@RequiredArgsConstructor
public class AIController {

    private final UserService userService;
    private final DataService dataService;

    @PostMapping("")
    public String generateHealthInformation(@RequestParam("userInput") String userInput, Model model) {
        // Flask 서버의 URL 설정
        String flaskUrl = "http://localhost:8000/predict";
        System.out.println("api요청시작");

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON 형식으로 데이터 생성
        String jsonBody = "{\"symptoms\": \"" + userInput + "\"}";

        // HTTP 요청 보내기
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

        // Flask에서 생성한 응답 JSON을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());
            JsonNode predictionsNode = responseJson.get("predictions");

            System.out.println("요청완료");
            System.out.println(predictionsNode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 이동할 View의 이름을 반환
        return "";
    }

}