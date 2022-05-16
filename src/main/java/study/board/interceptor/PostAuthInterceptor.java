package study.board.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.repository.BoardRepository;
import study.board.service.BoardService;
import study.board.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class PostAuthInterceptor implements HandlerInterceptor {

    @Autowired
    BoardService boardService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("Board 권한 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long id = loginMember.getId();

        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long boardId = Long.parseLong((String) pathVariables.get("id"));
        log.info("boardId = {}", boardId);

        Board board = boardService.getBoard(boardId);
        Long id1 = board.getMember().getId();

        if (!id1.equals(id)) {
            log.info("수정 권한 미인증 사용자 요청");
            // redirect
            response.sendRedirect("/board");
            return false;
        }
        return true;
    }
}
