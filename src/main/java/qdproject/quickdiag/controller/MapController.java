package qdproject.quickdiag.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qdproject.quickdiag.service.UserService;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final UserService userService;
    @GetMapping("/user/map")
    public String map() {
        return "map";
    }

}