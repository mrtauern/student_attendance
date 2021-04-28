package school.system.student_attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import school.system.student_attendance.models.Courses;
import school.system.student_attendance.models.Sessions;
import school.system.student_attendance.models.Teachers;
import school.system.student_attendance.services.CoursesService;
import school.system.student_attendance.services.SessionsService;
import school.system.student_attendance.services.StudentsService;
import school.system.student_attendance.services.TeachersService;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class SessionsController {
    SessionsController(){

    }

    @Autowired
    SessionsService sessionsService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    CoursesService coursesService;

    Logger log = Logger.getLogger(SessionsController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LANDING_PAGE = "landing_page";
    private final String LOGIN = "login";
    private final String SESSIONS_LIST = "sessions_list";

    @GetMapping("/sessions_list")
    public String getProductList(Model model){
        log.info("Sessions list called");

        List<Sessions> sessions = sessionsService.findAll();

        for (Sessions s: sessions) {
            for (Teachers t :s.getCourse().getTeachers()){
                log.info(t.getFirstname());
            }
        }



        model.addAttribute("sessions", sessions);
        model.addAttribute("pageTitle", "sessions list");



        return SESSIONS_LIST;
    }

}
