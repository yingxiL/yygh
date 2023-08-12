package com.atguigu.yygh.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.service.HospSetService;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("admin/hosp/hospSet")
public class HospSetController {
    @Autowired
    private HospSetService hospSetService;
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public List<HospitalSet> findAll() {
        //调用service的方法
        System.out.println("1、获取所有医院设置");
        return hospSetService.list();
    }

    @ApiOperation(value = "条件查询")
    @GetMapping("SelectObj")
    public Result SelectObj(@PathVariable HospitalQueryVo hospitalQueryVo){
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("林",hospitalQueryVo.getHosname());
        List<HospitalSet> list = hospSetService.list(queryWrapper);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据ID获取医院设置")
    @GetMapping("findById/{id}")
    public HospitalSet findById(@PathVariable int id) {
        //调用service的方法
        HospitalSet hospitalSet = hospSetService.getById(id);
        System.out.println(hospitalSet);
        return hospitalSet;
    }

    @ApiOperation(value = "获取所有医院设置总记录")
    @GetMapping("selectCount")
    public long selectCount(){
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
       long count = hospSetService.count(queryWrapper);
       return count;
    }

    @ApiOperation(value = "添加医院设置")
    //4 添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service
        boolean save = hospSetService.save(hospitalSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "根据ID删除医院设置")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        boolean b = hospSetService.removeById(id);
        System.out.println(b);
        return Result.ok();
    }

    @ApiOperation(value = "更新医院设置")
    @PostMapping("/update")
    public Result updateById(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospSetService.updateById(hospitalSet);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "批量删除医院设置")
    //7 批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospSetService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "分页查询医院设置")
    @GetMapping("selectPage/{current}/{size}")
    public Result selectPage(@PathVariable long current,@PathVariable long size){
        Page<HospitalSet> page = new Page<>(current,size);
        long total = page.getTotal();
        System.out.println(total);
        Page<HospitalSet> hospitalSetPage= hospSetService.page(page,null);;
        return Result.ok(hospitalSetPage);
    }

    @ApiOperation(value = "条件查询带分页")
    //3 条件查询带分页
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody (required = false) HospitalQueryVo hospitalQueryVo) {
        //current为当前页，limit为每页显示个数，hospitalQueryVo为封装的查询条件
        List<HospitalSet> list = hospSetService.myQuery(current,limit,hospitalQueryVo);
        return Result.ok(list);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("selectByPage/{current}/{limit}")
    public Result selectByPage(@PathVariable int current,@PathVariable int limit){
        Page<HospitalSet> page = new Page<>(current,limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        return Result.ok(hospSetService.page(page,queryWrapper));
    }

    @ApiOperation(value = "医院设置锁定和解锁")
    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospSetService.updateById(hospitalSet);
        return Result.ok();
    }

    @ApiOperation(value = "发送签名秘钥")
    //9 发送签名秘钥
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }

}
