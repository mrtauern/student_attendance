package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Attendance;
import school.system.student_attendance.models.Courses;

@Repository("AttendanceRepo")
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
}
