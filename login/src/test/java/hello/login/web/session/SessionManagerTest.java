package hello.login.web.session;


import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.assertj.core.api.Assertions.*;
import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {
        SessionManager sessionManager = new SessionManager();
        @Test
    void sessionTest(){
           MockHttpServletResponse response = new MockHttpServletResponse();

            //세션생성
            Member member = new Member();
            sessionManager.createSession(member,response);
            //요청에 으답 쿠키 저장

            //세션조회
            MockHttpServletRequest    request= new MockHttpServletRequest();
            request.setCookies(response.getCookies());
            Object result=sessionManager.getSession(request);
            Assertions.assertThat(result).isEqualTo(member);

            //세션 만료
            sessionManager.expire(request);
            Object expired = sessionManager.getSession(request);
            assertThat(expired).isNull();
        }

}
