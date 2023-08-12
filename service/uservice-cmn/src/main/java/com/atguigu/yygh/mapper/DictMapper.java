package com.atguigu.yygh.mapper;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
    Page<Dict> selectParentId(long id);
}
