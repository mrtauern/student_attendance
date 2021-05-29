package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Sessions;
import school.system.student_attendance.repositories.SessionsRepo;

import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

@Service("SessionsService")
public class SessionsServiceImpl implements SessionsService {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final int LEN = 4;
    static SecureRandom rnd = new SecureRandom();

    @Autowired
    SessionsRepo sessionsRepo;

    Logger log = Logger.getLogger(SessionsServiceImpl.class.getName());

    @Override
    public List<Sessions> findAll() {
        return sessionsRepo.findAll();
    }

    @Override
    public Sessions findById(int id) {
        return sessionsRepo.findById(id).get();
    }

    @Override
    public Sessions save(Sessions sessions) {
        return sessionsRepo.save(sessions);
    }

    @Override
    public void deleteById(int id) {
        sessionsRepo.deleteById(id);
    }

    @Override
    public void delete(Sessions sessions) {
        sessionsRepo.delete(sessions);
    }

    @Override
    public String checkInCode() {
        StringBuilder sb = new StringBuilder(LEN);
        for(int i = 0; i < LEN; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
