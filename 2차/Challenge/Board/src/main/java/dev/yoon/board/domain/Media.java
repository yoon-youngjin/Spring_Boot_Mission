package dev.yoon.board.domain;

import dev.yoon.board.dto.MediaDescriptorDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Media {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String originalName;

    private String resourcePath;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Media(MediaDescriptorDto dto) {
        this.setOriginalName(dto.getOriginalName());
        this.setResourcePath(dto.getResourcePath());
    }

//    @Builder
//    public Media(String origFileName, String filePath, Long fileSize) {
//        this.origFileName = origFileName;
//        this.filePath = filePath;
//        this.fileSize = fileSize;
//    }
//
//    public static Media createFile(FileDto fileDto) {
//        Media media = new Media();
//        media.setFilePath(fileDto.getFilePath());
//        media.setOrigFileName(fileDto.getOrigFileName());
//        media.setFilePath(fileDto.getFilePath());
//        return media;
//    }


}
