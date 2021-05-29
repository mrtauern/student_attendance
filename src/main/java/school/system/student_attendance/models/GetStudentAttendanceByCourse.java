package school.system.student_attendance.models;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GetStudentAttendanceByCourse {
    @Basic
    @Column(name = "courseid")
    private int courseID;

    @Basic
    @Column(name = "coursename")
    private String courseName;

    @Id
    @Column(name = "studentid")
    private int studentId;

    @Basic
    @Column(name = "studentfirstname")
    private String studentFirstname;

    @Basic
    @Column(name = "studentlastname")
    private String studentLastname;

    @Basic
    @Column(name = "session")
    private int session;

    @Basic
    @Column(name = "attended")
    private int attended;
}
