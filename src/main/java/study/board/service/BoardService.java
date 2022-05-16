package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.repository.BoardRepository;
import study.board.repository.MemberRepository;
import study.board.session.SessionConst;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public void save(Board form , HttpSession request) {

        Member findMember = (Member) request.getAttribute(SessionConst.LOGIN_MEMBER);
        Long id = findMember.getId();

        Member member = memberRepository.findById(id).get();

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setWriter(form.getWriter());
        board.setMember(member);

        boardRepository.save(board);
    }

    /**
     * 게시글 조회
     * 페이징
     */
    @Transactional
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /**
     * 게시글 하나 조회
     */
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).get();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void update(Long id, String title, String content, String writer) {
        Board findArticle = boardRepository.findById(id).get();

        findArticle.setTitle(title);
        findArticle.setContent(content);
        findArticle.setWriter(writer);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id).get();
        boardRepository.delete(board);
    }
}
