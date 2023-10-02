package com.ybcharlog.api.controller.common;

import com.ybcharlog.api.service.AWS.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class S3UploadController {

	private final S3UploaderService s3UploaderService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/files/images")
	public ResponseEntity<String> uploadImageFile(
			@RequestParam("file") MultipartFile file, @RequestParam("path") String path)
			throws IOException, URISyntaxException {
		log.info("{}, {}", file, path);

		String uploadStatus = s3UploaderService.upload(file, path);
		return ResponseEntity.ok(uploadStatus);
	}
}
