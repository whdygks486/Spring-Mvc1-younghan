package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.*;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        //sessionId 생성하고 , 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);
        //쿠키생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    //세션 조회
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());


    }
        //세션 만료
                public  void expire(HttpServletRequest request){
                    Cookie sessionCookie = findCookie(request , SESSION_COOKIE_NAME);
                    if (sessionCookie != null){
                        sessionStore.remove(sessionCookie.getValue());
                    }
                }


    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
      return   Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(cookieName))
                .findAny().orElse(null);
    }
}

