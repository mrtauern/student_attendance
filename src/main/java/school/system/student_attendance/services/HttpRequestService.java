package school.system.student_attendance.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("HttpRequestService")
public interface HttpRequestService {
    String getClientIp(HttpServletRequest request);
}
