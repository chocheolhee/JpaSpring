package study.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String userName;

    @Builder
    public Member( String userId, String password, String userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }
}
