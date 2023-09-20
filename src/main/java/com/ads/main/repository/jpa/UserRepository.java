package com.ads.main.repository.jpa;

import com.ads.main.core.security.config.dto.Role;
import com.ads.main.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findUserEntityByUserIdAndUserRole(String userId, Role user);
    Optional<UserEntity> findUserEntityByUserSeq(long userSeq);

    List<UserEntity> findUserEntitiesByUserSeqIn(List<Long> userSeqList);

    Optional<UserEntity> findUserEntityByUserIdAndUserPassword(String userId, String password);
}
