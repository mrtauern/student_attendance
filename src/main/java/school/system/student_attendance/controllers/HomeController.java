package school.system.student_attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import school.system.student_attendance.services.SessionsService;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
public class HomeController {

    HomeController(){

    }

    @Autowired
    SessionsService sessionsService;

    Logger log = Logger.getLogger(HomeController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";

    @GetMapping("/")
    public String index(HttpSession session, Model model){

        log.info(sessionsService.checkInCode(6));

        return INDEX;
    }
}
