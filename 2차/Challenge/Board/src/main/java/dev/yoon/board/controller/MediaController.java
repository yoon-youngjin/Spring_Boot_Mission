package dev.yoon.board.controller;

import dev.yoon.board.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

//
//    @GetMapping("**")
//    public ResponseEntity<byte[]> staticLikeEndPoint(
//            HttpServletRequest request
//    ){
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.IMAGE_PNG);
//
//        return new ResponseEntity<>(
//                this.mediaService.getFileAsBytes(request.getRequestURI().split("media")[1]),
//                httpHeaders,
//                HttpStatus.OK
//        );
//
//
//    }



}
