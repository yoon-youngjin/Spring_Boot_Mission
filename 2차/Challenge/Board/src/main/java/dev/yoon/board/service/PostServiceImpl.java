package dev.yoon.board.service;

import dev.yoon.board.FileHandler;
import dev.yoon.board.domain.Media;
import dev.yoon.board.domain.Post;
import dev.yoon.board.domain.Board;
import dev.yoon.board.dto.MediaDescriptorDto;
import dev.yoon.board.dto.PostDto;
import dev.yoon.board.repository.MediaRepository;
import dev.yoon.board.repository.PostRepository;
import dev.yoon.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MediaRepository mediaRepository;
    private final FileHandler fileHandler;

    @Override
    public PostDto createPost(Long boardId, PostDto postDto, List<MultipartFile> files) throws Exception {

        Board board = this.boardRepository.findOne(boardId);
        if (board == null) {
            return null;
        }
        Post post = Post.createPost(postDto);
        board.addPost(post);

        List<Media> mediaList = fileHandler.parseFileInfo(files);

        if (!mediaList.isEmpty()) {
            for (Media media : mediaList) {
                mediaRepository.save(media);
                post.addFile(media);
            }
        }
        this.postRepository.save(post);
        postDto.setBoardName(board.getName());
        return postDto;
    }

    @Override
    public PostDto readPostOneByBoardId(Long boardId, Long postId) {
        Post post = this.postRepository.findPostOnebyBoardId(boardId, postId);

        if (post == null) {
            return null;
        }
        PostDto postDto = new PostDto(post);

        return postDto;
    }

    @Override
    public List<PostDto> readPostAllByBoardId(Long boardId) {

        List<Post> posts = this.postRepository.findPostAllByBoardId(boardId);

        if (posts == null) {
            return null;
        }

        List<PostDto> postDtos = posts.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());


        return postDtos;

    }

    @SneakyThrows
    @Override
    public void updatePost(Long boardId, Long postId, PostDto postDto,List<MultipartFile> files) {

        List<Media> fileList = mediaRepository.findMediaAllFromPost(postId);
        List<MultipartFile> multipartFileList = files;

        List<MultipartFile> addFileList = new ArrayList<>();

        // 기존 Post에 File 없던 경우
        if (CollectionUtils.isEmpty(fileList)) {
            if (!CollectionUtils.isEmpty(multipartFileList)) {
                for (MultipartFile multipartFile : multipartFileList) {
                    addFileList.add(multipartFile);
                }
            }
        }
        // 기존 Post에 File 있던 경우
        else {

            // 전달된 파일 없는 경우
            if (CollectionUtils.isEmpty(multipartFileList)) {
                for (Media media : fileList)
                    mediaRepository.deleteMedia(media);
            }
            // 전달된 파일 있는 경우
            else {

                List<String> dbOriginNameList = new ArrayList<>();

                // DB의 파일 원본명 추출
                for (Media media : fileList) {

                    Media meida = mediaRepository.findMediaOneFromPost(postId, media.getId());
                    MediaDescriptorDto descriptorDto = new MediaDescriptorDto(meida);
                    String dbOrigFileName = descriptorDto.getOriginalName();

                    // 서버에 저장된 파일들 중 전달되어온 파일이 존재 x 경우
                    if (!multipartFileList.contains(dbOrigFileName))
                        mediaRepository.deleteMedia(media);
                    else
                        // 기존에 있던 파일인 경우
                        dbOriginNameList.add(dbOrigFileName);
                }

                for (MultipartFile multipartFile : multipartFileList) {
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    // DB에 없는 파일
                    if (!dbOriginNameList.contains(multipartOrigName)) {
                        addFileList.add(multipartFile);
                    }
                }
            }
        }

        Post post = postRepository.findPostOnebyBoardId(boardId, postId);
        post.update(postDto);

        if(addFileList != null) {
            List<Media> mediaList = fileHandler.parseFileInfo(addFileList);
            if (!mediaList.isEmpty()) {
                for (Media media : mediaList) {
                    media.setPost(post);
                    mediaRepository.save(media);
                }
            }
        }

    }

    @Override
    public boolean deletePost(Long boardId, Long postId, PostDto postDto) {

        return this.postRepository.deletePost(boardId, postId, postDto.getPw());
    }


}
