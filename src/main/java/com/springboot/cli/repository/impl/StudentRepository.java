package com.springboot.cli.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cli.mapper.StudentMapper;
import com.springboot.cli.model.DO.StudentDO;
import com.springboot.cli.repository.IStudentRepo;
import org.springframework.stereotype.Service;

@Service
public class StudentRepository extends ServiceImpl<StudentMapper, StudentDO> implements IStudentRepo {
}
