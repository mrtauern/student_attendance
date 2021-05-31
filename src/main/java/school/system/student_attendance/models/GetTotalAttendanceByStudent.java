package school.system.student_attendance.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GetTotalAttendanceByStudent {

    @Id
    @Column(name = "studentid")
    private int studentId;

    @Basic
    @Column(name = "session")
    private int session;

    @Basic
    @Column(name = "attended")
    private int attended;
}
