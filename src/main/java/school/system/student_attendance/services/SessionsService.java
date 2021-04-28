package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Sessions;

@Service("SessionsService")
public interface SessionsService {
    Iterable<Sessions> getAllSessionsByClassId(int id);
}
