package school.system.student_attendance.controllers;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import school.system.student_attendance.models.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import school.system.student_attendance.services.SessionsService;
import org.dom4j.rule.Mode;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import school.system.student_attendance.services.*;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.logging.Logger;

@Controller
public class HomeController {

    HomeController() {
    }

    @Autowired
    SessionsService sessionsService;

    @Autowired
    StudentsService studentsService;

    @Autowired
    TeachersService teachersService;

    @Autowired
    ClassService classService;

    @Autowired
    CoursesService coursesService;

    @Autowired
    HttpRequestService httpRequestService;

    @Autowired
    IprangesService iprangesService;


    Logger log = Logger.getLogger(HomeController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LANDING_PAGE = "landing_page";
    private final String DASHBOARD = "dashboard";
    private final String LOGIN = "login";
    private final String CLASSES = "classes";
    private final String CREATECLASSFORM = "createClassForm";
    private final String UPDATECLASSES = "updateClasses";
    private final String STUDENTS = "students";
    private final String CREATESTUDENTFORM = "createStudentForm";
    private final String UPDATESTUDENT = "updateStudents";
    private final String ADDSTUDENTTOCLASS = "addStudentToClass";
    private final String COURSES = "courses";
    private final String ADDCLASSTOCOURSE = "addClassToCourse";
    private final String IPRANGES = "ipRanges";
    private final String NOTALLOWED = "notAllowed";
    private final String EDITIPRANGE = "editIpRangeForm";
    private final String CREATECOURSEFORM = "createCourseForm";
    private final String UPDATECOURSES = "updateCourses";


    @GetMapping("/")
    public String index(HttpSession session, Model model) {

        log.info(sessionsService.checkInCode());

        return INDEX;
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model, HttpServletRequest request) {
        log.info("login called (get)");
        log.info("ipAddress = " + httpRequestService.getClientIp(request));

        String ipAddress = httpRequestService.getClientIp(request);

        boolean isAllowed = iprangesService.isIpAllowed(ipAddress);
        log.info("allowed? " + isAllowed);
        //isAllowed = true;
        model.addAttribute("sessionLogin", session.getAttribute("login"));
        if (isAllowed) {
            return LOGIN;
        } else {
            return NOTALLOWED;
        }
    }

    @PostMapping("/login")
    public String Login(@ModelAttribute Students students, HttpSession session, Model model) {
        log.info("login called (post)");

        Iterable<Students> allStudents = studentsService.findAll();

        Boolean loginCheck = false;

        for (Students s : allStudents) {
            if (s.getEmail().toLowerCase().equals(students.getEmail().toLowerCase())) {
                if (s.getPassword().equals(students.getPassword())) {
                    loginCheck = true;
                    setLogin(session, 's', s.getId());
                }
            }
        }

        if (loginCheck == false) {
            Iterable<Teachers> allTeachers = teachersService.findAll();

            for (Teachers t : allTeachers) {
                if (t.getEmail().toLowerCase().equals(students.getEmail().toLowerCase())) {
                    if (t.getPassword().equals(students.getPassword())) {
                        loginCheck = true;
                        setLogin(session, 't', t.getId());
                    }
                }
            }
        }

        if (loginCheck == false) {
            log.info("Username or password is wrong!");
            return REDIRECT + LOGIN;
        }

        model.addAttribute("Students", "Success!");

        return REDIRECT+DASHBOARD;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        log.info("Called getmapping landing_page");
        if(checkLogin(session) == false) {
            return REDIRECT+LOGIN;
        }else {
            return DASHBOARD;
        }
    }

    @GetMapping("/landing_page")
    public String landing_page(HttpSession session, Model model) {
        log.info("Called getmapping landing_page sessionLogin=" + session.getAttribute("login"));
        model.addAttribute("sessionLogin", session.getAttribute("login"));
        if (checkLogin(session) == false) {
            return REDIRECT + LOGIN;
        } else {
            return LANDING_PAGE;

        }
    }

    private void setLogin(HttpSession session, char type, long id) {
        String user = type + "" + id;
        session.setAttribute("id", id);
        session.setAttribute("login", type);
        session.setAttribute("user", user);
        log.info("login success sessionLogin=" + session.getAttribute("login"));
    }

    private boolean checkLogin(HttpSession session) {
        String user = (String) session.getAttribute("user");
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("log_out")
    public String logout(HttpSession session) {
        log.info("log out called...");

        session.removeAttribute("login");
        session.removeAttribute("id");
        session.removeAttribute("user");

        return REDIRECT+LOGIN;
    }

    @GetMapping("/classes")
    public String classes(HttpSession session, Model model) {
        if (checkLogin(session) == false) {
            return REDIRECT + LOGIN;
        } else {
            model.addAttribute("listClasses", classService.getAllClasses());
            model.addAttribute("sessionLogin",session.getAttribute("login"));
            log.info("  get mapping Classes is called");
            return CLASSES;
        }
    }

    @GetMapping("/createClassForm")
    public String createClassForm(HttpSession session, Model model) {
        //char test = 't';
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            Classes classes = new Classes();
            model.addAttribute("classes", classes);
            model.addAttribute("sessionLogin",session.getAttribute("login"));
            log.info("  createClassForm is called ");
            return CREATECLASSFORM;
        }


    }

    @PostMapping("/saveClass")
    public String saveClass(@ModelAttribute("class") Classes classes, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            classService.saveClass(classes);
            log.info("  PostMapping saveClass is called ");
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return REDIRECT + CLASSES;

        }
    }

    @GetMapping("/showClassUpdateForm/{id}")
    public String showClassUpdateForm(@PathVariable(value = "id") int id, Model model, HttpSession session) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            Classes classes = classService.getClassById(id);
            model.addAttribute("classes", classes);
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            log.info("  GetMapping showClassUpdateForm is called ");

            return UPDATECLASSES;

        }
    }

    @GetMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            this.classService.deleteClassById(id);
            log.info("  GetMapping deleteClass is called ");
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            return REDIRECT + CLASSES;
        }
    }

    //----------
    @GetMapping("/students")
    public String students(HttpSession session, Model model) {
        if (checkLogin(session) == false) {
            log.info("redirect to login if not logged in");
            return REDIRECT + LOGIN;
        } else {

            model.addAttribute("listStudents", studentsService.getAllStudents());
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            log.info("  get mapping Students is called");

            return STUDENTS;
        }
    }

    @GetMapping("/createStudentForm")
    public String createStudentForm(Model model, HttpSession session) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            Students students = new Students();

            model.addAttribute("students", students);
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            log.info("  createStudentForm is called ");

            return CREATESTUDENTFORM;
        }
    }

    @PostMapping("/saveStudent")
    public String saveSTUDENT(@ModelAttribute("student") Students students, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            studentsService.saveStudent(students);
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            log.info("  PostMapping saveClass is called ");

            return REDIRECT + STUDENTS;

        }
    }

    @GetMapping("/showStudentUpdateForm/{id}")
    public String showStudentUpdateForm(@PathVariable(value = "id") int id, Model model, HttpSession session) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            Students students = studentsService.getStudentById(id);
            model.addAttribute("students", students);
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            log.info("  GetMapping showStudentUpdateForm is called ");

            return UPDATESTUDENT;

        }
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            this.studentsService.deleteStudentById(id);
            log.info("  GetMapping deleteClass is called ");
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return REDIRECT + STUDENTS;

        }
    }

    //--- COURSES ---
    @GetMapping("/courses")
    public String courses(HttpSession session, Model model) {
        if (checkLogin(session) == false) {
            log.info("redirect to login if not logged in");
            return REDIRECT + LOGIN;
        } else {

            log.info("Courses getmapping called...");
            model.addAttribute("listCourses", coursesService.getAllCourses());
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return COURSES;
        }
    }

    @GetMapping("/addClassToCourse/{courseId}")
    public String addClassToCourse(@PathVariable(value = "courseId") long courseId, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            log.info("addClassToCourse getmapping called with id=" + courseId);

            Courses course = coursesService.getCourseById((int) courseId);
            List<Classes> classesInCourse = course.getClasses();

            List<Classes> classesNotInCourse = classService.getAllClassesNotInCourse(course);

            CourseClass newCourseClass = new CourseClass();

            model.addAttribute("course", course);
            model.addAttribute("courseId", "" + courseId);
            model.addAttribute("classes", classesInCourse);
            model.addAttribute("allClasses", classesNotInCourse);
            model.addAttribute("newCourseClass", newCourseClass);
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return ADDCLASSTOCOURSE;

        }
    }

    @PostMapping("/addClassToCourse")
    public String saveClassToCourse(@ModelAttribute("courseClass") CourseClass newCourseClass, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            log.info("saving courseClass courseId=" + newCourseClass.getCourseIdFk() + " classId=" + newCourseClass.getClassIdFk());

            Classes newClass = classService.getClassById(newCourseClass.getClassIdFk());
            Courses test = coursesService.getCourseById(newCourseClass.getCourseIdFk());

            newClass.getCourses().add(test);

            classService.saveClass(newClass);
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return REDIRECT + ADDCLASSTOCOURSE + "/" + newCourseClass.getCourseIdFk();

        }
    }

    @GetMapping("removeClassFromCourse/{courseId}/{classId}")
    public String removeClassFromCourse(@PathVariable(value = "courseId") long courseId,
                                        @PathVariable(value = "classId") long classId,
                                        HttpSession session, Model model) {

        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            log.info("removeClassFromCourse getmapping called with courseId=" + courseId + " & classId=" + classId);
            Classes newClass = classService.getClassById((int) classId);
            newClass.getCourses().remove(coursesService.getCourseById((int) courseId));
            classService.saveClass(newClass);
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return REDIRECT + ADDCLASSTOCOURSE + "/" + courseId;

        }
    }

    @GetMapping("/createCourseForm")
    public String createCourse(HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            Courses courses = new Courses();
            model.addAttribute("courses", courses);
            model.addAttribute("sessionLogin",session.getAttribute("login"));
            log.info("  createCourseForm is called ");
            return CREATECOURSEFORM;
        }
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute("course") Courses courses, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            coursesService.save(courses);
            log.info("  PostMapping saveCourse is called ");
            model.addAttribute("sessionLogin",session.getAttribute("login"));


            return REDIRECT + COURSES;

        }
    }

    @GetMapping("/showCourseUpdateForm/{id}")
    public String showCourseUpdateForm(@PathVariable(value = "id") int id, Model model, HttpSession session) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            Courses courses = coursesService.getCourseById(id);
            model.addAttribute("courses", courses);
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            log.info("  GetMapping showCourseUpdateForm is called ");

            return UPDATECOURSES;

        }
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            this.coursesService.deleteById(id);
            log.info("  GetMapping deleteCourse is called ");
            model.addAttribute("sessionLogin",session.getAttribute("login"));

            return REDIRECT + COURSES;
        }
    }

    //------------

    @GetMapping("/addStudentToClass/{classId}")
    public String addStudentToClass(@PathVariable(value = "classId") long classId, HttpSession session, Model model) {
        log.info("addClassToCourse getmapping called with id=" + classId);
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            Classes classes = classService.getClassById((int) classId);
            List<Students> studentInClass = classes.getStudents();

            List<Students> studentsNotInClass = studentsService.getAllStudentsNotInClass(classes);

            StudentClass newStudentClass = new StudentClass();

            //model.addAttribute("listStudents", studentsService.getAllStudents());
            model.addAttribute("classes", classes);
            model.addAttribute("classId", "" + classId);
            model.addAttribute("studentInClass", studentInClass);
            model.addAttribute("studentsNotInClass", studentsNotInClass);
            model.addAttribute("newStudentClass", newStudentClass);
            model.addAttribute("sessionLogin", session.getAttribute("login"));


            return ADDSTUDENTTOCLASS;
        }
    }

    @PostMapping("/addStudentToClass")
    public String addStudentToClass(@ModelAttribute("studentClass") StudentClass newStudentClass, HttpSession session) {
        log.info("saving courseClass courseId=" + newStudentClass.getStudentIdFk() + " classId=" + newStudentClass.getClassIdFk());
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            Classes newClass = classService.getClassById(newStudentClass.getClassIdFk());
            Students test = studentsService.getStudentById(newStudentClass.getStudentIdFk());

            newClass.getStudents().add(test);

            classService.saveClass(newClass);

            return REDIRECT + ADDSTUDENTTOCLASS + "/" + newStudentClass.getClassIdFk();
        }
    }


    @GetMapping("removeStudentFromClass/{studentId}/{classId}")
    public String removeStudentFromClass(@PathVariable(value = "studentId") long studentId,
                                         @PathVariable(value = "classId") long classId,
                                         HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            log.info("removeClassFromCourse getmapping called with courseId=" + studentId + " & classId=" + classId);
            Classes newClass = classService.getClassById((int) classId);
            newClass.getStudents().remove(studentsService.getStudentById((int) studentId));
            classService.saveClass(newClass);
            model.addAttribute("sessionLogin", session.getAttribute("login"));

            return REDIRECT + ADDSTUDENTTOCLASS + "/" + classId;
        }
    }

    //---------------IPRANGES-----------------

    @GetMapping("/ipRanges")
    public String ipRanges(HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {
            Ipranges iprange = new Ipranges();
            model.addAttribute("newIpRange", iprange);
            model.addAttribute("listIpRanges", iprangesService.getAllIpranges());
            model.addAttribute("sessionLogin", session.getAttribute("login"));
            return IPRANGES;
        }
    }

    @GetMapping("/editIpRangeForm/{id}")
    public String editIpRangeForm(@PathVariable(value = "id") long id, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            Ipranges ipRange = iprangesService.getIprangeById((int) id);

            model.addAttribute("ipRange", ipRange);
            model.addAttribute("sessionLogin", session.getAttribute("login"));
            return EDITIPRANGE;
        }
    }

    @PostMapping("/saveIpRange")
    public String saveIpRange(@ModelAttribute("ipRange") Ipranges ipRange, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            log.info("saveIpRange PostMapping called...");
            model.addAttribute("sessionLogin", session.getAttribute("login"));
            iprangesService.save(ipRange);
            return REDIRECT + IPRANGES;
        }
    }

    @GetMapping("/deleteIpRange/{id}")
    public String deleteIpRange(@PathVariable(value= "id") long id, HttpSession session, Model model) {
        if (checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute=" + session.getAttribute("login"));
            return REDIRECT + LOGIN;
        } else {

            iprangesService.deleteIprangeById((int) id);
            model.addAttribute("sessionLogin", session.getAttribute("login"));
            return REDIRECT + IPRANGES;
        }
    }

}
