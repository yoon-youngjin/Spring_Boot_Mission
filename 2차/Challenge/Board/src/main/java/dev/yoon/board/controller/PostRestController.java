package dev.yoon.board.controller;

import dev.yoon.board.domain.Media;
import dev.yoon.board.dto.MediaDescriptorDto;
import dev.yoon.board.dto.PostDto;
import dev.yoon.board.service.MediaService;
import dev.yoon.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("board/{boardId}/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final MediaService mediaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDto> createPost(
            @PathVariable("boardId") Long id,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestPart(value = "post") PostDto postDto,
            HttpServletRequest request) throws Exception {

        postDto.setBoardId(id);
        PostDto dto = this.postService.createPost(id, postDto, files);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }


    @GetMapping("{postId}")
    public ResponseEntity<PostDto> readPostOne(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId) {

        PostDto postDto = this.postService.readPostOneByBoardId(boardId, postId);
        if (postDto == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(postDto);
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> readPostAll(
            @PathVariable("boardId") Long boardId
    ) {
        List<PostDto> postDtos = this.postService.readPostAllByBoardId(boardId);
        if (postDtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDtos);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{postId}")
    public void updatePost(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestPart(value = "post") PostDto postDto) {

        this.postService.updatePost(boardId,postId,postDto,files);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId,
            @RequestBody PostDto postDto) {

        if (!this.postService.deletePost(boardId, postId, postDto))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }
}
