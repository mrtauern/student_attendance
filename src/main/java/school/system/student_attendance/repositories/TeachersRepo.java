package school.system.student_attendance.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Teachers;

//@Qualifier("TeachersRepo")
@Repository("TeachersRepo")
public interface TeachersRepo extends CrudRepository<Teachers, Long> {
}
