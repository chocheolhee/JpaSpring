package study.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.form.FindNameForm;
import study.board.form.LoginForm;
import study.board.form.MemberForm;
import study.board.form.PasswordForm;
import study.board.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(MemberForm form) {

        // 중복 회원 검증
        validateDuplicateMember(form);

        Member member = form.toEntity();

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(MemberForm member) {
        Member findMember = memberRepository.findByUserId(member.getUserId());
        if (findMember != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 로그인
     */
    public Member login(LoginForm form) {
        Member findMember = memberRepository.findByUserId(form.getUserId());

        if (findMember.getPassword().equals(form.getPassword())) {
            return findMember;
        } else {
            return null;
        }
    }

    /**
     * 아이디 찾기
     */
    public Member findLoginName(FindNameForm member) {

        Member findMember = memberRepository.findUserName(member.getUserName());

        if (findMember != null) {
            return findMember;
        } else {
            return null;
        }
    }

    /**
     * 비밀번호 찾기
     */
    public Member findPassword(PasswordForm member) {

        Member findMember = memberRepository.findPassword(member.getPassword());

        if (findMember != null) {
            return findMember;
        } else {
            return null;
        }
    }
}
