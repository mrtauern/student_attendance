package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Courses;
import school.system.student_attendance.repositories.CoursesRepo;

import java.util.List;
import java.util.Optional;

@Service("CoursesService")
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    CoursesRepo coursesRepo;

    @Override
    public List<Courses> getAllCourses() {
        return (List<Courses>) coursesRepo.findAll();
    }

    @Override
    public Courses getCourseById(int id) {
        //long idLong = Long.valueOf(id);
        Optional<Courses> optional = coursesRepo.findById(id);
        Courses course = null;
        if (optional.isPresent()){
            course = optional.get();
        }else {
            throw new RuntimeException("Class is not found for id ::" + id);
        }
        return course;
    }

    @Override
    public void save(Courses course) {
        coursesRepo.save(course);
    }
}
