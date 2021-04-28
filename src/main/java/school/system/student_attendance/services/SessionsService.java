package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Sessions;

import java.util.List;

@Service("SessionsService")
public interface SessionsService {
    List<Sessions> findAll();

    Sessions findById(int id);

    Sessions save(Sessions sessions);

    void deleteById(int id);

    void delete(Sessions sessions);

    String checkInCode();
}
