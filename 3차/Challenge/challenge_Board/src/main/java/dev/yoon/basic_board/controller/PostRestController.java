package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.PostDto;
import dev.yoon.basic_board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("board/{boardId}/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDto> createPost(
            @PathVariable("boardId") Long id,
            @RequestBody @Valid PostDto postDto,
            HttpServletRequest request) throws Exception {

        postDto.setBoardId(id);
        PostDto dto = this.postService.createPost(id,postDto);

        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> readPostAll(
            @PathVariable("boardId") Long boardId
    ) {
        List<PostDto> postDtoList = this.postService.readPostAllbyBoardId(boardId);

        return ResponseEntity.ok(postDtoList);

    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> readPostOne(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId) {

        PostDto postDto = this.postService.readPostOneByBoardId(boardId, postId);

        return ResponseEntity.ok(postDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId,
            @RequestBody PostDto postDto) {
        postService.updatePost(boardId, postId, postDto);

        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId,
            @RequestBody PostDto postDto) {
        this.postService.deletePost(boardId, postId, postDto);

        return ResponseEntity.noContent().build();

    }

}