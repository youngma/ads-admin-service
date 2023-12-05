package com.ads.main.core.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ExcelSupportV1 {

    void download(Class<?> clazz, List<?> data, String fileName, HttpServletResponse response);
}
