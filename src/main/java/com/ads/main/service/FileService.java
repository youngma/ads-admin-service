package com.ads.main.service;


import com.google.common.io.Files;
import com.ads.main.vo.FilesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileService {

    @Value("${app.file.uploadTempPath}")
    private String UPLOAD_TEMP_LOCATION;

    @Value("${app.file.uploadPath}")
    private String UPLOAD_LOCATION;

    public void move(FilesVo filesVo, String fileType) {
        try {
            if (filesVo != null && filesVo.getNewFile()) {
                File tempFile = Paths.get(UPLOAD_TEMP_LOCATION + "/" + filesVo.getFileName()).toFile();

                File destinationFile = Paths.get(UPLOAD_LOCATION + "/" + filesVo.getFileName()).toFile();

                if (tempFile.exists() && destinationFile.exists()) {
                    throw new RuntimeException(fileType + "을 다시 등록 해주세요.");
                }

                if ( !destinationFile.getParentFile().exists() ) {
                    try {
                        destinationFile.getParentFile().mkdirs();
                        log.debug("폴더가 생성 되었습니다.");
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

                if ( !destinationFile.exists() ) {
                    Files.move(tempFile, destinationFile);
                }
            }
        } catch (Exception e) {
            log.error("# 파일 이동 중 오류", e);
            throw new RuntimeException(fileType + " 이동 중 오류 발생.");
        }
    }
}
