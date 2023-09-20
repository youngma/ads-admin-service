package com.ads.main.controller;


import com.ads.main.core.vo.FileVo;
import com.ads.main.core.vo.RespVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/upload")
@RestController
@Slf4j
@Validated
public class UploadController {

    @Value("${app.file.uploadTempPath}")
    private String UPLOAD_LOCATION;

    @PostMapping("/{target}/files")
    public RespVo<List<FileVo>> upload(
            @PathVariable("target") String target,
            @RequestParam("file") MultipartFile[] files,
            ModelMap model,
            RedirectAttributes redirectAttributes
    ) {

        log.debug("파일개수 : {}", files.length);

        List<FileVo> fileList = new ArrayList<>();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                FileVo fileVO = new FileVo();

                String fileName = files[i].getOriginalFilename();
                String fileExt = fileName.substring(fileName.lastIndexOf("."));

                log.debug("UPLOAD_LOCATION : {}", UPLOAD_LOCATION);
                log.debug("파일 이름 : {}", fileName);
                log.debug("파일 확장자 : {}", fileExt);
                log.debug("파일 크기 : {}", files[i].getSize());

                String uuidFile = UUID.randomUUID().toString().replaceAll("-", "") + fileExt;

                log.debug("UUID 파일명 : {}", uuidFile);

                String uploadFolder = UPLOAD_LOCATION + "/" + target;
                String uploadFile = uploadFolder + "/" + uuidFile;

                log.debug("업로드 파일 : {}", uploadFile);

                try {
                    if (files[i].isEmpty()) {
                        throw new IOException("빈 파일 입니다."); // 빈 파일입니다.
                    }

                    File Folder = new File(uploadFolder);
                    if ( !Folder.exists() ) {
                        try {
                            Folder.mkdirs();
                            log.debug("폴더가 생성 되었습니다.");
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }

                    Path destinationFile = Paths.get(uploadFile);
                    try (InputStream inputStream = files[i].getInputStream()) {
                        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    log.error("# upload error", e);
                    throw new RuntimeException("file Save Error");
                }


                fileVO.setOriginFileName(fileName); // file
                fileVO.setNewFileName(uuidFile); // new file
                fileVO.setTarget(target); // new file
                fileVO.setUploadFile(uploadFile); // 경로 + 변경된 파일명

                fileList.add(fileVO);
            }

        }

        return new RespVo<>(fileList);
    }

}
