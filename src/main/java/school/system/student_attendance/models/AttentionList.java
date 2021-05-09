package school.system.student_attendance.models;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AttentionList {
    private String name;
    private ArrayList<SessionAttendance> list;
}
