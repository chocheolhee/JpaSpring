package study.board.form;

import lombok.*;
import study.board.entity.Member;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberForm {

    @NotEmpty(message = "아이디를 입력해 주세요.")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;

    private String userName;

    @Builder
    public MemberForm(String userId, String password, String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build();
    }
}
