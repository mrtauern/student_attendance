package school.system.student_attendance.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Sessions {
    private int id;
    private Timestamp date;
    private int classIdFk;
    private String sessionCode;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "classId_fk")
    public int getClassIdFk() {
        return classIdFk;
    }

    public void setClassIdFk(int classIdFk) {
        this.classIdFk = classIdFk;
    }

    @Basic
    @Column(name = "sessionCode")
    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sessions sessions = (Sessions) o;

        if (id != sessions.id) return false;
        if (classIdFk != sessions.classIdFk) return false;
        if (date != null ? !date.equals(sessions.date) : sessions.date != null) return false;
        if (sessionCode != null ? !sessionCode.equals(sessions.sessionCode) : sessions.sessionCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + classIdFk;
        result = 31 * result + (sessionCode != null ? sessionCode.hashCode() : 0);
        return result;
    }
}
