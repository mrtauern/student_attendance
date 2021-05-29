package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Sessions;

@Repository("SessionsRepo")
public interface SessionsRepo extends JpaRepository<Sessions, Integer> {
}
