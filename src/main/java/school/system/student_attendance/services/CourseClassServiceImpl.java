package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.CourseClass;
import school.system.student_attendance.repositories.CourseClassRepo;

import java.util.ArrayList;
import java.util.List;

@Service("CourseClassService")
public class CourseClassServiceImpl implements CourseClassService{
    @Autowired
    CourseClassRepo courseClassRepo;

    @Override
    public List<CourseClass> getClassesByCourseId(long courseId) {
        List<CourseClass> allCourseClasses = (List<CourseClass>) courseClassRepo.findAll();
        List<CourseClass> coursesById = new ArrayList<>();
        for (CourseClass c: allCourseClasses) {
            if(c.getCourseIdFk() == courseId) {
                coursesById.add(c);
            }
        }
        return coursesById;
    }

    /*@Override
    public List<CourseClass> getAllCourseClasses() {
        return (List<CourseClass>) courseClassRepo.findAll();
    }*/
}
