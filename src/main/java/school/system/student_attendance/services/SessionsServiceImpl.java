package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Sessions;
import school.system.student_attendance.repositories.SessionsRepo;

import java.util.ArrayList;

@Service("SessionsService")
public class SessionsServiceImpl implements SessionsService {
    @Autowired
    SessionsRepo sessionsRepo;

    @Override
    public Iterable<Sessions> getAllSessionsByClassId(int id) {
        Iterable<Sessions> sessions = sessionsRepo.findAll();
        ArrayList<Sessions> sessionsByClassId = new ArrayList<Sessions>();

        for (Sessions s: sessions) {
            if(s.getCourseIdFk() == id) {
                sessionsByClassId.add(s);
            }
        }
        return sessionsByClassId;
    }
}
