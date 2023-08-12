package com.atguigu.yygh.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> selectParentId(long id);
    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);
    /**
     * 导入
     * @param response
     */
    void importDictData(MultipartFile file);
}
