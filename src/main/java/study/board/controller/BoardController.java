package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.entity.Board;
import study.board.form.BoardForm;
import study.board.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 저장
     */
    @GetMapping("/board/add")
    public String addForm(@ModelAttribute("boardForm")BoardForm boardForm) {
        return "board/createForm";
    }

    @PostMapping("/board/add")
    public String addArticle(BoardForm form) {

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());

        boardService.save(board);

        return "redirect:/board";
    }

    /**
     * 게시글 상세 페이지
     */
    @GetMapping("/board/view")
    public String boardView(Long id, Model model) {

        Board board = boardService.getBoard(id);
        model.addAttribute("board", board);

        return "board/boardView";
    }

    /**
     * 게시글 전체 페이지
     */
    @GetMapping("/board")
    public String boardList(Model model) {

        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);

        return "board/boardList";
    }
}
