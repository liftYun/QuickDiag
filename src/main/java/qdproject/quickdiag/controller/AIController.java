package qdproject.quickdiag.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qdproject.quickdiag.dto.DataDTO;
import qdproject.quickdiag.dto.UserDTO;
import qdproject.quickdiag.service.ChatService;
import qdproject.quickdiag.service.ChatService2;
import qdproject.quickdiag.service.DataService;
import qdproject.quickdiag.service.UserService;


@Controller
@RequiredArgsConstructor
public class AIController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatService2 chatService2;
    private final UserService userService;
    private final DataService dataService;

    @PostMapping("/diag/askAI") //RequestParam 등 추가, Gemini input을 위한 전처리 기능 구현 _liftyun
    public String generateHealthInformation(@RequestParam(value = "head[]", required = false) String[] headArray,
                                            @RequestParam(value = "eye[]", required = false) String[] eyeArray,
                                            @RequestParam(value = "nose[]", required = false) String[] noseArray,
                                            @RequestParam(value = "mouse[]", required = false) String[] mouseArray,
                                            @RequestParam(value = "ear[]", required = false) String[] earArray,
                                            @RequestParam(value = "neck[]", required = false) String[] neckArray,
                                            @RequestParam(value = "chest[]", required = false) String[] chestArray,
                                            @RequestParam(value = "gas[]", required = false) String[] gasArray,
                                            @RequestParam(value = "etc[]", required = false) String[] etcArray,
                                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // Flask 서버의 URL 설정
        String flaskUrl = "http://localhost:8000/predict";
        System.out.println("api요청시작");

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //증상을 받아와서 하나의 String으로 재처리
        String userInput = "";
        if(headArray == null && eyeArray == null && noseArray == null && mouseArray == null && earArray == null
                && neckArray == null && chestArray == null && gasArray == null && etcArray == null) {
            redirectAttributes.addFlashAttribute("error", "적어도 하나의 증상을 선택해야 합니다.");
            return "redirect:/user/selectDiag";
        } else {
            if(headArray != null){
                for(int i = 0; headArray.length >= i; i++){
                    userInput = String.join(",",headArray);
                }
            }
            if(eyeArray != null){
                for(int i = 0; eyeArray.length >= i; i++){
                    userInput = String.join(",",eyeArray);
                }
            }
            if(noseArray != null){
                for(int i = 0; noseArray.length >= i; i++){
                    userInput = String.join(",",noseArray);
                }
            }
            if(mouseArray != null){
                for(int i = 0; mouseArray.length >= i; i++){
                    userInput = String.join(",",mouseArray);
                }
            }
            if(earArray != null){
                for(int i = 0; earArray.length >= i; i++){
                    userInput = String.join(",",earArray);
                }
            }
            if(neckArray != null){
                for(int i = 0; neckArray.length >= i; i++){
                    userInput = String.join(",",neckArray);
                }
            }
            if(chestArray != null){
                for(int i = 0; chestArray.length >= i; i++){
                    userInput = String.join(",",chestArray);
                }
            }
            if(gasArray != null){
                for(int i = 0; gasArray.length >= i; i++){
                    userInput = String.join(",",gasArray);
                }
            }
            if(etcArray != null){
                for(int i = 0; etcArray.length >= i; i++){
                    userInput = String.join(",",etcArray);
                }
            }
        }
        // JSON 형식으로 데이터 생성
        String jsonBody = "{\"symptoms\": \"" + userInput + "\"}";
        System.out.println(jsonBody);

        // HTTP 요청 보내기
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

        // Flask에서 생성한 응답 JSON을 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());
            JsonNode predictionsNode = responseJson.get("predictions");
            // 로그인 세션 정보를 불러와 DB에서 정보 추출
            String loginUserId = (String) session.getAttribute("loginUserId");
            DataDTO dataDTO = dataService.userDataFrom(loginUserId);
            //환자 나이 정보 출력을 위함
            UserDTO userDTO = userService.mypageForm(loginUserId);
            String text = "해당 질병들 중에서 환자의 상태가 "
                    + "나이는 " + userDTO.getUser_birthday()
                    + ", 가족력에 "+ dataDTO.getUser_family()
                    + "이 있고, 궐련형 담배는 " + dataDTO.getUser_smoking()
                    + ", 궐련형 전자담배는 " + dataDTO.getUser_esmoking()
                    + ", 음주 습관은 " + dataDTO.getUser_drinking()
                    + ", 운동은 " + dataDTO.getUser_workout()
                    + "이라 할 때 어느 질병의 확률이 가장 높은지 다른 설명 없이 병명만 보여줘";
            //Gemini input을 위한 Json + String 처리.
            String ask = predictionsNode + text;
            System.out.println("요청완료");
            System.out.println(predictionsNode);
            System.out.println(text);
//            String scriptOutput = chatService.runScriptWithInput(String.valueOf(predictionsNode));
            String scriptOutput = chatService.runScriptWithInput(ask);
            model.addAttribute("scriptOutput",scriptOutput);

            String ask2 = scriptOutput + "1순위 질병은 어느 과를 가야할까?";
            System.out.println(ask2);
            String scriptOutput2 = chatService2.runScriptWithInput(ask2);
            model.addAttribute("scriptOutput2",scriptOutput2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 이동할 View의 이름을 반환
        return "askAI";
    }

}