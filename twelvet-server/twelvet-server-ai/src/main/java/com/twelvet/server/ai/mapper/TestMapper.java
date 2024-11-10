package com.twelvet.server.ai.mapper;

import com.twelvet.api.i18n.domain.I18n;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TestMapper {

    /**
     * 查询国际化
     *
     * @param i18nId 国际化主键
     * @return 国际化
     */
    @Select("SELECT * FROM `tb_test`")
    List<Map<String, Object>> selectI18nByI18nId();

}
