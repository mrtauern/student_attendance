package school.system.student_attendance.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.CourseClass;

//@Qualifier("CourseClassRepo")
@Repository("CourseClassRepo")
public interface CourseClassRepo extends CrudRepository<CourseClass, Long> {
}
