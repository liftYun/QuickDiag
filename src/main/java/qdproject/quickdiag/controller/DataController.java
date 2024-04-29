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

}