package school.system.student_attendance.services;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.*;

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

    public List<GetCourseAttendanceByStudent> getCourseAttendanceByStudent(int studentId);

    public List<GetStudentAttendanceByCourse> getStudentAttendanceByCourse(int courseId);

    public GetTotalAttendanceByStudent getTotalAttendanceByStudent(int studentId);
}
