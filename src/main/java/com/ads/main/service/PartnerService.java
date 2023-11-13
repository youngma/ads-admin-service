package com.ads.main.service;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.PartnerEntity;
import com.ads.main.entity.PartnerUserEntity;
import com.ads.main.entity.UserEntity;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.entity.mapper.PartnerConvert;
import com.ads.main.entity.mapper.PartnerUserConvert;
import com.ads.main.entity.mapper.UserConverter;
import com.ads.main.repository.jpa.PartnerRepository;
import com.ads.main.repository.jpa.PartnerUserRepository;
import com.ads.main.repository.querydsl.QPartnerRepository;
import com.ads.main.repository.querydsl.QPartnerUserRepository;
import com.ads.main.service.FileService;
import com.ads.main.service.UserService;
import com.ads.main.vo.FilesVo;
import com.ads.main.vo.partner.PartnerBusinessModifyVo;
import com.ads.main.vo.partner.PartnerModifyVo;
import com.ads.main.vo.partner.PartnerSearchVo;
import com.ads.main.vo.partner.PartnerVo;
import com.ads.main.vo.partner.user.*;
import com.ads.main.vo.admin.UserVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ads.main.core.enums.exception.PartnerException.PARTNER_ALREADY_EXISTS;
import static com.ads.main.core.enums.exception.PartnerException.PARTNER_NOT_FOUND;
import static com.ads.main.core.enums.exception.UserException.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerService {
    private final PartnerUserRepository partnerUserRepository;


    private final PartnerConvert partnerConvert;
    private final PartnerUserConvert partnerUserConvert;
    private final UserConverter userConverter;
    private final FilesConverter filesConverter;

    private final PartnerRepository partnerRepository;

    private final UserService userService;
    private final FileService fileService;

    private final QPartnerRepository qPartnerRepository;
    private final QPartnerUserRepository qPartnerUserEntityRepository;

    private PageMapper<PartnerVo, PartnerEntity> pageMapper;
    private PageMapper<UserVo, UserEntity> userPageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper = new PageMapper<>(partnerConvert);
        userPageMapper = new PageMapper<>(userConverter);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerEntity> findPartnerBySeq(Long partnerSeq) {
        return partnerRepository.findPartnerEntityByPartnerSeq(partnerSeq);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerEntity> findPartnerByBusinessNumber(String businessNumber) {
        return partnerRepository.findPartnerEntityByBusinessNumber(businessNumber);
    }

    @Transactional(readOnly = true)
    public PartnerVo findPartner(long partnerSeq) {
        PartnerEntity advertiserEntity = findPartnerBySeq(partnerSeq)
                .orElseThrow(PARTNER_NOT_FOUND::throwErrors);
        return partnerConvert.toDto(advertiserEntity);
    }

    @Transactional(readOnly = true)
    public Page<PartnerVo> findAdvertisers(PartnerSearchVo partnerSearchVo, Pageable pageable) {
        Page<PartnerEntity> partnerEntities = qPartnerRepository.searchPartners(partnerSearchVo, pageable);
        return pageMapper.convert(partnerEntities);
    }

//    public AdvertiserUsersVo findAdvertisersUsers(Long partnerSeq) {
//
//        List<AdvertiserUserEntity> advertiserUserEntities = qPartnerRepository.findAdvertiserUsers(partnerSeq);
//
//        AdvertiserUsersVo advertiserUsers = new AdvertiserUsersVo();
//        if (!advertiserUserEntities.isEmpty()){
//            advertiserUsers.setAdvertiser(advertiserConvert.toDto(advertiserUserEntities.get(0).getAdvertiserEntity()));
//            advertiserUserEntities.forEach(advertiserUserEntity -> {
//                advertiserUsers.addUser(userConverter.toDto(advertiserUserEntity.getUserEntity()));
//            });
//        }
//
//        return advertiserUsers;
//    }

    @Transactional(readOnly = true)
    public boolean checkBusinessNumber(String businessNumber) {
        return findPartnerByBusinessNumber(businessNumber).isPresent();
    }

    @Transactional
    public PartnerVo register(PartnerVo partnerVo) {

        if (checkBusinessNumber(partnerVo.getBusinessNumber())) {
            throw USER_ALREADY_EXISTS.throwErrors();
        }

        FilesVo filesVo = partnerVo.getFile();
        fileService.move(filesVo, "사업자 등록증");

        PartnerEntity advertiserEntity = partnerConvert.toEntity(partnerVo);
        partnerRepository.save(advertiserEntity);

        return partnerConvert.toDto(advertiserEntity);
    }

    @Transactional
    public PartnerVo modify(PartnerModifyVo modifyVo) {

        log.info("# step 1");
        Optional<PartnerEntity> partnerEntityOptional = findPartnerBySeq(modifyVo.getPartnerSeq());
        log.info("# step 2");

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        log.info("# step 3");


        PartnerEntity advertiserEntity = partnerEntityOptional.get();
        partnerConvert.updateFromDto(modifyVo, advertiserEntity);
        log.info("# step 4");

        partnerRepository.save(advertiserEntity);
        log.info("# step 5");

        return partnerConvert.toDto(advertiserEntity);
    }


    @Transactional
    public PartnerVo businessNumberModify(PartnerBusinessModifyVo modifyVo) {
        Optional<PartnerEntity> partnerEntityOptional = findPartnerByBusinessNumber(modifyVo.getBusinessNumber());

        if (partnerEntityOptional.isPresent()) {
            throw PARTNER_ALREADY_EXISTS.throwErrors();
        }

        partnerEntityOptional = findPartnerBySeq(modifyVo.getPartnerSeq());

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();
        partnerConvert.updateFromDto(modifyVo, partnerEntity);

        partnerRepository.save(partnerEntity);

        return partnerConvert.toDto(partnerEntity);
    }

    // 등록된 사용자 유무 체크
    @Transactional(readOnly = true)
    public boolean userCheck(long partnerSeq, String userid) {
        try {
            userService.registerValid(Role.PARTNER, partnerSeq , userid);
            return false; // 등록된 사용자 가 없음
        } catch (Exception e) {
            return true; // 등록된 사용자 가 있음
        }
    }


    @Transactional(readOnly = true)
    public Page<UserVo> findPartnerUsers(PartnerUserSearchVo partnerSearchVo, Pageable pageable) {
        return userPageMapper.convert(qPartnerUserEntityRepository.findAdvertiserUsers(partnerSearchVo, pageable));
    }


    @Transactional
    public PartnerUserVo registerPartnerUser(PartnerUserRegisterVo registerVo) {

        Optional<PartnerEntity> partnerEntityOptional = findPartnerBySeq(registerVo.getPartnerSeq());

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();

        UserVo userVo = registerVo.toUser(Role.PARTNER, UserStatus.Enable);

        // 광고주 사용자 등록 validation
        userService.registerValid(Role.PARTNER, registerVo.getPartnerSeq(), userVo.getUserId());

//        partnerEntity.addUser(userConverter.toEntity(userVo));
        partnerEntity.addUser(userService.convertUser(userVo));


        // 광고주 - 사용자 맵핑 정보 저장.
        return partnerUserConvert.toDtoList(partnerUserRepository.saveAll(partnerEntity.getPartnerUserEntities()))
                .stream()
                .filter(t -> t.getUser().getUserId().equals(userVo.getUserId()))
                .findFirst().get();
    }

    @Transactional
    public UserVo modifyPartnerUser(PartnerUserModifyVo modifyVo) {

        Optional<PartnerEntity> partnerEntityOptional = findPartnerBySeq(modifyVo.getPartnerSeq());

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        return userService.modify(modifyVo);

    }

    @Transactional
    public UserVo partnerUserEnable(PartnerUserStatusVo vo) {

        Optional<PartnerUserEntity> partnerEntityOptional = partnerUserRepository.findPartnerUserEntitiesByPartnerEntity_PartnerSeqAndUserEntity_UserSeq(vo.getPartnerSeq(), vo.getUserSeqList().get(0));

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        return userService.userEnable(vo.getUserSeqList().get(0));

    }


    @Transactional
    public UserVo partnerUserDisable(PartnerUserStatusVo vo) {

        Optional<PartnerUserEntity> partnerEntityOptional = partnerUserRepository.findPartnerUserEntitiesByPartnerEntity_PartnerSeqAndUserEntity_UserSeq(vo.getPartnerSeq(), vo.getUserSeqList().get(0));

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        return userService.userDisable(vo.getUserSeqList().get(0));

    }
}
