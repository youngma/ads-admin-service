package com.ads.main.service;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.UserEntity;
import com.ads.main.entity.mapper.UserConverter;
import com.ads.main.repository.jpa.UserRepository;
import com.ads.main.repository.querydsl.QAdvertiserUserRepository;
import com.ads.main.repository.querydsl.QPartnerUserRepository;
import com.ads.main.repository.querydsl.QUserRepository;
import com.ads.main.vo.admin.UserModifyVo;
import com.ads.main.vo.admin.UserPasswordVo;
import com.ads.main.vo.admin.UserSearchVo;
import com.ads.main.vo.admin.UserVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ads.main.core.enums.exception.UserException.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final QUserRepository qUserRepository;
    private final QAdvertiserUserRepository qAdvertiserUserRepository;
    private final QPartnerUserRepository qPartnerUserRepository;

    private final SCryptPasswordEncoder sCryptPasswordEncoder = SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();


    private PageMapper<UserVo, UserEntity> pageMapper;

    @PostConstruct
    private void initPageMappers() {
         pageMapper =  new PageMapper<>(userConverter);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserEntityById(String userid) {
        return userRepository.findUserEntityByUserIdAndUserRole(userid, Role.ADMIN);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserEntityBySeq(long userSeq) {
        return userRepository.findUserEntityByUserSeq(userSeq);
    }

    @Transactional(readOnly = true)
    public boolean adminUserCheck(String userid) {
        return findUserEntityById(userid).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean advertiserUserCheck(long advertiserSeq, String userId) {
        return qAdvertiserUserRepository.findAdvertiserUsersByAdvertiserSeqAndUserId(advertiserSeq, userId).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean partnerUserCheck(long partnerSeq, String userId) {
        return qPartnerUserRepository.findPartnerUser(partnerSeq, userId).isPresent();
    }

    @Transactional(readOnly = true)
    public UserVo findUserById(String userid) throws UsernameNotFoundException {
        UserEntity userEntity = findUserEntityById(userid).orElseThrow(USER_NOT_FOUND::throwErrors);
        return userConverter.toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public UserVo findUser(String userid, String password) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUserIdAndUserPassword(userid, password).orElseThrow(USER_NOT_FOUND::throwErrors);
        return userConverter.toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public Page<UserVo> findUsers(UserSearchVo userSearchVo, Pageable pageable) {
        Page<UserEntity> users = qUserRepository.searchUsers(userSearchVo, pageable);
        return pageMapper.convert(users);
    }

    @Transactional(readOnly = true)
    public void registerValid(UserVo userVo) {
        if (adminUserCheck(userVo.getUserId())) {
            throw USER_ALREADY_EXISTS.throwErrors();
        }
    }

    @Transactional(readOnly = true)
    public void registerValid(Role role, long advertiserSeq, String userId) {
        switch (role) {
            case ADVERTISER -> {
                if (advertiserUserCheck(advertiserSeq, userId)) {
                    throw USER_ALREADY_EXISTS.throwErrors();
                }
            }
            case PARTNER -> {
                if (partnerUserCheck(advertiserSeq, userId)) {
                    throw USER_ALREADY_EXISTS.throwErrors();
                }
            }
            default -> throw USER_ALREADY_EXISTS.throwErrors();
        }
    }

    @Transactional
    public UserVo register(UserVo userVo) {

        registerValid(userVo);

        UserEntity userEntity = convertUser(userVo);
        userEntity.enable();

        userRepository.save(userEntity);
        return userConverter.toDto(userEntity);
    }

    @Transactional
    public UserVo modify(UserModifyVo userVo) {
        UserEntity userEntity = findUserEntityBySeq(userVo.getUserSeq()).orElseThrow(USER_NOT_FOUND::throwErrors);
        userConverter.updateFromDto(userVo, userEntity);
        userRepository.save(userEntity);
        return userConverter.toDto(userEntity);
    }

    @Transactional
    public UserVo userDisable(long userSeq) {
        UserEntity userEntity = findUserEntityBySeq(userSeq).orElseThrow(USER_NOT_FOUND::throwErrors);
        userEntity.disable();

        userRepository.save(userEntity);

        return userConverter.toDto(userEntity);
    }

    @Transactional
    public List<UserVo> bulkUserDisable(List<Long> userSeqList) {
        qUserRepository.updateUserStatus(userSeqList, UserStatus.Disable);
        List<UserEntity> userEntities = userRepository.findUserEntitiesByUserSeqIn(userSeqList);
        return userConverter.toDtoList(userEntities);
    }


    @Transactional
    public UserVo userEnable(long userSeq) {

        UserEntity userEntity = findUserEntityBySeq(userSeq).orElseThrow(USER_NOT_FOUND::throwErrors);
        userEntity.enable();

        userRepository.save(userEntity);

        return userConverter.toDto(userEntity);
    }

    @Transactional
    public List<UserVo> bulkUserEnable(List<Long> userSeqList) {
        qUserRepository.updateUserStatus(userSeqList, UserStatus.Enable);
        List<UserEntity> userEntities = userRepository.findUserEntitiesByUserSeqIn(userSeqList);
        return userConverter.toDtoList(userEntities);
    }


    @Transactional
    public UserVo changePassword(UserPasswordVo userPasswordVo) {

        Optional<UserEntity> optionalUserEntity = findUserEntityBySeq(userPasswordVo.getUsrSeq());
        UserEntity userEntity = optionalUserEntity.orElseThrow(USER_NOT_FOUND::throwErrors);

        if (userEntity.comparePassword(sCryptPasswordEncoder, userPasswordVo.getCurrentUserPassword())) {
            userEntity.setUserPassword(userPasswordVo.getNextUserPassword());

        userRepository.save(userEntity);
        return userConverter.toDto(userEntity);

        } else {
            throw PASSWORD_MISS_MATCH.throwErrors();
        }
    }

    public UserEntity convertUser(UserVo userVo) {

        String password = sCryptPasswordEncoder.encode(userVo.getUserPassword());
        userVo.setUserPassword(password);

        return userConverter.toEntity(userVo);
    }
}
