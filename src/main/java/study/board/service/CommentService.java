package study.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;
import study.board.entity.Comment;
import study.board.entity.Member;
import study.board.form.CommentForm;
import study.board.repository.BoardRepository;
import study.board.repository.CommentRepository;
import study.board.repository.MemberRepository;
import study.board.session.SessionConst;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 저장
     */
    @Transactional
    public void save(Long boardId, Comment form, HttpSession request) {

        Member findMember = (Member) request.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = findMember.getId();
        Member member = memberRepository.findById(memberId).get();

        Board board = boardRepository.findId(boardId);

        Comment comment = new Comment();
        comment.setContent(form.getContent());
        comment.setBoard(board);
        comment.setMember(member);

        commentRepository.save(comment);
    }

    /**
     * 댓글 조회
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void update(Long id, String content) {
        Comment comment = commentRepository.findById(id).get();
        comment.setContent(content);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }
}
