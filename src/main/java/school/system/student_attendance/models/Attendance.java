package school.system.student_attendance.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Attendance {
    private int id;
    private byte status;
    private Timestamp time;
    private Byte networkVerified;
    private int sessionIdFk;
    private int studentIdFk;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "networkVerified")
    public Byte getNetworkVerified() {
        return networkVerified;
    }

    public void setNetworkVerified(Byte networkVerified) {
        this.networkVerified = networkVerified;
    }

    @Basic
    @Column(name = "sessionId_fk")
    public int getSessionIdFk() {
        return sessionIdFk;
    }

    public void setSessionIdFk(int sessionIdFk) {
        this.sessionIdFk = sessionIdFk;
    }

    @Basic
    @Column(name = "studentId_fk")
    public int getStudentIdFk() {
        return studentIdFk;
    }

    public void setStudentIdFk(int studentIdFk) {
        this.studentIdFk = studentIdFk;
    }

    @Override
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
    }
}
