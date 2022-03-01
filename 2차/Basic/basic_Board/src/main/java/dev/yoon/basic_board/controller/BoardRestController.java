package dev.yoon.basic_board.controller;


import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.dto.BoardDto;
import dev.yoon.basic_board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("board")
@Slf4j
public class BoardRestController {
    private final BoardService boardService;

    public BoardRestController(
            @Autowired BoardService boardService
    ) {
        this.boardService = boardService;
    }

    @PostMapping()
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto, HttpServletRequest request) {
        Board board = Board.createBoard(boardDto);
        this.boardService.createBoard(board);
        return ResponseEntity.ok(boardDto);
    }

    @GetMapping()
    public ResponseEntity<List<BoardDto>> readBoardAll() {
        return ResponseEntity.ok(this.boardService.readBoardAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<BoardDto> readBoardOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.boardService.readBoard(id));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    public ResponseEntity<?> updateBoard(@PathVariable("id") Long id, @RequestBody BoardDto boardDto) {
        log.info("target id: " + id);
        log.info("update content: " + boardDto);
        if (!boardService.updateBoard(id, boardDto))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();


    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        if (!boardService.deleteBoard(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }


}
