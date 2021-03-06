package school.system.student_attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import school.system.student_attendance.models.*;
import school.system.student_attendance.services.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.Date;

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

    @Autowired
    HttpRequestService httpRequestService;

    @Autowired
    IprangesService iprangesService;

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
    private final String CREATE_SESSION = "create_session";
    private final String COURSE_STATISTIC = "course_statistic";
    private final String STUDENT_STATISTIC = "student_statistic";

    @GetMapping("/sessions_list")
    public String getSessionList() {
        log.info("Sessions list called");

        return REDIRECT + SESSIONS_LIST + "/today";
    }

    @GetMapping("/sessions_list/all")
    public String getSessionListAll(Model model, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Sessions list all called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        List<Sessions> sessions = sessionsService.findAll();
        List<Attendance> attendances = attendanceService.findAll();

        for (Attendance a: attendances) {
            log.info(a.getStudent().getFirstname() + " " + a.getStudent().getLastname() + " attended: " + a.getStatus() + " verified: " + a.getNetworkVerified() + " session: " + a.getSession().getId());
        }

        //Set test date
        Timestamp ts = Timestamp.from(Instant.now());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(ts);
        calendar3.add(Calendar.MINUTE, -10);
        Timestamp ts2 = Timestamp.from(calendar3.toInstant());
        sessions.get(1).setDate(ts2);

        //String sessionId = httpSession.getAttribute("login").toString();
        String sessionType = httpSession.getAttribute("login").toString();
        int studentId = Integer.parseInt(httpSession.getAttribute("id").toString());

        List<SessionList> sessionList = new ArrayList<>();

        int i = 0;
        for(Sessions s: sessions){
            boolean studentOnCourse = false;

            for(Students st : s.getCourse().getStudents()){
                if(st.getId() == studentId){
                    studentOnCourse = true;
                }
            }

            for(Classes c: s.getCourse().getClasses()){
                for(Students st: c.getStudents()){
                    if(st.getId() == studentId){
                        studentOnCourse = true;
                    }
                }
            }

            if(sessionType.equals("t") || studentOnCourse == true) {
                sessionList.add(new SessionList());
                SessionList sl = sessionList.get(i);
                sl.setId(s.getId());
                sl.setDate(s.getDate());
                sl.setCourseName(s.getCourse().getName());
                List<String> teacherNames = new ArrayList<>();
                sl.setTeacherNames(teacherNames);

                for (Teachers t : s.getCourse().getTeachers()) {
                    if (t.getFirstname() != null) {
                        sl.getTeacherNames().add(t.getFirstname() + " " + t.getLastname());
                    }
                }
                for (Classes c : s.getCourse().getClasses()) {
                    for (Teachers t : c.getTeachers()) {
                        if (t.getFirstname() != null) {
                            sl.getTeacherNames().add(t.getFirstname() + " " + t.getLastname());
                        }
                    }
                }

                sl.setActive(attendanceService.checkIfActive(s.getDate()));

                log.info("Session type: " + sessionType);

                if (sessionType.equals("s")) {
                    for (Attendance a : attendances) {
                        if (a.getSession().getId() == s.getId() && a.getStudent().getId() == studentId) {
                            if (a.getStatus() == (byte) 1) {
                                sl.setAttended(true);
                            } else {
                                sl.setAttended(false);
                            }
                            if (a.getNetworkVerified() == (byte) 1) {
                                sl.setVerified(true);
                            } else {
                                sl.setVerified(false);
                            }
                        }
                    }
                }
                i++;
            }
        }

        for(SessionList sl: sessionList){
            log.info(sl.toString());
        }

        model.addAttribute("sessionType", sessionType);
        model.addAttribute("studentId", studentId);
        model.addAttribute("sessions", sessions);
        model.addAttribute("sessionList", sessionList);
        model.addAttribute("pageTitle", "sessions list");
        model.addAttribute("showAll", true);

        return SESSIONS_LIST;

    }

    @GetMapping("/sessions_list/today")
    public String getSessionListToday(Model model, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Sessions list today called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        List<Sessions> sessions = sessionsService.findAll();
        List<Attendance> attendances = attendanceService.findAll();

        for (Attendance a: attendances) {
            log.info(a.getStudent().getFirstname() + " " + a.getStudent().getLastname() + " attended: " + a.getStatus() + " verified: " + a.getNetworkVerified() + " session: " + a.getSession().getId());
        }

        //Set test date
        Timestamp ts = Timestamp.from(Instant.now());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(ts);
        calendar3.add(Calendar.MINUTE, -10);
        Timestamp ts2 = Timestamp.from(calendar3.toInstant());
        sessions.get(1).setDate(ts2);

        //String sessionId = httpSession.getAttribute("login").toString();
        String sessionType = httpSession.getAttribute("login").toString();
        int studentId = Integer.parseInt(httpSession.getAttribute("id").toString());

        List<SessionList> sessionList = new ArrayList<>();

        int i = 0;
        for(Sessions s: sessions){
            boolean studentOnCourse = false;
            boolean isToday = false;

            for(Students st : s.getCourse().getStudents()){
                if(st.getId() == studentId){
                    studentOnCourse = true;
                }
            }

            for(Classes c: s.getCourse().getClasses()){
                for(Students st: c.getStudents()){
                    if(st.getId() == studentId){
                        studentOnCourse = true;
                    }
                }
            }

            Timestamp tsToday = Timestamp.from(Instant.now());
            Calendar today = Calendar.getInstance();
            today.setTime(tsToday);

            Calendar cSession = Calendar.getInstance();
            cSession.setTime(s.getDate());

            if(today.get(Calendar.YEAR) == cSession.get(Calendar.YEAR)) {
                if(today.get(Calendar.DAY_OF_YEAR) == cSession.get(Calendar.DAY_OF_YEAR)) {
                    isToday = true;
                }
            }

            if((sessionType.equals("t") || studentOnCourse == true) && isToday == true) {
                sessionList.add(new SessionList());
                SessionList sl = sessionList.get(i);
                sl.setId(s.getId());
                sl.setDate(s.getDate());
                sl.setCourseName(s.getCourse().getName());
                List<String> teacherNames = new ArrayList<>();
                sl.setTeacherNames(teacherNames);

                for (Teachers t : s.getCourse().getTeachers()) {
                    if (t.getFirstname() != null) {
                        sl.getTeacherNames().add(t.getFirstname() + " " + t.getLastname());
                    }
                }
                for (Classes c : s.getCourse().getClasses()) {
                    for (Teachers t : c.getTeachers()) {
                        if (t.getFirstname() != null) {
                            sl.getTeacherNames().add(t.getFirstname() + " " + t.getLastname());
                        }
                    }
                }

                sl.setActive(attendanceService.checkIfActive(s.getDate()));

                log.info("Session type: " + sessionType);

                if (sessionType.equals("s")) {
                    for (Attendance a : attendances) {
                        if (a.getSession().getId() == s.getId() && a.getStudent().getId() == studentId) {
                            if (a.getStatus() == (byte) 1) {
                                sl.setAttended(true);
                            } else {
                                sl.setAttended(false);
                            }
                            if (a.getNetworkVerified() == (byte) 1) {
                                sl.setVerified(true);
                            } else {
                                sl.setVerified(false);
                            }
                        }
                    }
                }
                i++;
            }
        }

        for(SessionList sl: sessionList){
            log.info(sl.toString());
        }

        model.addAttribute("sessionType", sessionType);
        model.addAttribute("studentId", studentId);
        model.addAttribute("sessions", sessions);
        model.addAttribute("sessionList", sessionList);
        model.addAttribute("pageTitle", "sessions list");
        model.addAttribute("showAll", false);

        return SESSIONS_LIST;

    }

    @GetMapping("/create_session")
    public String getCreateSession(Model model, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Create session get mapping called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSIONS_LIST;
        }

        Sessions session = new Sessions();
        List<Courses> courses = coursesService.findAll();

        model.addAttribute("pageTitle", "Create session");
        model.addAttribute("session", session);
        model.addAttribute("courses", courses);

        return CREATE_SESSION;
    }

    @PostMapping("/create_session")
    public String getCreateSession(@RequestParam("hour") int hour, @RequestParam("minute") int minute, @RequestParam("date") String sDate, @RequestParam("course") int courseId, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Create session post mapping called");
        log.info("Date: " + sDate);

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSIONS_LIST;
        }

        Date date = new Date();

        sDate = sDate + " " + hour + ":" +minute;

        log.info("New date: " + sDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = dateFormat.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String sessionCode = sessionsService.checkInCode();
        Sessions session = new Sessions();
        Courses course = coursesService.findById(courseId);

        Timestamp ts=new Timestamp(date.getTime());

        session.setDate(ts);
        session.setSessionCode(sessionCode);
        session.setCourse(course);

        sessionsService.save(session);

        redAt.addFlashAttribute("showMessage", true);
        redAt.addFlashAttribute("messageType", "success");
        redAt.addFlashAttribute("message", "Session successfully created");
        return REDIRECT + SESSIONS_LIST;
    }

    @GetMapping("/reveal_code/{id}")
    public String getRevealCode(@PathVariable("id") int id, Model model, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Reveal code called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();
        int studentId = Integer.parseInt(httpSession.getAttribute("id").toString());

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSIONS_LIST;
        }

        Sessions session = sessionsService.findById(id);

        model.addAttribute("thisSession", session);
        model.addAttribute("pageTitle", "Reveal code" + session.getCourse().getName() + " - " + formatDate(session.getDate()));

        return REVEAL_CODE;
    }

    @GetMapping("/session_info/{id}")
    public String getSessionInfo(@PathVariable("id") int id, Model model, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Sessions info called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();
        int studentId = Integer.parseInt(httpSession.getAttribute("id").toString());

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSIONS_LIST;
        }

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
        model.addAttribute("sessionType", sessionType);
        model.addAttribute("attentionList", attendanceService.attendanceList(id));

        return SESSION_INFO;
    }

    @GetMapping("/attend_session/{sessionId}")
    public String getAttendSession(@PathVariable("sessionId") int sessionId, Model model, HttpSession httpSession, RedirectAttributes redAt, HttpServletRequest request){
        log.info("Attend session getMapping called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        model.addAttribute("sessionId", sessionId);

        return ATTEND_SESSION;
    }

    @PostMapping("/attend_session")
    public String getAttendSession(@RequestParam("sessionId") int sessionId, @RequestParam("sessionCode") String sessionCode, Model model, HttpSession httpSession, RedirectAttributes redAt, HttpServletRequest request){
        log.info("Attend session postMapping called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();
        int studentId = Integer.parseInt(httpSession.getAttribute("id").toString());

        Students student = studentsService.findById(studentId);
        Sessions session = sessionsService.findById(sessionId);

        Timestamp ts = Timestamp.from(Instant.now());

        String ipAddress = httpRequestService.getClientIp(request);
        boolean ipVerified = iprangesService.isIpAllowed(ipAddress);

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
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You can't attend this course");
            return REDIRECT + SESSIONS_LIST;
        }

        if(!session.getSessionCode().equals(sessionCode.toUpperCase())){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "Wrong attendance code");
            return REDIRECT + ATTEND_SESSION + "/" + sessionId;
        }

        Attendance attendance = new Attendance();

        attendance.setStatus((byte) 1);
        attendance.setTime(ts);
        if (ipVerified == true) {
            attendance.setNetworkVerified((byte) 1);
        } else {
            attendance.setNetworkVerified((byte) 0);
        }
        attendance.setSession(session);
        attendance.setStudent(student);


        attendanceService.save(attendance);

        if (ipVerified == true) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "success");
            redAt.addFlashAttribute("message", "You have attended the class");
        } else {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "warning");
            redAt.addFlashAttribute("message", "You have attended the class, but you haven't been verified");
        }
        return REDIRECT+SESSIONS_LIST;
    }

    @GetMapping("/attend_student/{studentId}/{sessionId}")
    public String getAttendStudent(@PathVariable("studentId") int studentId, @PathVariable("sessionId") int sessionId, Model model, HttpSession httpSession, RedirectAttributes redAt){
        log.info("Attend student called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSION_INFO + "/" + sessionId;
        }

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

        redAt.addFlashAttribute("showMessage", true);
        redAt.addFlashAttribute("messageType", "success");
        redAt.addFlashAttribute("message", "Student is successfully attended to this session");

        return REDIRECT + SESSION_INFO + "/" + sessionId;
    }

    @GetMapping("/unattend_student/{studentId}/{sessionId}")
    public String getUnattendStudent(@PathVariable("studentId") int studentId, @PathVariable("sessionId") int sessionId, Model model, RedirectAttributes redAt, HttpSession httpSession){
        log.info("Attend student called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSION_INFO + "/" + sessionId;
        }

        Students student = studentsService.findById(studentId);
        Sessions session = sessionsService.findById(sessionId);
        List<Attendance> attendances = attendanceService.findAll();

        for (Attendance a: attendances) {
            if(a.getStudent() == student && a.getSession() == session){
                attendanceService.delete(a);
            }
        }

        redAt.addFlashAttribute("showMessage", true);
        redAt.addFlashAttribute("messageType", "success");
        redAt.addFlashAttribute("message", "Student is successfully unattended from this session");

        return REDIRECT + SESSION_INFO + "/" + sessionId;
    }

    private boolean checkLogin(HttpSession session) {
        String user = (String)session.getAttribute("user");
        if(user != null) {
            return true;
        }else {
            return false;
        }
    }

    @GetMapping("/course_statistic/{courseId}/{sessionId}")
    public String getCourseStatistic(@PathVariable("courseId") int courseId, @PathVariable("sessionId") int sessionId, Model model, RedirectAttributes redAt, HttpSession httpSession) {
        log.info("Course statistic called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSION_INFO + "/" + sessionId;
        }

        List<GetStudentAttendanceByCourse> getStudentAttendanceByCourseList = attendanceService.getStudentAttendanceByCourse(courseId);

        model.addAttribute("pageTitle", "course statistic ");
        model.addAttribute("attendanceList", getStudentAttendanceByCourseList);
        model.addAttribute("sessionId", sessionId);

        return COURSE_STATISTIC;
    }

    @GetMapping("/student_statistic/{studentId}")
    public String getMyStatistic(@PathVariable("studentId") int studentId, Model model, RedirectAttributes redAt, HttpSession httpSession) {
        log.info("Student statistic for student called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        List<GetCourseAttendanceByStudent> getCourseAttendanceByStudentList = attendanceService.getCourseAttendanceByStudent(studentId);
        GetTotalAttendanceByStudent getTotalAttendanceByStudent = attendanceService.getTotalAttendanceByStudent(studentId);

        for (GetCourseAttendanceByStudent s: getCourseAttendanceByStudentList) {
            log.info(s.toString());
        }

        model.addAttribute("pageTitle", "course statistic ");
        model.addAttribute("attendanceList", getCourseAttendanceByStudentList);
        model.addAttribute("totalAttendance", getTotalAttendanceByStudent);
        model.addAttribute("sessionId", 0);
        model.addAttribute("sessionType", sessionType);

        return STUDENT_STATISTIC;
    }

    @GetMapping("/student_statistic/{studentId}/{sessionId}")
    public String getStudentStatistic(@PathVariable("studentId") int studentId, @PathVariable("sessionId") int sessionId, Model model, RedirectAttributes redAt, HttpSession httpSession) {
        log.info("Student statistic for teacher called");

        if(checkLogin(httpSession) == false) {
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You have to be logged in to see this page");
            return REDIRECT+LOGIN;
        }

        String sessionType = httpSession.getAttribute("login").toString();

        if(!sessionType.equals("t")){
            redAt.addFlashAttribute("showMessage", true);
            redAt.addFlashAttribute("messageType", "error");
            redAt.addFlashAttribute("message", "You don't have permission to do this");
            return REDIRECT + SESSION_INFO + "/" + sessionId;
        }

        List<GetCourseAttendanceByStudent> getCourseAttendanceByStudentList = attendanceService.getCourseAttendanceByStudent(studentId);
        GetTotalAttendanceByStudent getTotalAttendanceByStudent = attendanceService.getTotalAttendanceByStudent(studentId);

        for (GetCourseAttendanceByStudent s: getCourseAttendanceByStudentList) {
            log.info(s.toString());
        }

        model.addAttribute("pageTitle", "course statistic ");
        model.addAttribute("attendanceList", getCourseAttendanceByStudentList);
        model.addAttribute("totalAttendance", getTotalAttendanceByStudent);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("sessionType", sessionType);

        return STUDENT_STATISTIC;
    }
}
