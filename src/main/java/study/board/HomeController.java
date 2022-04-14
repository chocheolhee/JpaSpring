package study.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.board.entity.Member;
import study.board.entity.Menu;
import study.board.repository.MemberRepository;
import study.board.repository.MenuRepository;
import study.board.service.MenuService;
import study.board.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MenuService menuService;


//    @GetMapping("/")
//    public String homeLogin(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession();
//        if (session == null) {
//            return "home";
//        }
//
//        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        // 세션에 회원 데이터가 없으면 home
//        if (loginMember == null) {
//            return "home";
//        }
//
//        // 세션이 유지되면 로그인으로 이동
//        model.addAttribute("member", loginMember);
//        return "login/loginHome";
//    }

    /**
     * @ SessionAttribute 사용
     */
    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Member loginMember, Model model) {

        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // Menu 데이터 화면에 뿌려줌
        List<Menu> menu = menuService.menu();
        model.addAttribute("menu", menu);

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "login/loginHome";
    }
}
