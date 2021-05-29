package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.GetCourseAttendanceByStudent;

import java.util.List;

@Repository("GetCourseAttendanceByStudentRepo")
public interface GetCourseAttendanceByStudentRepo extends JpaRepository<GetCourseAttendanceByStudent, Integer> {

    @Query(value = "{call get_course_attendance_by_student(:student_id)}", nativeQuery = true)
    public List<GetCourseAttendanceByStudent> getCourseAttendanceByStudent(@Param("student_id") int studentId);
}
