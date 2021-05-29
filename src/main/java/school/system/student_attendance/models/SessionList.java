package school.system.student_attendance.models;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessionList {
    private int id;
    private Timestamp date;
    private String courseName;
    private List<String> teacherNames;
    private boolean active;
    private boolean attended;
    private boolean verified;

}
