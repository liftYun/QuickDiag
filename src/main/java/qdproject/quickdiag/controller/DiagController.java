package qdproject.quickdiag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qdproject.quickdiag.service.ChatService;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class DiagController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/diag/askAI")
    public String askAI(){ return "askAI"; }

    @PostMapping("/chat")
    public String runNodeScript(@RequestParam("userInput") String userInput, String scriptOutput, Model model) {
        if(scriptOutput.equals("기타")){
            userInput = "(" + userInput + ") 이런 증상이 있을때 의심되는 질병을 알려줘.";
        }
        else {
            userInput = "너가 답한 질병인 (" + scriptOutput +") 중 에서 (" + userInput + ") 이런 증상이 있을때 해당하는 질병을 알려줘.";
        }
        scriptOutput = chatService.runScriptWithInput(userInput);
        model.addAttribute("scriptOutput", scriptOutput);
        return "askAI";  // 결과를 보여줄 Thymeleaf 템플릿 이름
    }

    @PostMapping("/diag/askAI")
    public String receiveSymptoms(
            @RequestParam(value = "head[]", required = false) String[] headSymptoms,
            @RequestParam(value = "neck[]", required = false) String[] neckSymptoms,
            @RequestParam(value = "shoulder[]", required = false) String[] shoulderSymptoms,
            @RequestParam(value = "chest[]", required = false) String[] chestSymptoms,
            @RequestParam(value = "stomach[]", required = false) String[] stomachSymptoms,
            @RequestParam(value = "ass[]", required = false) String[] assSymptoms,
            @RequestParam(value = "waist[]", required = false) String[] waistSymptoms,
            @RequestParam(value = "arm[]", required = false) String[] armSymptoms,
            @RequestParam(value = "leg[]", required = false) String[] legSymptoms,
            Model model) {

        // 체크박스에서 선택된 값들은 배열로 받아집니다. 필요한 로직을 구현하세요.
        // 예를 들어, 받은 데이터를 로깅하거나 처리하는 코드를 작성할 수 있습니다.
        ArrayList<String> allSymptoms = new ArrayList<>();
        if(headSymptoms == null && neckSymptoms == null && shoulderSymptoms == null && chestSymptoms == null && stomachSymptoms == null
                && assSymptoms == null && waistSymptoms == null && armSymptoms == null && legSymptoms == null) {
            return "Error";
        }
        else {
            if (headSymptoms != null) {
                allSymptoms.add("(머리 증상 : ");
                for (int i = 0; i < headSymptoms.length; i++) {
                    allSymptoms.add(headSymptoms[i]);
                    if( i != headSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (neckSymptoms != null) {
                allSymptoms.add("(목 증상 : ");
                for (int i = 0; i < neckSymptoms.length; i++) {
                    allSymptoms.add(neckSymptoms[i]);
                    if( i != neckSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (shoulderSymptoms != null) {
                allSymptoms.add("(어깨 증상 : ");
                for (int i = 0; i < shoulderSymptoms.length; i++) {
                    allSymptoms.add(shoulderSymptoms[i]);
                    if( i != shoulderSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (chestSymptoms != null) {
                allSymptoms.add("(가슴 증상 : ");
                for (int i = 0; i < chestSymptoms.length; i++) {
                    allSymptoms.add(chestSymptoms[i]);
                    if( i != chestSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (stomachSymptoms != null) {
                allSymptoms.add("(배 증상 : ");
                for (int i = 0; i < stomachSymptoms.length; i++) {
                    allSymptoms.add(stomachSymptoms[i]);
                    if( i != stomachSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (assSymptoms != null) {
                allSymptoms.add("(엉덩이 증상 : ");
                for (int i = 0; i < assSymptoms.length; i++) {
                    allSymptoms.add(assSymptoms[i]);
                    if( i != assSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (waistSymptoms != null) {
                allSymptoms.add("(허리 증상 : ");
                for (int i = 0; i < waistSymptoms.length; i++) {
                    allSymptoms.add(waistSymptoms[i]);
                    if( i != waistSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (armSymptoms != null) {
                allSymptoms.add("(팔 증상 : ");
                for (int i = 0; i < armSymptoms.length; i++) {
                    allSymptoms.add(armSymptoms[i]);
                    if( i != armSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            if (legSymptoms != null) {
                allSymptoms.add("(다리 증상 : ");
                for (int i = 0; i < legSymptoms.length; i++) {
                    allSymptoms.add(legSymptoms[i]);
                    if( i != legSymptoms.length-1){
                        allSymptoms.add(", ");
                    }
                    else {
                        allSymptoms.add(") ");
                    }
                }
            }
            allSymptoms.add(" 증상을 가지고 있는 질병을 알려줘.");
        }

        // 다른 부위의 증상도 비슷하게 처리 가능
        String scriptOutput = chatService.runScriptWithInput(String.valueOf(allSymptoms));
        model.addAttribute("scriptOutput", scriptOutput);

        return "askAI"; // 처리 후 리다이렉트할 페이지의 이름을 반환합니다.
    }

}
