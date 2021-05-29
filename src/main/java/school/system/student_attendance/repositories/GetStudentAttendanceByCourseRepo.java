package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Attendance;
import school.system.student_attendance.models.GetStudentAttendanceByCourse;

import java.util.List;

@Repository("GetStudentAttendanceByCourseRepo")
public interface GetStudentAttendanceByCourseRepo extends JpaRepository<GetStudentAttendanceByCourse, Integer> {

    @Query(value = "{call get_student_attendance_by_course(:course_id)}", nativeQuery = true)
    public List<GetStudentAttendanceByCourse> getStudentAttendanceByCourse(@Param("course_id") int courseId);
}
