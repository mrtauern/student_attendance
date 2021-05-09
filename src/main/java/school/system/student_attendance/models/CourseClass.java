package school.system.student_attendance.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "courseclass", schema = "student_attendance", catalog = "")
public class CourseClass implements Serializable {
    private int courseIdFk;
    private int classIdFk;

    @Id
    @Column(name = "courseid_fk")
    public int getCourseIdFk() {
        return courseIdFk;
    }

    public void setCourseIdFk(int courseIdFk) {
        this.courseIdFk = courseIdFk;
    }

    //@Id
    @Column(name = "classid_fk")
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

        CourseClass that = (CourseClass) o;

        if (courseIdFk != that.courseIdFk) return false;
        if (classIdFk != that.classIdFk) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = courseIdFk;
        result = 31 * result + classIdFk;
        return result;
    }
}
