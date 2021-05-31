package school.system.student_attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.GetTotalAttendanceByStudent;

@Repository("GetTotalAttendanceByStudentRepo")
public interface GetTotalAttendanceByStudentRepo extends JpaRepository<GetTotalAttendanceByStudent, Integer> {

    @Query(value = "{call get_total_attendance_by_student(:student_id)}", nativeQuery = true)
    public GetTotalAttendanceByStudent getTotalAttendanceByStudent(@Param("student_id") int studentId);
}
