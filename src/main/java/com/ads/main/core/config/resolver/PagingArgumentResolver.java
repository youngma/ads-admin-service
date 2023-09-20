package com.ads.main.core.config.resolver;

import com.ads.main.core.config.aspect.anno.PageParameter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PagingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class)
                && parameter.hasParameterAnnotation(PageParameter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String page = webRequest.getParameter("page");
        String size = webRequest.getParameter("size");

        int PAGE = 0;
        int SIZE = 20;

        return PageRequest.of(NumberUtils.toInt(page, PAGE) - 1, NumberUtils.toInt(size, SIZE));
    }
}
