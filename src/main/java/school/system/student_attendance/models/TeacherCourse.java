package school.system.student_attendance.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teacherCourse", schema = "student_attendance", catalog = "")
public class TeacherCourse implements Serializable {
    private int teacherIdFk;
    private int courseIdFk;

    @Id
    @Column(name = "teacherId_fk")
    public int getTeacherIdFk() {
        return teacherIdFk;
    }

    public void setTeacherIdFk(int teacherIdFk) {
        this.teacherIdFk = teacherIdFk;
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

        TeacherCourse that = (TeacherCourse) o;

        if (teacherIdFk != that.teacherIdFk) return false;
        if (courseIdFk != that.courseIdFk) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teacherIdFk;
        result = 31 * result + courseIdFk;
        return result;
    }
}
