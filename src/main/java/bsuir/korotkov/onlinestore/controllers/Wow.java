package bsuir.korotkov.onlinestore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RestController
public class Wow {
    @GetMapping("/admin")
    public Map<String, String> wow(){
        return Map.of("admin", "sdf");
    }

    @GetMapping("/admin/ad")
    public Map<String, String> wow4(){
        return Map.of("admin2", "sdf");
    }

    @GetMapping("/basket")
    public Map<String, String> wow2(){
        return Map.of("user admin", "sdf");
    }

    @GetMapping("/wow")
    public Map<String, String> wow3(){
        return Map.of("all", "sdf");
    }
}