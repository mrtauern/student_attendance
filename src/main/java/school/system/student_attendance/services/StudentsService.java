package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Students;

@Service("StudentsService")
public interface StudentsService {
    Iterable<Students> findAll();

    Students findById(int id);

    Students save(Students student);

    void deleteById(int id);

    void delete(Students student);
}
