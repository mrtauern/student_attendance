package school.system.student_attendance.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Classes {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "classname")
    private String classname;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(name = "courseclass",
            joinColumns = @JoinColumn(name = "classid_fk"),
            inverseJoinColumns = @JoinColumn(name = "courseid_fk")
    )
    private List<Courses> courses = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(name = "teacherclass",
            joinColumns = @JoinColumn(name = "classid_fk"),
            inverseJoinColumns = @JoinColumn(name = "teacherid_fk")
    )
    private List<Teachers> teachers = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(name = "studentclass",
            joinColumns = @JoinColumn(name = "classid_fk"),
            inverseJoinColumns = @JoinColumn(name = "studentid_fk")
    )
    private List<Students> students = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classes classes = (Classes) o;

        if (id != classes.id) return false;
        if (classname != null ? !classname.equals(classes.classname) : classes.classname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        return result;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }
}
