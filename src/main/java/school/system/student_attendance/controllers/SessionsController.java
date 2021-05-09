package school.system.student_attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import school.system.student_attendance.models.*;
import school.system.student_attendance.services.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    AttendanceService attendanceService;

    public String formatDate(Timestamp date){

        String formattedDate = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date);

        return formattedDate;
    }

    Logger log = Logger.getLogger(SessionsController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LANDING_PAGE = "landing_page";
    private final String LOGIN = "login";
    private final String SESSIONS_LIST = "sessions_list";
    private final String REVEAL_CODE = "reveal_code";
    private final String SESSION_INFO = "session_info";

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

    @GetMapping("/reveal_code/{id}")
    public String getRevealCode(@PathVariable("id") int id, Model model){
        log.info("Reveal code called");

        Sessions session = sessionsService.findById(id);

        model.addAttribute("sessionCode", session.getSessionCode());
        model.addAttribute("pageTitle", "sessions list");

        return REVEAL_CODE;
    }

    @GetMapping("/session_info/{id}")
    public String getSessionInfo(@PathVariable("id") int id, Model model){
        log.info("Sessions info called");

        Sessions session = sessionsService.findById(id);

        List<Attendance> attendance = attendanceService.findAll();

        List<AttentionList> attentionList = new ArrayList<>();

        attentionList.add(new AttentionList("", new ArrayList<SessionAttendance>()));

        int listNumber = 0;

        for (Students s: session.getCourse().getStudents()) {
            int attended = 0;
            for (Attendance a: attendance){
                if(a.getSession().getId() == id && a.getStudent().getId() == s.getId()) {
                    if (a.getStatus() == 1) {
                        attended = 1;
                    }
                }
            }
            SessionAttendance sessionAttendance = new SessionAttendance(s.getId(), s.getFirstname(), s.getLastname(), s.getEmail(), attended);
            attentionList.get(0).getList().add(sessionAttendance);
        }

        for (Classes c: session.getCourse().getClasses()){
            listNumber++;

            attentionList.add(new AttentionList(c.getClassname(), new ArrayList<SessionAttendance>()));

            for (Students s: c.getStudents()) {
                int attended = 0;
                for (Attendance a: attendance){
                    if(a.getSession().getId() == id && a.getStudent().getId() == s.getId()) {
                        if (a.getStatus() == 1) {
                            attended = 1;
                        }
                    }
                }
                SessionAttendance sessionAttendance = new SessionAttendance(s.getId(), s.getFirstname(), s.getLastname(), s.getEmail(), attended);
                attentionList.get(listNumber).getList().add(sessionAttendance);
            }
        }

        model.addAttribute("pageTitle", "sessions info " + session.getCourse().getName() + " - " + formatDate(session.getDate()));
        model.addAttribute("thisSession", session);
        model.addAttribute("attentionList", attentionList);

        return SESSION_INFO;
    }

}
