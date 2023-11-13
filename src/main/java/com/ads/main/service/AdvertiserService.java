package com.ads.main.service;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.AdvertiserEntity;
import com.ads.main.entity.AdvertiserUserEntity;
import com.ads.main.entity.UserEntity;
import com.ads.main.entity.mapper.AdvertiserConvert;
import com.ads.main.entity.mapper.AdvertiserUserConvert;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.entity.mapper.UserConverter;
import com.ads.main.repository.jpa.AdvertiserRepository;
import com.ads.main.repository.jpa.AdvertiserUserRepository;
import com.ads.main.repository.querydsl.QAdvertiserRepository;
import com.ads.main.repository.querydsl.QAdvertiserUserRepository;
import com.ads.main.service.FileService;
import com.ads.main.service.UserService;
import com.ads.main.vo.FilesVo;
import com.ads.main.vo.advertiser.AdvertiserModifyVo;
import com.ads.main.vo.advertiser.AdvertiserSearchVo;
import com.ads.main.vo.advertiser.AdvertiserVo;
import com.ads.main.vo.advertiser.AdvertiserBusinessModifyVo;
import com.ads.main.vo.advertiser.user.*;
import com.ads.main.vo.admin.UserVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ads.main.core.enums.exception.AdvertiserException.ADVERTISER_ALREADY_EXISTS;
import static com.ads.main.core.enums.exception.AdvertiserException.ADVERTISER_NOT_FOUND;
import static com.ads.main.core.enums.exception.UserException.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertiserService {


    private final AdvertiserConvert advertiserConvert;
    private final AdvertiserUserConvert advertiserUserConvert;
    private final UserConverter userConverter;
    private final FilesConverter filesConverter;

    private final AdvertiserRepository advertiserRepository;

    private final AdvertiserUserRepository advertiserUserRepository;


    private final UserService userService;
    private final FileService fileService;


    private final QAdvertiserRepository qAdvertiserRepository;
    private final QAdvertiserUserRepository qAdvertiserUserRepository;

    private PageMapper<AdvertiserVo, AdvertiserEntity> pageMapper;
    private PageMapper<UserVo, UserEntity> userPageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper = new PageMapper<>(advertiserConvert);
        userPageMapper = new PageMapper<>(userConverter);
    }

    @Transactional(readOnly = true)
    public Optional<AdvertiserEntity> findUserEntityByAdvertiserSeq(Long advertiserSeq) {
        return advertiserRepository.findAdvertiserEntityByAdvertiserSeq(advertiserSeq);
    }

    @Transactional(readOnly = true)
    public Optional<AdvertiserEntity> findUserEntityByBusinessNumber(String businessNumber) {
        return advertiserRepository.findAdvertiserEntityByBusinessNumber(businessNumber);
    }

    @Transactional(readOnly = true)
    public AdvertiserVo findAdvertiser(long advertiserSeq) {
        AdvertiserEntity advertiserEntity = findUserEntityByAdvertiserSeq(advertiserSeq)
                .orElseThrow(ADVERTISER_NOT_FOUND::throwErrors);
        return advertiserConvert.toDto(advertiserEntity);
    }

    @Transactional(readOnly = true)
    public Page<AdvertiserVo> findAdvertisers(AdvertiserSearchVo advertiserSearchVo, Pageable pageable) {
        Page<AdvertiserEntity> advertiserEntities = qAdvertiserRepository.searchAdvertisers(advertiserSearchVo, pageable);
        return pageMapper.convert(advertiserEntities);
    }

    public AdvertiserUsersVo findAdvertisersUsers(Long advertiserSeq) {

        List<AdvertiserUserEntity> advertiserUserEntities = qAdvertiserRepository.findAdvertiserUsers(advertiserSeq);

        AdvertiserUsersVo advertiserUsers = new AdvertiserUsersVo();
        if (!advertiserUserEntities.isEmpty()){
            advertiserUsers.setAdvertiser(advertiserConvert.toDto(advertiserUserEntities.get(0).getAdvertiserEntity()));
            advertiserUserEntities.forEach(advertiserUserEntity -> {
                advertiserUsers.addUser(userConverter.toDto(advertiserUserEntity.getUserEntity()));
            });
        }

        return advertiserUsers;
    }

    @Transactional(readOnly = true)
    public boolean checkBusinessNumber(String businessNumber) {
        Optional<AdvertiserEntity> advertiserEntityOptional = findUserEntityByBusinessNumber(businessNumber);
        return advertiserEntityOptional.isPresent();
    }

    @Transactional
    public AdvertiserVo register(AdvertiserVo advertiserVo) {

        if (checkBusinessNumber(advertiserVo.getBusinessNumber())) {
            throw USER_ALREADY_EXISTS.throwErrors();
        }


        FilesVo filesVo = advertiserVo.getFile();
        fileService.move(filesVo, "사업자 등록증");

        AdvertiserEntity advertiserEntity = advertiserConvert.toEntity(advertiserVo);
        advertiserRepository.save(advertiserEntity);

        return advertiserConvert.toDto(advertiserEntity);
    }

    @Transactional
    public AdvertiserVo modify(AdvertiserModifyVo advertiserVo) {
        Optional<AdvertiserEntity> advertiserEntityOptional = findUserEntityByAdvertiserSeq(advertiserVo.getAdvertiserSeq());

        if (advertiserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        FilesVo filesVo = advertiserVo.getFile();
        fileService.move(filesVo, "사업자 등록증");

        AdvertiserEntity advertiserEntity = advertiserEntityOptional.get();
        advertiserConvert.updateFromDto(advertiserVo, advertiserEntity);

        advertiserRepository.save(advertiserEntity);

        return advertiserConvert.toDto(advertiserEntity);
    }


    @Transactional
    public AdvertiserVo businessNumberModify(AdvertiserBusinessModifyVo advertiserVo) {
        Optional<AdvertiserEntity> advertiserEntityOptional =findUserEntityByBusinessNumber(advertiserVo.getBusinessNumber());

        if (advertiserEntityOptional.isPresent()) {
            throw ADVERTISER_ALREADY_EXISTS.throwErrors();
        }

         advertiserEntityOptional = findUserEntityByAdvertiserSeq(advertiserVo.getAdvertiserSeq());

        if (advertiserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        AdvertiserEntity advertiserEntity = advertiserEntityOptional.get();
        advertiserConvert.updateFromDto(advertiserVo, advertiserEntity);

        advertiserRepository.save(advertiserEntity);

        return advertiserConvert.toDto(advertiserEntity);
    }

    // 등록된 사용자 유무 체크
    @Transactional(readOnly = true)
    public boolean userCheck(long advertiserSeq, String userid) {
        try {
            userService.registerValid(Role.ADVERTISER, advertiserSeq , userid);
            return false; // 등록된 사용자 가 없음
        } catch (Exception e) {
            return true; // 등록된 사용자 가 있음
        }
    }


    @Transactional(readOnly = true)
    public Page<UserVo> findAdvertiserUsers(AdvertiserUserSearchVo advertiserUserSearchVo, Pageable pageable) {
        return userPageMapper.convert(qAdvertiserUserRepository.findAdvertiserUsers(advertiserUserSearchVo, pageable));
    }


    @Transactional
    public AdvertiserUserVo registerAdvertiserUser(AdvertiserUserRegisterVo registerVo) {

        Optional<AdvertiserEntity> advertiserEntityOptional = findUserEntityByAdvertiserSeq(registerVo.getAdvertiserSeq());

        if (advertiserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        AdvertiserEntity advertiserEntity = advertiserEntityOptional.get();

        UserVo userVo = registerVo.toUser(Role.ADVERTISER, UserStatus.Enable);

        // 광고주 사용자 등록 validation
        userService.registerValid(Role.ADVERTISER, registerVo.getAdvertiserSeq(), userVo.getUserId());

        advertiserEntity.addUser(userService.convertUser(userVo));

        // 광고주 - 사용자 맵핑 정보 저장.
        return advertiserUserConvert.toDtoList(advertiserUserRepository.saveAll(advertiserEntity.getAdvertiserUserEntities()))
                .stream()
                .filter(t -> t.getUser().getUserId().equals(userVo.getUserId()))
                .findFirst().get();
    }

    @Transactional
    public UserVo modifyAdvertiserUser(AdvertiserUserModifyVo modifyVo) {

        Optional<AdvertiserEntity> advertiserEntityOptional = findUserEntityByAdvertiserSeq(modifyVo.getAdvertiserSeq());

        if (advertiserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        return userService.modify(modifyVo);

    }

    @Transactional
    public UserVo advertiserUserEnable(AdvertiserUserStatusVo vo) {

        Optional<AdvertiserUserEntity> advertiserUserEntityOptional = advertiserUserRepository.findAdvertiserUserEntitiesByAdvertiserEntity_AdvertiserSeqAndUserEntity_UserSeq(vo.getAdvertiserSeq(), vo.getUserSeqList().get(0));

        if (advertiserUserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        return userService.userEnable(vo.getUserSeqList().get(0));

    }


    @Transactional
    public UserVo advertiserUserDisable(AdvertiserUserStatusVo vo) {

        Optional<AdvertiserUserEntity> advertiserUserEntityOptional = advertiserUserRepository.findAdvertiserUserEntitiesByAdvertiserEntity_AdvertiserSeqAndUserEntity_UserSeq(vo.getAdvertiserSeq(), vo.getUserSeqList().get(0));

        if (advertiserUserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        return userService.userDisable(vo.getUserSeqList().get(0));

    }
}
