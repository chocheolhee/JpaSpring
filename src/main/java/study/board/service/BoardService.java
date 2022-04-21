package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.repository.BoardRepository;
import study.board.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public void save(Board board) {
        boardRepository.save(board);
    }

    /**
     * 게시글 조회
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
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
