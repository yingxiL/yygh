package com.atguigu.yygh.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface HospSetService extends IService<HospitalSet> {
    //
    List<HospitalSet> myQuery(long current, long limit, HospitalQueryVo hospitalQueryVo);
}
