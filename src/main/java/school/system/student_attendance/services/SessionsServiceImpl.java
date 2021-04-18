package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Sessions;

import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

@Service("SessionsService")
public class SessionsServiceImpl implements SessionsService {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();

    Logger log = Logger.getLogger(SessionsServiceImpl.class.getName());

    @Override
    public List<Sessions> findAll() {
        return null;
    }

    @Override
    public Sessions findById(int id) {
        return null;
    }

    @Override
    public Sessions save(Sessions sessions) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void delete(Sessions sessions) {

    }

    @Override
    public String checkInCode(int len) {
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
