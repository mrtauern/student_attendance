package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Students;

import java.util.List;

@Service("StudentsService")
public interface StudentsService {
    Iterable<Students> findAll();

    Students findById(int id);

    Students save(Students student);

    void deleteById(int id);

    void delete(Students student);

    List<Students> getAllStudents();
    Students saveStudent(Students students);
    Students getStudentById(int id);
    void deleteStudentById(int id);
    List<Students> getAllStudentsNotInClass(Classes classes);
}
