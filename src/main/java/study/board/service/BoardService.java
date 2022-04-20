package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.repository.BoardRepository;
import study.board.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 등록
     */
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
}
