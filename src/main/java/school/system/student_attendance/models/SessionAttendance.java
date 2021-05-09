package school.system.student_attendance.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessionAttendance {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int attendance;

}
