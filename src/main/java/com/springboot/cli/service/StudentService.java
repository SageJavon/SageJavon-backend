package com.springboot.cli.service;

import com.springboot.cli.model.DO.StudentDO;

public interface StudentService {
    StudentDO getStuInfo();

    void updateStuInfo(StudentDO student);
}
