package school.system.student_attendance.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class StudentClass {
    private int studentIdFk;
    private int classIdFk;

    @Basic
    @Column(name = "studentId_fk")
    public int getStudentIdFk() {
        return studentIdFk;
    }

    public void setStudentIdFk(int studentIdFk) {
        this.studentIdFk = studentIdFk;
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

        StudentClass that = (StudentClass) o;

        if (studentIdFk != that.studentIdFk) return false;
        if (classIdFk != that.classIdFk) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentIdFk;
        result = 31 * result + classIdFk;
        return result;
    }
}
