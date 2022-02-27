package dev.yoon.basic_board.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MediaDescriptorDto {
    // 요청을 처리한 결과
    private Integer status;

    private String message;

    private String originalName;

    private String resourcePath;

}
