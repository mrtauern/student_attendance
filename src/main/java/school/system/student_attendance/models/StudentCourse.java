package school.system.student_attendance.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studentCourse", schema = "student_attendance", catalog = "")
public class StudentCourse implements Serializable {
    private int studentIdFk;
    private int courseIdFk;

    @Id
    @Column(name = "studentId_fk")
    public int getStudentIdFk() {
        return studentIdFk;
    }

    public void setStudentIdFk(int studentIdFk) {
        this.studentIdFk = studentIdFk;
    }

    @Id
    @Column(name = "courseId_fk")
    public int getCourseIdFk() {
        return courseIdFk;
    }

    public void setCourseIdFk(int courseIdFk) {
        this.courseIdFk = courseIdFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourse that = (StudentCourse) o;

        if (studentIdFk != that.studentIdFk) return false;
        if (courseIdFk != that.courseIdFk) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentIdFk;
        result = 31 * result + courseIdFk;
        return result;
    }
}
