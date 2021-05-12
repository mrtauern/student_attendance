package school.system.student_attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import school.system.student_attendance.models.*;
import school.system.student_attendance.services.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
    private final String ATTEND_SESSION = "attend_session";

    @GetMapping("/sessions_list")
    public String getProductList(Model model, HttpSession httpSession){
        log.info("Sessions list called");

        if(checkLogin(httpSession) == false) {
            return REDIRECT+LOGIN;
        }

        List<Sessions> sessions = sessionsService.findAll();
        List<Attendance> attendances = attendanceService.findAll();

        String sessionId = httpSession.getAttribute("login").toString();
        char sessionType = sessionId.charAt(0);
        int studentId = Integer.parseInt(httpSession.getAttribute("login").toString().substring(1));

        List<SessionList> sessionList = new ArrayList<>();

        int i = 0;
        for(Sessions s: sessions){
            sessionList.add(new SessionList());
            SessionList sl = sessionList.get(i);
            sl.setId(s.getId());
            sl.setDate(s.getDate());
            sl.setCourseName(s.getCourse().getName());
            List<String> teacherNames = new ArrayList<>();
            sl.setTeacherNames(teacherNames);

            for(Teachers t: s.getCourse().getTeachers()){
                if(t.getFirstname() != null) {
                    sl.getTeacherNames().add(t.getFirstname() + " " + t.getLastname());
                }
            }
            for(Classes c: s.getCourse().getClasses()){
                for(Teachers t: c.getTeachers()){
                    if(t.getFirstname() != null) {
                        sl.getTeacherNames().add(t.getFirstname() + " " + t.getLastname());
                    }
                }
            }

            sl.setActive(attendanceService.checkIfActive(s.getDate()));

            if(sessionType == 's'){
                for(Attendance a: attendances){
                    if(a.getSession().getId() == s.getId() && a.getStudent().getId() == studentId){
                        if(a.getStatus() == (byte) 1){
                            sl.setAttended(true);
                        } else {
                            sl.setAttended(false);
                        }
                        if(a.getNetworkVerified() == (byte) 1){
                            sl.setVerified(true);
                        } else {
                            sl.setVerified(false);
                        }
                    }
                }
            }
            i++;
        }

        for(SessionList sl: sessionList){
            log.info(sl.toString());
        }

        model.addAttribute("sessionType", sessionType);
        model.addAttribute("sessions", sessions);
        model.addAttribute("pageTitle", "sessions list");

        return SESSIONS_LIST;

    }

    @GetMapping("/reveal_code/{id}")
    public String getRevealCode(@PathVariable("id") int id, Model model){
        log.info("Reveal code called");

        Sessions session = sessionsService.findById(id);

        model.addAttribute("thisSession", session);
        model.addAttribute("pageTitle", "Reveal code" + session.getCourse().getName() + " - " + formatDate(session.getDate()));

        return REVEAL_CODE;
    }

    @GetMapping("/session_info/{id}")
    public String getSessionInfo(@PathVariable("id") int id, Model model){
        log.info("Sessions info called");

        Sessions session = sessionsService.findById(id);

        /*List<Attendance> attendance = attendanceService.findAll();

        List<AttentionList> attentionList = new ArrayList<>();

        attentionList.add(new AttentionList("", new ArrayList<SessionAttendance>()));

        int listNumber = 0;

        for (Students s: session.getCourse().getStudents()) {
            int attended = 0;
            int networkVerified = 0;
            for (Attendance a: attendance){
                if(a.getSession().getId() == id && a.getStudent().getId() == s.getId()) {
                    if (a.getStatus() == 1) {
                        attended = 1;
                        networkVerified = a.getNetworkVerified();
                    }
                }
            }
            SessionAttendance sessionAttendance = new SessionAttendance(s.getId(), s.getFirstname(), s.getLastname(), s.getEmail(), attended, networkVerified);
            attentionList.get(0).getList().add(sessionAttendance);
        }

        for (Classes c: session.getCourse().getClasses()){
            listNumber++;

            attentionList.add(new AttentionList(c.getClassname(), new ArrayList<SessionAttendance>()));

            for (Students s: c.getStudents()) {
                int attended = 0;
                int networkVerified = 0;
                for (Attendance a: attendance){
                    if(a.getSession().getId() == id && a.getStudent().getId() == s.getId()) {
                        if (a.getStatus() == 1) {
                            attended = 1;
                            networkVerified = a.getNetworkVerified();
                        }
                    }
                }
                SessionAttendance sessionAttendance = new SessionAttendance(s.getId(), s.getFirstname(), s.getLastname(), s.getEmail(), attended, networkVerified);
                attentionList.get(listNumber).getList().add(sessionAttendance);
            }
        }*/

        model.addAttribute("pageTitle", "sessions info " + session.getCourse().getName() + " - " + formatDate(session.getDate()));
        model.addAttribute("thisSession", session);
        model.addAttribute("attentionList", attendanceService.attendanceList(id));

        return SESSION_INFO;
    }

    @GetMapping("/attend_session/{sessionId}")
    public String getAttendSession(@PathVariable("sessionId") int sessionId, Model model, HttpSession httpSession){
        log.info("Attend student called");

        int studentId = Integer.parseInt(httpSession.getAttribute("login").toString().substring(1));
        Students student = studentsService.findById(studentId);
        Sessions session = sessionsService.findById(sessionId);
        Timestamp ts = Timestamp.from(Instant.now());

        boolean studentOnCourse = false;
        for(Students s : session.getCourse().getStudents()){
            if(s == student){
                studentOnCourse = true;
            }
        }

        if(studentOnCourse == false){
            for(Classes c : session.getCourse().getClasses()){
                for(Students s : c.getStudents()){
                    if(s == student){
                        studentOnCourse = true;
                    }
                }
            }
        }

        if (studentOnCourse == false){
            return REDIRECT + SESSIONS_LIST;
        }

        Attendance attendance = new Attendance();

        attendance.setStatus((byte) 1);
        attendance.setTime(ts);
        attendance.setNetworkVerified((byte) 0);
        attendance.setSession(session);
        attendance.setStudent(student);


        attendanceService.save(attendance);

        return ATTEND_SESSION;
    }

    @GetMapping("/attend_student/{studentId}/{sessionId}")
    public String getAttendStudent(@PathVariable("studentId") int studentId, @PathVariable("sessionId") int sessionId, Model model){
        log.info("Attend student called");

        Students student = studentsService.findById(studentId);
        Sessions session = sessionsService.findById(sessionId);
        Timestamp ts = Timestamp.from(Instant.now());

        Attendance attendance = new Attendance();

        attendance.setStatus((byte) 1);
        attendance.setTime(ts);
        attendance.setNetworkVerified((byte) 1);
        attendance.setSession(session);
        attendance.setStudent(student);

        attendanceService.save(attendance);

        return REDIRECT + SESSION_INFO + "/" + sessionId;
    }

    @GetMapping("/unattend_student/{studentId}/{sessionId}")
    public String getUnattendStudent(@PathVariable("studentId") int studentId, @PathVariable("sessionId") int sessionId, Model model, HttpSession httpSession){
        log.info("Attend student called");

        Students student = studentsService.findById(studentId);
        Sessions session = sessionsService.findById(sessionId);
        List<Attendance> attendances = attendanceService.findAll();

        for (Attendance a: attendances) {
            if(a.getStudent() == student && a.getSession() == session){
                attendanceService.delete(a);
            }
        }

        return REDIRECT + SESSION_INFO + "/" + sessionId;
    }

    private boolean checkLogin(HttpSession session) {
        String user = (String)session.getAttribute("login");
        if(user != null) {
            return true;
        }else {
            return false;
        }
    }

}
