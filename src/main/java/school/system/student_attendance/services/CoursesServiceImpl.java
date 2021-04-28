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
    public List<Courses> findAll() {
        return coursesRepo.findAll();
    }

    @Override
    public Courses findById(int id) {
        return coursesRepo.findById(id).get();
    }

    @Override
    public Courses save(Courses courses) {
        return coursesRepo.save(courses);
    }

    @Override
    public void deleteById(int id) {
        coursesRepo.deleteById(id);
    }

    @Override
    public void delete(Courses courses) {
        coursesRepo.delete(courses);
    }
}
