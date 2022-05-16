package study.board.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import study.board.entity.Board;
import study.board.repository.BoardRepository;
import study.board.service.BoardService;
import study.board.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
public class PostAuthInterceptor implements HandlerInterceptor {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("Board 권한 체크 인터셉터 실행 {}", requestURI);

        String httpMethod = request.getMethod();

        if(httpMethod.equals("POST") || httpMethod.equals("DELETE")) {
            String sessionItem = (String)request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
            Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            Long id = Long.parseLong((String)pathVariables.get("id"));

            Board board = boardRepository.findById(id).get();
            String commentWriter = board.getCreatedBy();

            if(!commentWriter.equals(sessionItem)){
                response.getOutputStream().println("NOT AUTHORIZE!!");
                return false;
            }
        }

        return true;
    }
}
