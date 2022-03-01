package dev.yoon.board.dto;

import dev.yoon.board.domain.Media;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MediaDescriptorDto {

    private String originalName;
    private String resourcePath;

    public MediaDescriptorDto(Media media) {
        this.originalName = media.getOriginalName();
        this.resourcePath = media.getResourcePath();
    }
}
