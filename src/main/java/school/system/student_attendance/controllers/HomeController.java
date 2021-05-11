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


    @GetMapping("/classes")
    public String classes(HttpSession session, Model model){
        model.addAttribute("listClasses", classService.getAllClasses());
        log.info("  get mapping Clases is called");
        return CLASSES;
    }

    @GetMapping("/createClassForm")
    public String createClassForm(Model model) {
        Classes classes = new Classes();
        model.addAttribute("classes", classes);
        log.info("  createClassForm is called ");
        return CREATECLASSFORM;
    }
    @PostMapping("/saveClass")
    public String saveClass (@ModelAttribute("class") Classes classes){
        classService.saveClass(classes);
        log.info("  PostMapping saveClass is called ");
        return REDIRECT+CLASSES;
    }
    @GetMapping("/showClassUpdateForm/{id}")
    public String showClassUpdateForm(@PathVariable( value = "id") int id, Model model){
        Classes classes = classService.getClassById(id);
        model.addAttribute("classes", classes);
        log.info("  GetMapping showClassUpdateForm is called ");

        return UPDATECLASSES;
    }

    @GetMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable (value = "id") int id){
        this.classService.deleteClassById(id);
        log.info("  GetMapping deleteClass is called ");
        return REDIRECT+CLASSES;
    }

    //----------
    @GetMapping("/students")
    public String students(HttpSession session, Model model){
        model.addAttribute("listStudents", studentsService.getAllStudents());
        log.info("  get mapping Students is called");
        return STUDENTS;
    }

    @GetMapping("/createStudentForm")
    public String createStudentForm(Model model) {
        Students students = new Students();
        model.addAttribute("students", students);
        log.info("  createStudentForm is called ");
        return CREATESTUDENTFORM;
    }
    @PostMapping("/saveStudent")
    public String saveSTUDENT (@ModelAttribute("student") Students students){
        studentsService.saveStudent(students);
        log.info("  PostMapping saveClass is called ");
        return REDIRECT+STUDENTS;
    }
    @GetMapping("/showStudentUpdateForm/{id}")
    public String showStudentUpdateForm(@PathVariable( value = "id") int id, Model model){
        Students students = studentsService.getStudentById(id);
        model.addAttribute("students", students);
        log.info("  GetMapping showStudentUpdateForm is called ");

        return UPDATESTUDENT;
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable (value = "id") int id){
        this.studentsService.deleteStudentById(id);
        log.info("  GetMapping deleteClass is called ");
        return REDIRECT+STUDENTS;
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
        log.info("addClassToCourse getmapping called with id="+courseId);

        //Courses course = coursesService.getCourseById(1);
        //Classes classe = classService.getClassById(1);

        //List<CourseClass> classesByCourseId = courseClassService.getClassesByCourseId(courseId);
        //List<CourseClass> classesNiC = courseClassService.getAllClassesNotInCourse(courseId);

        //List<Classes> classesInCourse = new ArrayList<>();
        //List<Courses> courseList = new ArrayList<>();
        //nt intCourseId = (int) courseId;
        //Courses course = coursesService.getCourseById(intCourseId);

        //CourseClass newCourseClass = new CourseClass();

        /*
        for (CourseClass c: classesByCourseId) {
            //log.info("classId="+c.getClassIdFk());
            Classes tempClass = new Classes();
            tempClass = classService.getClassById(c.getClassIdFk());
            classesInCourse.add(tempClass);

            /*
            Courses tempCourse = coursesService.getCourseById(c.getCourseIdFk());
            courseList.add(tempCourse);
            */ /*
        }*/


        //List<Classes> classesNotInCourse = new ArrayList<>();
        /*
        for (CourseClass c: classesNiC) {

            Classes tempClass = new Classes();
            tempClass = classService.getClassById(c.getClassIdFk());
            classesNotInCourse.add(tempClass);
        }*/

        //model.addAttribute("course", course);
        //model.addAttribute("classes", "Hello");
        //model.addAttribute("allClasses", classesNotInCourse);
        //model.addAttribute("newCourseClass", newCourseClass);

        return ADDCLASSTOCOURSE;
    }

    @PostMapping("/addClassToCourse")
    public String saveClassToCourse(@ModelAttribute("courseClass") CourseClass courseClass, HttpSession session) {
        log.info("saving courseClass");
        //courseClassService.save(courseClass);
        //CourseClass courseClass1 = new CourseClass();
        //courseClass1.setCourseIdFk(1);
        //courseClass1.setCourseIdFk(3);
        //courseClassService.save(courseClass);
        return REDIRECT+ADDCLASSTOCOURSE;
    }

}
