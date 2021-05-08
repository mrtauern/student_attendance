package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Classes;

@Repository
public interface ClassRepository extends JpaRepository<Classes, Integer> {

}
