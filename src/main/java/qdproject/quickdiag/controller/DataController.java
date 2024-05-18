package qdproject.quickdiag.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import qdproject.quickdiag.dto.DataDTO;
import qdproject.quickdiag.service.DataService;



@Controller
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    @GetMapping("/user/userData")
    public String dataForm(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("loginUserId");
        model.addAttribute("user_id", userId);
        return "userData";
    }

    @PostMapping("/user/userData")
    public String insertUserData(@ModelAttribute DataDTO dataDTO){
        dataService.save(dataDTO);
        return "redirect:/";
    }

    @GetMapping("/user/checkUserSelectedData")
    public String checkUserSelectedData(HttpSession session, Model model){
        System.out.println("사용자 설문데이터 조회 시작");
        String loginUserId = (String) session.getAttribute("loginUserId");
        //세션에서 로그인 정보 가져옴.
        DataDTO dataDTO =  dataService.getCheckUserSelectedData(loginUserId);
        System.out.println("반환받은 컨트롤러" + dataDTO);
        System.out.println("사용자 설문데이터 조회 종료");
        model.addAttribute("checkUserSelectedData", dataDTO);
        return "checkUserSelectedData";
    }

    @GetMapping("/user/userData/redirect")
    public String redirectUserData(){
        return "redirect:/user/userData";
    }

}