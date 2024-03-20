package qdproject.quickdiag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DiagController {
    @GetMapping("/diag/selectDiag")
    public String selectDiag(){ return "selectDiag"; }//증상선택으로 이동
}
