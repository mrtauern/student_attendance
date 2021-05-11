package school.system.student_attendance.controllers;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import school.system.student_attendance.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import school.system.student_attendance.services.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
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
    private ClassService classService;

    @Autowired
    private CoursesService coursesService;

    /*
    @Qualifier(value = "CourseClassService")
    @Autowired
    public CourseClassService courseClassService;
*/

    Logger log = Logger.getLogger(HomeController.class.getName());

    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LANDING_PAGE = "landing_page";
    private final String LOGIN = "login";
    private final String CLASSES = "classes";
    private final String CREATECLASSFORM = "createClassForm";
    private final String UPDATECLASSES = "updateClasses";
    private final String STUDENTS = "students";
    private final String CREATESTUDENTFORM = "createStudentForm";
    private final String UPDATESTUDENT = "updateStudents";
    private final String COURSES = "courses";
    private final String ADDCLASSTOCOURSE = "addClassToCourse";

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
        session.setAttribute("id", id);
        session.setAttribute("login", type);
        session.setAttribute("user", user);
        log.info("login success");
    }

    private boolean checkLogin(HttpSession session) {
        String user = (String)session.getAttribute("user");
        if(user != null) {
            return true;
        }else {
            return false;
        }
    }
    //@GetMapping("/landing_page")
    //public String landing_page


    @GetMapping("/classes")
    public String classes(HttpSession session, Model model){
        if(checkLogin(session) == false) {
            return REDIRECT+LOGIN;
        }else {
            model.addAttribute("listClasses", classService.getAllClasses());
            log.info("  get mapping Classes is called");
            return CLASSES;
        }
    }

    @GetMapping("/createClassForm")
    public String createClassForm(HttpSession session, Model model) {
        //char test = 't';
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {
            Classes classes = new Classes();
            model.addAttribute("classes", classes);
            log.info("  createClassForm is called ");
            return CREATECLASSFORM;
        }


    }
    @PostMapping("/saveClass")
    public String saveClass (@ModelAttribute("class") Classes classes, HttpSession session){
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            classService.saveClass(classes);
            log.info("  PostMapping saveClass is called ");

            return REDIRECT + CLASSES;

        }
    }
    @GetMapping("/showClassUpdateForm/{id}")
    public String showClassUpdateForm(@PathVariable( value = "id") int id, Model model, HttpSession session){
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            Classes classes = classService.getClassById(id);
            model.addAttribute("classes", classes);
            log.info("  GetMapping showClassUpdateForm is called ");

            return UPDATECLASSES;

        }
    }

    @GetMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable (value = "id") int id, HttpSession session){
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {
            this.classService.deleteClassById(id);
            log.info("  GetMapping deleteClass is called ");
            return REDIRECT + CLASSES;
        }
    }

    //----------
    @GetMapping("/students")
    public String students(HttpSession session, Model model){
        if(checkLogin(session) == false) {
            log.info("redirect to login if not logged in");
            return REDIRECT+LOGIN;
        }else {

            model.addAttribute("listStudents", studentsService.getAllStudents());
            log.info("  get mapping Students is called");

            return STUDENTS;
        }
    }

    @GetMapping("/createStudentForm")
    public String createStudentForm(Model model, HttpSession session) {
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            Students students = new Students();

            model.addAttribute("students", students);
            log.info("  createStudentForm is called ");

            return CREATESTUDENTFORM;
        }
    }
    @PostMapping("/saveStudent")
    public String saveSTUDENT (@ModelAttribute("student") Students students, HttpSession session){
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            studentsService.saveStudent(students);
            log.info("  PostMapping saveClass is called ");

            return REDIRECT + STUDENTS;

        }
    }
    @GetMapping("/showStudentUpdateForm/{id}")
    public String showStudentUpdateForm(@PathVariable( value = "id") int id, Model model, HttpSession session){
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            Students students = studentsService.getStudentById(id);
            model.addAttribute("students", students);
            log.info("  GetMapping showStudentUpdateForm is called ");

            return UPDATESTUDENT;

        }
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable (value = "id") int id, HttpSession session){
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            this.studentsService.deleteStudentById(id);
            log.info("  GetMapping deleteClass is called ");

            return REDIRECT + STUDENTS;

        }
    }

    //--- COURSES ---
    @GetMapping("/courses")
    public String courses(HttpSession session, Model model) {
        log.info("Courses getmapping called...");
        model.addAttribute("listCourses", coursesService.getAllCourses());
        return COURSES;
    }

    @GetMapping("/addClassToCourse/{courseId}")
    public String addClassToCourse(@PathVariable (value = "courseId") long courseId, HttpSession session, Model model) {
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

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

            return ADDCLASSTOCOURSE;

        }
    }

    @PostMapping("/addClassToCourse")
    public String saveClassToCourse(@ModelAttribute("courseClass") CourseClass newCourseClass, HttpSession session) {
        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            log.info("saving courseClass courseId=" + newCourseClass.getCourseIdFk() + " classId=" + newCourseClass.getClassIdFk());

            Classes newClass = classService.getClassById(newCourseClass.getClassIdFk());
            Courses test = coursesService.getCourseById(newCourseClass.getCourseIdFk());

            newClass.getCourses().add(test);

            classService.saveClass(newClass);

            return REDIRECT + ADDCLASSTOCOURSE + "/" + newCourseClass.getCourseIdFk();

        }
    }

    @GetMapping("removeClassFromCourse/{courseId}/{classId}")
    public String removeClassFromCourse(@PathVariable(value = "courseId") long courseId,
                                        @PathVariable(value = "classId") long classId,
                                        HttpSession session, Model model) {

        if(checkLogin(session) == false || !session.getAttribute("login").equals('t')) {
            log.info("redirect to login if not logged in or sessionattribute login !=t... sessionAttribute="+session.getAttribute("login"));
            return REDIRECT+LOGIN;
        }else {

            log.info("removeClassFromCourse getmapping called with courseId=" + courseId + " & classId=" + classId);
            Classes newClass = classService.getClassById((int) classId);
            newClass.getCourses().remove(coursesService.getCourseById((int) courseId));
            classService.saveClass(newClass);


            return REDIRECT + ADDCLASSTOCOURSE + "/" + courseId;

        }
    }

}
