package study.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.form.FindNameForm;
import study.board.form.PasswordForm;
import study.board.repository.MemberRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        // 중복 회원 검증
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
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
    public Member login(String userId, String password) {

        Member findMember = memberRepository.findByUserId(userId);

        if (findMember.getPassword().equals(password)) {
            return findMember;
        } else {
            return null;
        }

        //람다 적용
//       return memberRepository.findByUserId(userId)
//                .filter(member -> member.getPassword().equals(password))
//                .orElse(null);
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
     * 비밀번호 찾기 H2 DB 통해서 판별
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
