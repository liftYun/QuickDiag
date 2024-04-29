package qdproject.quickdiag.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qdproject.quickdiag.dto.UserDTO;
import qdproject.quickdiag.service.DataService;
import qdproject.quickdiag.service.UserService;



@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private  final DataService dataService;

    @GetMapping("/user/selectDiag")
    public String selectDiagForm(HttpSession session, Model model){
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId",loginUserId);
        return "selectDiag";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login";
    } //로그인으로 이동

    @GetMapping("/user/loginError")
    public String loginError(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "로그인이 필요한 서비스입니다.");
        return "redirect:/user/login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            session.setAttribute("loginUserId", loginResult.getUser_id());
            System.out.println("로그인 성공: 사용자 " + loginResult.getUser_id() + "으로 로그인하였습니다.");
            return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉트
        } else {
            System.out.println("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
            return "redirect:/user/login";
        }
    }

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

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();//세션무효화
        System.out.println("로그아웃 되었습니다.");
        return "redirect:/";
    }

    @GetMapping("/user/mypage")
    public String myPageForm(HttpSession session, Model model){
        String loginUserId = (String) session.getAttribute("loginUserId");
        //세션에서 로그인 정보를 가져옴
        UserDTO userDTO = userService.mypageForm(loginUserId);
        //서비스를 통해 loginUserId에 해당하는 정보를 dto폼으로 가져옴
        model.addAttribute("mypageUser", userDTO);
        //유저 정보를 dto타입으로 mypage로 보냄
        return "mypage";
    }

    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        //세션에서 로그인 정보를 가져옴
        UserDTO userDTO = userService.mypageForm(loginUserId);
        //서비스를 통해 loginUserId에 해당하는 정보를 dto폼으로 가져옴
        model.addAttribute("mypageUser", userDTO);
        return "update";
    }//업데이트 페이지 띄움

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.updateUser(userDTO);
        return "redirect:/user/mypage";
    }

    @GetMapping("/user/delete")
    public String userDelete(HttpSession session){
        String loginUserId = (String) session.getAttribute("loginUserId");
        //세션에서 로그인 정보를 가져옴
        userService.userDelete(loginUserId);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/user/checkUserData") //main에서 selectDiag로 이동 시 userData입력여부 확인
    public ResponseEntity<String> checkUserData(HttpSession session) {
        String sessionId = (String) session.getAttribute("loginUserId");

        if (dataService.isDataPresent(sessionId)) {
            return ResponseEntity.ok("Data exists");
        } else {
            return ResponseEntity.badRequest().body("사용자 추가 정보를 입력해 주세요");
        }
    }
}