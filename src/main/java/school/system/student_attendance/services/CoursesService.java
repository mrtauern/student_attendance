package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Courses;

import java.util.List;
import java.util.Optional;

@Service("CoursesService")
public interface CoursesService {
    List<Courses> getAllCourses();

    Courses getCourseById(int courseId);
}
