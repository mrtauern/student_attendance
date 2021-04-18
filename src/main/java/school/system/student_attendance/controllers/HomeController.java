package school.system.student_attendance.controllers;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import school.system.student_attendance.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import school.system.student_attendance.services.StudentsService;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
public class HomeController {

    HomeController(){

    }

    @Autowired
    StudentsService studentsService;

    Logger log = Logger.getLogger(HomeController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LANDING_PAGE = "landing_page";
    private final String LOGIN = "login";

    @GetMapping("/")
    public String index(HttpSession session, Model model){

        return INDEX;
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        log.info("login called (get)");

        return LOGIN;
    }

    @PostMapping("/login")
    public String Login(@ModelAttribute Students students, HttpSession session, Model model) {
        log.info("login called (post)");

        Iterable<Students> allStudents = studentsService.findAll();

        model.addAttribute("students", allStudents);
        //for (Students student: allStudents) {

        //}

        return LANDING_PAGE;
    }

    //@GetMapping("/landing_page")
    //public String landing_page
}
