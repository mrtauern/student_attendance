package school.system.student_attendance.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attendance {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "status")
    private byte status;

    @Basic
    @Column(name = "time")
    private Timestamp time;

    @Basic
    @Column(name = "networkverified")
    private Byte networkVerified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionid_fk")
    private Sessions session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentid_fk")
    private Students student;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendance that = (Attendance) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (sessionIdFk != that.sessionIdFk) return false;
        if (studentIdFk != that.studentIdFk) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (networkVerified != null ? !networkVerified.equals(that.networkVerified) : that.networkVerified != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) status;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (networkVerified != null ? networkVerified.hashCode() : 0);
        result = 31 * result + sessionIdFk;
        result = 31 * result + studentIdFk;
        return result;
    }*/
}
