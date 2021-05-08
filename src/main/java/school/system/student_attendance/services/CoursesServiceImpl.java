package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Courses;
import school.system.student_attendance.repositories.CoursesRepo;

import java.util.List;

@Service("CoursesService")
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    CoursesRepo coursesRepo;

    @Override
    public List<Courses> getAllCourses() {
        return (List<Courses>) coursesRepo.findAll();
    }
}
