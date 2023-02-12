package spring_study.board_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.board_crud.domain.Board;
import spring_study.board_crud.repository.BoardRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository; // Auto wired로 스프링 빈에 등록

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(NullPointerException::new);

    }

    @Transactional // DB에 영향을 주기 때문 (db에 영향을 주는 생성,삭제같은 민감한 쿼리들은 트렌젝션으로 감싸준다.
    public void create(Board board){
        boardRepository.save(board);
    }

    @Transactional
// Dirty Checking으로 update 수행
    public void update(Long id, String title, String content){
        Board board = boardRepository.findById(id).orElseThrow(NullPointerException::new);//jpa의 영속성 컨텍스트 (캐싱을 위한
        board.setTitle(title); //Dirty Checking(변경 감지)을 해서
        board.setContent(content);// 커밋이 되는 순간에 변경된것들을 감지해서 업데이트 쿼리로 쏴준다.
    }

    @Transactional
    public void delete(Board board) {
        boardRepository.delete(board);
    }
}