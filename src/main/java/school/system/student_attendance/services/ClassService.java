package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;

import java.util.List;

@Service
public interface ClassService {

    List<Classes> getAllClasses();
    Classes saveClass(Classes classes);
    Classes getClassById(int id);
    void deleteClassById(int id);


}
