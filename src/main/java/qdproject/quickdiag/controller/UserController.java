package qdproject.quickdiag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qdproject.quickdiag.dto.UserDTO;
import qdproject.quickdiag.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/doctor")
    public String doctor() {
        return "doctor";
    }

    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/user/register")
    public String registering(@ModelAttribute UserDTO userDTO,
                              @RequestParam("user_password") String userPassword,
                              @RequestParam("user_password_confirm") String userPasswordConfirm,
                              RedirectAttributes redirectAttributes) {
        if(userPassword.equals(userPasswordConfirm)){
            userService.save(userDTO);
        }
        else {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/user/register";
        }
        return "welcome";
    }

    @PostMapping("/user/id_check")
    @ResponseBody // ajax를 쓸 때 컨트롤러에서 반환되는 값을 html에 보내기 위한 용도로 사용된(body에 직접적으로 값을 포함해야한다는 의미)
    public String idCheck(@RequestParam("user_id") String userId) { // ajax로 부터 user_id를 받아서 서비스에 넘기며 로직을 요청한다
        return userService.idCheck(userId);
    }

    @PostMapping("/user/phone_check")
    @ResponseBody
    public String phoneCheck(@RequestParam("user_phoneNumber") String userPhoneNumber) {
        return userService.phoneCheck(userPhoneNumber);
    }



}
