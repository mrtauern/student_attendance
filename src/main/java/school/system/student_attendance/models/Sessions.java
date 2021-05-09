package school.system.student_attendance.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions", schema = "student_attendance", catalog = "")
public class Sessions implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "date")
    private Timestamp date;

    //@Basic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseid_fk")
    private Courses course;

    @Basic
    @Column(name = "sessioncode")
    private String sessionCode;

    @OneToMany(mappedBy="session")
    private Set<Attendance> attendances;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sessions sessions = (Sessions) o;

        if (id != sessions.id) return false;
        if (courseIdFk != sessions.courseIdFk) return false;
        if (date != null ? !date.equals(sessions.date) : sessions.date != null) return false;
        if (sessionCode != null ? !sessionCode.equals(sessions.sessionCode) : sessions.sessionCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + courseIdFk;
        result = 31 * result + (sessionCode != null ? sessionCode.hashCode() : 0);
        return result;
    }*/
}
