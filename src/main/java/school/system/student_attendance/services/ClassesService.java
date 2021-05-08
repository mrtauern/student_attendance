package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;

import java.util.List;
import java.util.Optional;

@Service("ClassesService")
public interface ClassesService {
    Iterable<Classes> findAll();

    void save(Classes classname);

    Optional<Classes> findById(long id);

    List<Classes> getAllClasses();
    Classes saveClass(Classes classes);
    Classes getClassById(long id);
    void deleteClassById(long id);
}
