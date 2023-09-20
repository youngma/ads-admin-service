package com.ads.main.repository.jpa;

import com.ads.main.entity.FilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilesEntityRepository extends JpaRepository<FilesEntity, Long>, JpaSpecificationExecutor<FilesEntity> {

}
