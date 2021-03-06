package dev.yoon.board;

import dev.yoon.board.domain.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * MultipartFile => File
 */
@Component
@RequiredArgsConstructor
public class FileHandler {
    private final String basePath = "./media";

    public List<Media> parseFileInfo(
            List<MultipartFile> files
    ) throws Exception {
        List<Media> mediaList = new ArrayList<>();


        if (!CollectionUtils.isEmpty(files)) {

//            String absolutePath = new java.io.File("").getAbsolutePath()
//                    + java.io.File.separator + java.io.File.separator;
//
//            String path = "images" + java.io.File.separator;
//            java.io.File file = new java.io.File(path);

//            if (!file.exists()) {
//                boolean wasSuccessful = file.mkdirs();
//
//                // 디렉터리 생성에 실패했을 경우
//                if (!wasSuccessful)
//                    System.out.println("file: was not successful");
//            }

            // 다중 파일 처리
            for (MultipartFile file : files) {

                LocalDateTime now = LocalDateTime.now();
                String targetDir = Path.of(
                        basePath,
                        now.format(DateTimeFormatter.BASIC_ISO_DATE)
                ).toString();

                String newFileName = now.format(DateTimeFormatter.ofPattern("HHmmss"))
                        + "_"
                        + file.getOriginalFilename();

                File dirNow = new File(targetDir);

                if (!dirNow.exists()) dirNow.mkdir();

//                file.transferTo(Path.of(
//                        targetDir,
//                        newFileName
//                ));
                Media media = new Media();
                media.setOriginalName(file.getOriginalFilename());
                media.setResourcePath(Path.of(
                        targetDir,
                        newFileName
                ).toString().substring(1));

                mediaList.add(media);

//                // 파일의 확장자 추출
//                String originalFileExtension;
//                String contentType = multipartFile.getContentType();

//                확장자명이 존재하지 않을 경우 처리 x
//                if (ObjectUtils.isEmpty(contentType)) {
//                    break;
//                } else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
//                    if (contentType.contains("image/jpeg"))
//                        originalFileExtension = ".jpg";
//                    else if (contentType.contains("image/png"))
//                        originalFileExtension = ".png";
//                    else  // 다른 확장자일 경우 처리 x
//                        break;
//                }
                // 파일명 중복 피하고자 나노초까지 얻어와 지정
//                String new_file_name = System.nanoTime() + originalFileExtension;
//
//                // 파일 DTO 생성
//                FileDto fileDto = FileDto.builder()
//                        .origFileName(multipartFile.getOriginalFilename())
//                        .filePath(path + java.io.File.separator)
//                        .fileSize(multipartFile.getSize())
//                        .build();
//
//                // 파일 DTO 이용하여 Photo 엔티티 생성
//                Media media1 = Media.createFile(fileDto);
//
//
//                // 생성 후 리스트에 추가
//                mediaList.add(media1);
//
//                // 업로드 한 파일 데이터를 지정한 파일에 저장
//                file = new java.io.File(absolutePath + path + java.io.File.separator + new_file_name);
//                multipartFile.transferTo(file);

            }
        }
        return mediaList;
    }
}
