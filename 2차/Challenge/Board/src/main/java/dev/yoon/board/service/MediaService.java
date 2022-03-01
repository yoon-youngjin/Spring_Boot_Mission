package dev.yoon.board.service;

import dev.yoon.board.domain.Media;
import dev.yoon.board.dto.MediaDescriptorDto;
import dev.yoon.board.repository.MediaRepository;
import dev.yoon.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MediaService {

    private final PostRepository postRepository;
    private final MediaRepository mediaRepository;


    public List<Media> findMediaAllByPost(Long postId) {
        return this.mediaRepository.findMediaAllFromPost(postId);
//        return mediaList.stream()
//                .map(media -> new MediaDescriptorDto(media))
//                .collect(Collectors.toList());

    }

    public MediaDescriptorDto findMediaOneByPost(Long postId, Long meidaId) {
        Media media = this.mediaRepository.findMediaOneFromPost(postId,meidaId);
        return new MediaDescriptorDto(media);
    }

    public void deleteMedia(Media media) {
        mediaRepository.deleteMedia(media);
    }

}
