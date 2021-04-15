package school.system.student_attendance.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TeacherClass {
    private int teacherIdFk;
    private int classIdFk;

    @Basic
    @Column(name = "teacherId_fk")
    public int getTeacherIdFk() {
        return teacherIdFk;
    }

    public void setTeacherIdFk(int teacherIdFk) {
        this.teacherIdFk = teacherIdFk;
    }

    @Basic
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
