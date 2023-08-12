package com.atguigu.yygh.service.impl;

import com.atguigu.yygh.mapper.HospSetMapper;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.service.HospSetService;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class HospSetServiceImpl extends ServiceImpl<HospSetMapper,HospitalSet> implements HospSetService {
    @Autowired
    private HospSetMapper hospSetMapper;
    @Override
    public List<HospitalSet> myQuery(long current, long limit, HospitalQueryVo hospitalQueryVo) {
        //初始化page
        Page<HospitalSet> page = new Page<>(current,limit);
        //设置条件
        QueryWrapper<HospitalSet> wrapper =new QueryWrapper<>();
        if (hospitalQueryVo == null){
            baseMapper.selectPage(page, wrapper);
            System.out.println("条件为空");
        }
        String hosname = hospitalQueryVo.getHosname();
        String hoscode = hospitalQueryVo.getHoscode();

        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hospitalQueryVo.getHosname());
        }
        if(!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalQueryVo.getHoscode());
        }
        //执行查询
        hospSetMapper.selectPage(page,wrapper);
        long total = page.getTotal();//总数
        List<HospitalSet> list = page.getRecords();//结果
        return list;
    }
}
