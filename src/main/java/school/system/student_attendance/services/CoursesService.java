package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Courses;
import school.system.student_attendance.models.Sessions;

import java.util.List;

@Service("CoursesService")
public interface CoursesService {
    List<Courses> findAll();

    Courses findById(int id);

    Courses save(Courses courses);

    void deleteById(int id);

    void delete(Courses courses);
}
