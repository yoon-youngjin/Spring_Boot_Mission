package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.PostDto;
import dev.yoon.basic_board.repository.BoardRepository;
import dev.yoon.basic_board.repository.PostRepository;
import dev.yoon.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Override
    public PostDto createPost(Long boardId, PostDto postDto) {

        Optional<User> optionalUser = this.userRepository.findById(postDto.getUserId());
        Board board = this.boardRepository.findOne(boardId);
        if (optionalUser.isEmpty() || board == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        Post post = Post.createPost(postDto);
        user.addPost(post);
        board.addPost(post);

        this.postRepository.save(post);
        return postDto;
    }


    @Override
    public List<PostDto> readPostAllbyBoardId(Long boardId) {
        List<Post> postList = this.postRepository.findPostAllbyBoardId(boardId);

        if(postList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : postList) {
            PostDto postDto = PostDto.createPostDtoPassWordMasked(post);
            postDtos.add(postDto);
        }
        return postDtos;
    }


    @Override
    public boolean updatePost(Long boardId, Long postId, PostDto postDto) {
        Board board = boardRepository.findOne(boardId);
        Post post = postRepository.findPostOnebyBoardId(boardId, postId);

        if (board == null || post == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return postRepository.updatePost(boardId,postId,postDto);
    }

    @Override
    public boolean deletePost(Long boardId, Long postId, PostDto postDto) {
        Board board = boardRepository.findOne(boardId);
        Post post = postRepository.findPostOnebyBoardId(boardId, postId);

        if (board == null || post == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return this.postRepository.deletePost(boardId, postId, postDto.getPw());
    }

    @Override
    public PostDto readPostOneByBoardId(Long boardId, Long postId) {
        Post post = this.postRepository.findPostOnebyBoardId(boardId, postId);

        if(post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        PostDto postDto = PostDto.createPostDtoPassWordMasked(post);
        return postDto;
    }
}
