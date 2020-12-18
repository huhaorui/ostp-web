package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.CollegeMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.College;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

@Service
public class CollegeService {
    CollegeMapper collegeMapper;

    @Autowired
    public void setCollegeMapper(CollegeMapper collegeMapper) {
        this.collegeMapper = collegeMapper;
    }

    public ApiResponse<Object> insert(College college) {
        try {
            int result = collegeMapper.insert(college);
            return Responses.ok();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }

    public ApiResponse<College> selectByName(String name) {
        return Responses.ok(collegeMapper.selectByName(name));
    }

    public ApiResponse<Object> deleteById(int id) {
        int result = collegeMapper.deleteById(id);
        if (result == 1) {
            return Responses.ok();
        } else {
            return Responses.fail("删除失败");
        }
    }

}