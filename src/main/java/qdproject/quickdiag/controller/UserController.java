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

    @GetMapping("/user/aiDoctor")
    public String doctor() {
        return "aiDoctor";
    } //진단하기로 이동

    @GetMapping("/user/login")
    public String login() {
        return "login";
    } //로그인으로 이동


    @GetMapping("/user/register")
    public String registerForm() {
        return "register";
    } //회원가입으로 이동

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
    } // 회원가입 중 비밀번호 비밀번호 확인이 같은 경우에만 회원가입 진행

    @PostMapping("/user/id_check")
    @ResponseBody // ajax를 쓸 때 컨트롤러에서 반환되는 값을 html에 보내기 위한 용도로 사용된(body에 직접적으로 값을 포함해야한다는 의미)
    public String idCheck(@RequestParam("user_id") String userId) { // ajax로 부터 user_id를 받아서 서비스에 넘기며 로직을 요청한다
        return userService.idCheck(userId);
    }// 이미 있는 아이디의 경우 등록하지 않도록 함

    @PostMapping("/user/phone_check")
    @ResponseBody
    public String phoneCheck(@RequestParam("user_phoneNumber") String userPhoneNumber) {
        return userService.phoneCheck(userPhoneNumber);
    }// 이미 있는 전화번호의 경우 등록하지 않도록 함

}
