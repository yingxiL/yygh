package com.atguigu.yygh.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.mapper.DictMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.service.DictService;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("admin/cmn/dict")
public class DictController {
    @Autowired
    private DictService dictService;
    @ApiOperation("条件查询带分页数据字典")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPage(@PathVariable long current, @PathVariable long limit,
                           @RequestBody( required = false ) HospitalQueryVo hospitalQueryVo) {
        Page<Dict> page = new Page<>(current,limit);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalQueryVo.getHosname();
        String hoscode = hospitalQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname)){
            queryWrapper.like("hosname",hospitalQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)){
            queryWrapper.eq("hoscode",hospitalQueryVo.getHoscode());
        }
        Page<Dict> pageDict = dictService.page(page,queryWrapper);
        return Result.ok(pageDict);
    }

    @ApiOperation("根据ID查询字典")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable long id) {
        List<Dict> list = dictService.selectParentId(id);
        return Result.ok(list);
    }

    @ApiOperation(value = "导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        dictService.importDictData(file);
        return Result.ok();
    }

    @ApiOperation(value="导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        dictService.exportData(response);
    }
}
