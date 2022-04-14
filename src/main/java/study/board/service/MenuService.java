package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.entity.Menu;
import study.board.repository.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    /**
     * DB Menu 테이블 화면에 표시
     */
    public List<Menu> menu() {
        return menuRepository.findAll();
    }
}
