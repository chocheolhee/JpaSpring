package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.board.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // @Query 사용해서 직접 SQL 정의
    @Query("select m from Member m where m.userId= :userId")
    Member findByUserId(@Param("userId") String userId);

    @Query("select m from Member m where m.userName= :userName")
    Member findUserName(@Param("userName") String userName);

}
