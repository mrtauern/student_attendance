package school.system.student_attendance.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Students;

@Qualifier("StudentsRepo")
@Repository
public interface StudentsRepo extends CrudRepository<Students, Long> {
}
