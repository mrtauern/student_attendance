package school.system.student_attendance.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teacherClass", schema = "student_attendance", catalog = "")
public class TeacherClass implements Serializable {
    private int teacherIdFk;
    private int classIdFk;

    @Id
    @Column(name = "teacherId_fk")
    public int getTeacherIdFk() {
        return teacherIdFk;
    }

    public void setTeacherIdFk(int teacherIdFk) {
        this.teacherIdFk = teacherIdFk;
    }

    @Id
    @Column(name = "classId_fk")
    public int getClassIdFk() {
        return classIdFk;
    }

    public void setClassIdFk(int classIdFk) {
        this.classIdFk = classIdFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherClass that = (TeacherClass) o;

        if (teacherIdFk != that.teacherIdFk) return false;
        if (classIdFk != that.classIdFk) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teacherIdFk;
        result = 31 * result + classIdFk;
        return result;
    }
}
