package com.tailYY.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tailYY.backend.common.constants.ErrorCode;
import com.tailYY.backend.common.response.BaseResponse;
import com.tailYY.backend.common.util.ResultUtils;
import com.tailYY.backend.model.Class;
import com.tailYY.backend.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author daken 2025/1/11
 **/
@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController {

    @Resource
    private ClassService classService;

    /**
     * 查询类别
     */
    @GetMapping("/getClassList")
    public BaseResponse<List<Class>> getClassList(Class cl) {
        log.info("text,{}", cl);
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>(cl);
        List<Class> classList = classService.list(queryWrapper);
        return ResultUtils.success(classList);
    }

    /**
     * 删除类别
     */
    @PostMapping("/deleteClass")
    public BaseResponse<Boolean> deleteClass(@RequestBody Class classId) {
        if (classId == null || classId == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(classService.removeById(classId.getId()));
    }

    /**
     * 添加类别
     */
    @PostMapping("/addClass")
    public BaseResponse<Boolean> addClass(@RequestBody Class cl) {
        if (StringUtils.isAnyBlank(cl.getClassName(), cl.getClassType())) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(classService.save(cl));
    }

    /**
     * 编辑类别
     */
    @PostMapping("/editClass")
    public BaseResponse<Boolean> updateClass(@RequestBody Class cl) {
        if (cl.getId() == null || StringUtils.isAnyBlank(cl.getClassName(), cl.getClassType())) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        if (cl.getIsdelete() == 1) {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "请检查类别的状态");
        }
        return ResultUtils.success(classService.updateById(cl));
    }
}
