package com.qmx.base;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * mapper 父类
 */

public interface IBaseMapper<T extends BaseModel> extends BaseMapper<T> {

    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);
}
