package study.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    /**
     * Member 엔티티 연관관계 맵핑
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 연관관계 편의 메소드
     */
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getBoards().remove(this);
        }
        this.member = member;
        member.getBoards().add(this);
    }
}