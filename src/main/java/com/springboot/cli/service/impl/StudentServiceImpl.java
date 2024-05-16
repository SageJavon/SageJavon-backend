package com.springboot.cli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.cli.common.enums.OpExceptionEnum;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.common.jwt.AuthStorage;
import com.springboot.cli.model.DO.StudentDO;
import com.springboot.cli.repository.impl.StudentRepository;
import com.springboot.cli.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    StudentRepository studentRepository;

    @Override
    public StudentDO getStuInfo() {
        String studentId = AuthStorage.getUser().getUserId();
        LambdaQueryWrapper<StudentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentDO::getId, studentId);
        StudentDO student = studentRepository.getOne(queryWrapper);
        if(student == null) {
            //新建一条记录并保存
            StudentDO newStudent = StudentDO.builder().id(studentId).build();
            studentRepository.save(newStudent);
            student = newStudent;
        }
        return student;
    }

    @Override
    public void updateStuInfo(StudentDO student) {
        if(student == null) throw  new OpException(OpExceptionEnum.ILLEGAL_ARGUMENT);
        //判断是否传了id
        String studentId = student.getId() == null ? AuthStorage.getUser().getUserId() : student.getId();
        student.setId(studentId);
        //根据id修改student
        studentRepository.updateById(student);
    }
}
