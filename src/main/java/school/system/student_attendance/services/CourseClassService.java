package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.CourseClass;

import java.util.List;

//@Qualifier("CourseClassService")
@Service("CourseClassService")
public interface CourseClassService {
    List<CourseClass> getClassesByCourseId(long courseId);
    //List<CourseClass> getAllCourseClasses();
}
