package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.College;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.complex.CollegeAdvice;
import top.ostp.web.service.CollegeService;

import java.util.List;

@Controller(value = "/college")
public class CollegeController {
    CollegeService collegeService;

    @Autowired
    public void setCollegeService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @AuthAdmin
    @PostMapping(value = "/college/insert")
    @ResponseBody
    public ApiResponse<Object> insertCollege(College college) {
        return collegeService.insert(college);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/college/select")
    @ResponseBody
    public ApiResponse<College> selectCollege(String name) {
        return collegeService.selectByName(name);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/college/get")
    @ResponseBody
    public ApiResponse<College> getCollege(int id) {
        return collegeService.selectById(id);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/college/get_major")
    @ResponseBody
    public ApiResponse<College> getCollegeFromMajorId(int id) {
        return collegeService.selectByMajorId(id);
    }

    @AuthAdmin
    @PostMapping(value = "college/delete")
    @ResponseBody
    public ApiResponse<Object> deleteCollege(int id) {
        return collegeService.deleteById(id);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "college/list")
    @ResponseBody
    public ApiResponse<List<CollegeAdvice>> selectAllCollege() {
        return collegeService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "college/fuzzy")
    @ResponseBody
    public ApiResponse<List<College>> fuzzy(String name) {
        return collegeService.fuzzy(name);
    }
}
