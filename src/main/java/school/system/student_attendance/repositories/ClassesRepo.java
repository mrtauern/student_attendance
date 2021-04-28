package school.system.student_attendance.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Students;

import java.util.Optional;

@Qualifier("ClassesRepo")
@Repository
public interface ClassesRepo extends CrudRepository<Classes, Long> {
}
