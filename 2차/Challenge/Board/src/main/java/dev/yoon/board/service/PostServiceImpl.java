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

        // ?????? Post??? File ?????? ??????
        if (CollectionUtils.isEmpty(fileList)) {
            if (!CollectionUtils.isEmpty(multipartFileList)) {
                for (MultipartFile multipartFile : multipartFileList) {
                    addFileList.add(multipartFile);
                }
            }
        }
        // ?????? Post??? File ?????? ??????
        else {

            // ????????? ?????? ?????? ??????
            if (CollectionUtils.isEmpty(multipartFileList)) {
                for (Media media : fileList)
                    mediaRepository.deleteMedia(media);
            }
            // ????????? ?????? ?????? ??????
            else {

                List<String> dbOriginNameList = new ArrayList<>();

                // DB??? ?????? ????????? ??????
                for (Media media : fileList) {

                    Media meida = mediaRepository.findMediaOneFromPost(postId, media.getId());
                    MediaDescriptorDto descriptorDto = new MediaDescriptorDto(meida);
                    String dbOrigFileName = descriptorDto.getOriginalName();

                    // ????????? ????????? ????????? ??? ??????????????? ????????? ?????? x ??????
                    if (!multipartFileList.contains(dbOrigFileName))
                        mediaRepository.deleteMedia(media);
                    else
                        // ????????? ?????? ????????? ??????
                        dbOriginNameList.add(dbOrigFileName);
                }

                for (MultipartFile multipartFile : multipartFileList) {
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    // DB??? ?????? ??????
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
