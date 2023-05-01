package com.ybcharlog.api.controller.common;

import com.ybcharlog.api.RequestDto.post.PostThumbNailImageDto;
import com.ybcharlog.api.repository.comment.CommentRepository;
import com.ybcharlog.api.repository.post.PostRepository;
import com.ybcharlog.api.service.AWS.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class S3UploadController {

	private final S3UploaderService s3UploaderService;

	@PostMapping("/files/images")
	public ResponseEntity<String> uploadImageFile(
			@RequestParam("file") MultipartFile file, @RequestParam("path") String dirName)
			throws IOException, URISyntaxException {

		String uploadStatus = s3UploaderService.upload(file, dirName);
		return ResponseEntity.ok(uploadStatus);
	}
}