package school.system.student_attendance.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teachers implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "firstname")
    private String firstname;

    @Basic
    @Column(name = "lastname")
    private String lastname;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "password")
    private String password;

    @ManyToMany(mappedBy = "teachers")
    private List<Courses> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "teachers")
    private List<Classes> classes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teachers teachers = (Teachers) o;

        if (id != teachers.id) return false;
        if (firstname != null ? !firstname.equals(teachers.firstname) : teachers.firstname != null) return false;
        if (lastname != null ? !lastname.equals(teachers.lastname) : teachers.lastname != null) return false;
        if (email != null ? !email.equals(teachers.email) : teachers.email != null) return false;
        if (password != null ? !password.equals(teachers.password) : teachers.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
