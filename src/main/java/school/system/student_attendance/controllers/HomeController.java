package school.system.student_attendance.controllers;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import school.system.student_attendance.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import school.system.student_attendance.services.ClassesService;
import school.system.student_attendance.services.SessionsService;
import school.system.student_attendance.services.StudentsService;
import school.system.student_attendance.services.TeachersService;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class HomeController {

    HomeController(){

    }

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    ClassesService classesService;

    @Autowired
    SessionsService sessionsService;

    Logger log = Logger.getLogger(HomeController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LANDING_PAGE = "landing_page";
    private final String LOGIN = "login";
    private final String CREATE_CLASS = "create_class";
    private final String CLASS_ADD_SESSIONS = "class_add_session";

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

    @GetMapping("/create_class")
    private String create_class(HttpSession session) {
        log.info("Called getmapping create_class");
        if(checkLogin(session) == false) {
            return REDIRECT+LOGIN;
        }else {
            return CREATE_CLASS;
        }
    }

    @PostMapping("/create_class")
    private String create_class(@ModelAttribute Classes classname, HttpSession session, Model model) {
        log.info("Called postmapping create_class");

        if(checkLogin(session) == false) {
            return REDIRECT+LOGIN;
        }else {
            Iterable<Classes> allClasses = classesService.findAll();
            boolean duplicate = false;

            for (Classes c: allClasses) {
                if(c.getClassname() == classname.getClassname()) {
                    duplicate = true;
                }
            }

            if(duplicate == false) {
                classesService.save(classname);
            }
            //else {
            //    return "some error";
            //}

            return CREATE_CLASS;
        }
    }

    @GetMapping("/class_add_session/{id}")
    private String class_add_sessions(HttpSession session, Model model, @PathVariable("id") int id) {
        if(checkLogin(session) == false) {
            return REDIRECT+LOGIN;
        }else {
            Iterable<Sessions> allSessionsByClassId = sessionsService.getAllSessionsByClassId(id);
            Optional<Classes> selectedClass = classesService.findById(id);

            model.addAttribute("classId", id);
            model.addAttribute("sessions", allSessionsByClassId);
            model.addAttribute("class", selectedClass);

            return CLASS_ADD_SESSIONS;
        }
    }
}
