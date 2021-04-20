package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Teachers;

@Service("TeachersService")
public interface TeachersService {
    Iterable<Teachers> findAll();
}
