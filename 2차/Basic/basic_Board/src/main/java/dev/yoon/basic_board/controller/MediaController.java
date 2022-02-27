package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.MediaDescriptorDto;
import dev.yoon.basic_board.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

//    @PostMapping
//    public void uploadMedia(
//            @RequestParam("file") MultipartFile file
//    ) {
//        String basePath = "./media";
//        File dir = new File(basePath);
//        if (!dir.exists())
//            dir.mkdir();
//        // Path.of : 여러개의 경로를 새로운 경로로 묶어줌
//        Path newFilePath = Path.of(basePath, file.getOriginalFilename());
//        try {
//            file.transferTo(newFilePath);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @PostMapping("upload")
    public ResponseEntity<MediaDescriptorDto> uploadMedia(
            @RequestParam("file") MultipartFile file
    ) {
        MediaDescriptorDto descriptorDto = this.mediaService.saveFile(file);
        return ResponseEntity
                .status(descriptorDto.getStatus())
                .body(descriptorDto);
    }

    @PostMapping("upload-bulk")
    public ResponseEntity<List<MediaDescriptorDto>> uploadMediaBulk(
            @RequestParam("file") MultipartFile[] files
    ) {
        return ResponseEntity
                .status(HttpStatus.MULTI_STATUS)
                .body(this.mediaService.saveFileBulk(files));
    }

    @GetMapping("**")
    public ResponseEntity<byte[]> staticLikeEndPoint(
            HttpServletRequest request
    ){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(
                this.mediaService.getFileAsBytes(request.getRequestURI().split("media")[1]),
                httpHeaders,
                HttpStatus.OK
        );


    }



}
