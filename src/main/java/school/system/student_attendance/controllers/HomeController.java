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
import school.system.student_attendance.services.TeachersService;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
public class HomeController {

    HomeController(){

    }

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;

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

        Boolean loginCheck = false;

        for (Students s: allStudents) {
            if(s.getEmail().toLowerCase().equals(students.getEmail().toLowerCase())){
                if(s.getPassword().equals(students.getPassword())){
                    loginCheck = true;
                    setLogin(session, 's', s.getId());
                }
            }
        }

        if(loginCheck == false) {
            Iterable<Teachers> allTeachers = teachersService.findAll();

            for (Teachers t: allTeachers) {
                if(t.getEmail().toLowerCase().equals(students.getEmail().toLowerCase())) {
                    if(t.getPassword().equals(students.getPassword())) {
                        loginCheck = true;
                        setLogin(session, 't', t.getId());
                    }
                }
            }
        }

        if(loginCheck == false) {
            log.info("Username or password is wrong!");
            return REDIRECT+LOGIN;
        }

        model.addAttribute("Students", "Success!");
        return LANDING_PAGE;
    }

    @GetMapping("/landing_page")
    public String landing_page(HttpSession session) {
        log.info("Called getmapping landing_page");
        if(checkLogin(session) == false) {
            return REDIRECT+LOGIN;
        }else {
            return LANDING_PAGE;
        }
    }

    private void setLogin(HttpSession session, char type, long id){
        String user = type+""+id;
        session.setAttribute("login", user);
        log.info("login success");
    }

    private boolean checkLogin(HttpSession session) {
        String user = (String)session.getAttribute("login");
        if(user != null) {
            return true;
        }else {
            return false;
        }
    }
    //@GetMapping("/landing_page")
    //public String landing_page
}
