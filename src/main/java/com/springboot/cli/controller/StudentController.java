package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.common.jwt.JwtUser;
import com.springboot.cli.model.DO.StudentDO;
import com.springboot.cli.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("/information")
    public BaseResponse<StudentDO> getStuInfo() {
        JwtUser jwtUser = AuthStorage.getUser();
        log.info("Get student information: studentId = {}", jwtUser.getUserId());
        try {
            return BaseResponse.buildSuccess(studentService.getStuInfo());
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @PostMapping("/information")
    public BaseResponse<Void> updateStuInfo(@RequestBody StudentDO student) {
        JwtUser jwtUser = AuthStorage.getUser();
        log.info("Update student information: studentId = {}", jwtUser.getUserId());
        try {
            studentService.updateStuInfo(student);
            return BaseResponse.buildSuccess(null);
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
