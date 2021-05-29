package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Attendance;
import school.system.student_attendance.models.AttentionList;

import java.sql.Timestamp;
import java.util.List;

@Service("AttendanceService")
public interface AttendanceService {
    List<Attendance> findAll();

    Attendance findById(int id);

    Attendance save(Attendance attendance);

    void deleteById(int id);

    void delete(Attendance attendance);

    List<AttentionList> attendanceList(int id);

    boolean checkIfActive(Timestamp timestamp);
}
