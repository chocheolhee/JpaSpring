package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.form.FindNameForm;
import study.board.form.LoginForm;
import study.board.entity.Member;
import study.board.form.PasswordForm;
import study.board.service.MemberService;
import study.board.session.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult result,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (result.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = memberService.login(form);

        if (loginMember == null) {
            return "login/loginForm";
        } else {
            // 로그인 성공 처리
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
            HttpSession session = request.getSession(true);
            //세션에 로그인 회원 정보 보관
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            return "redirect:" + redirectURL;
        }
    }

    @PostMapping("/logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/findId")
    public String findLoginId(@ModelAttribute FindNameForm form) {
        return "findId";
    }

    @PostMapping("/findId")
    public String findCheckLoginId(@ModelAttribute("findNameForm") FindNameForm form) {
        Member findNameMember = memberService.findLoginName(form);
        if (findNameMember == null) {
            return "findId";
        }
        log.info("member= {}", findNameMember);
        return "redirect:/";
    }

    @GetMapping("/findPassword")
    public String passwordPage(@ModelAttribute PasswordForm form) {
        return "findPassword";
    }

    @PostMapping("/findPassword")
    public String findPassword(@ModelAttribute("passwordForm") PasswordForm form) {
        Member findPassword = memberService.findPassword(form);

        if (findPassword == null) {
            return "findPassword";
        }
        log.info("member= {}", findPassword.getUserName());
        return "redirect:/";
    }
}
