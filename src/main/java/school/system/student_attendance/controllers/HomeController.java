package school.system.student_attendance.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
public class HomeController {

    HomeController(){

    }

    Logger log = Logger.getLogger(HomeController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";

    @GetMapping("/")
    public String index(HttpSession session, Model model){

        return INDEX;
    }
}
