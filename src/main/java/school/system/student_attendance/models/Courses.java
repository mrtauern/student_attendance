package school.system.student_attendance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courses {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="course")
    private Set<Sessions> sessions;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(name = "teachercourse",
            joinColumns = @JoinColumn(name = "courseid_fk"),
            inverseJoinColumns = @JoinColumn(name = "teacherid_fk")
    )
    private List<Teachers> teachers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Courses courses = (Courses) o;

        if (id != courses.id) return false;
        if (name != null ? !name.equals(courses.name) : courses.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
