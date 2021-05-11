package school.system.student_attendance.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Courses;

//@Qualifier("CoursesRepo")
@Repository("CoursesRepo")
public interface CoursesRepo extends CrudRepository<Courses, Integer> {
}
