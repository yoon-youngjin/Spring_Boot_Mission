package dev.yoon.basic_board.service;

import dev.yoon.basic_board.dto.MediaDescriptorDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaDescriptorDto saveFile(MultipartFile file);

    List<MediaDescriptorDto> saveFileBulk(MultipartFile[] files);

    // 정적 리소스를 보내주기 위한 설정을 위한 함수
    byte[] getFileAsBytes(String resourcePath);
}
