package com.ads.main.core.utils;


import com.ads.main.core.config.convert.GenericMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;


@AllArgsConstructor
public class PageMapper<Dto, Entity> {

    private GenericMapper<Dto, Entity> convert;

    public Page<Dto> convert(Page<Entity> entities) {
        List<Dto> list  = convert.toDtoList(entities.getContent());
        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

}
