package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.*;

import java.util.List;

@Repository("AttendanceRepo")
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

}
