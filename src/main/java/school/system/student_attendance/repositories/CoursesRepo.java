package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Courses;
import school.system.student_attendance.models.Sessions;

@Repository("CoursesRepo")
public interface CoursesRepo extends JpaRepository<Courses, Integer> {

}
