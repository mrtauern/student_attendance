package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Courses;

import java.util.List;

@Service("ClassService")
public interface ClassService {

    List<Classes> getAllClasses();
    Classes saveClass(Classes classes);
    Classes getClassById(int id);
    void deleteClassById(int id);
    List<Classes> getAllClassesNotInCourse(Courses course);

}
