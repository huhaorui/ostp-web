package top.ostp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ostp.web.model.SecondHandFind;
import top.ostp.web.model.annotations.AuthAdmin;
import top.ostp.web.model.annotations.AuthStudent;
import top.ostp.web.model.annotations.AuthTeacher;
import top.ostp.web.model.annotations.NoAuthority;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.service.SecondHandFindService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SecondHandFindController {
    SecondHandFindService secondHandFindService;

    @Autowired
    public void setSecondHandFindService(SecondHandFindService secondHandFindService) {
        this.secondHandFindService = secondHandFindService;
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/select/all")
    @ResponseBody
    public ApiResponse<Object> selectAll() {
        return secondHandFindService.selectAll();
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/select/{id}")
    @ResponseBody
    public ApiResponse<Object> select(@PathVariable String id) {
        return secondHandFindService.select(id);

    }

    @AuthStudent
    @PostMapping(path = "/second/find/selectByStudent")
    @ResponseBody
    public ApiResponse<List<SecondHandFind>> selectByStudentId(String person) {
        return secondHandFindService.selectByStudentId(person);
    }

    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(path = "/second/find/selectByBook")
    @ResponseBody
    public ApiResponse<Object> selectByBook(String isbn) {
        return secondHandFindService.selectByBook(isbn);
    }
    @NoAuthority
    @AuthAdmin
    @AuthStudent
    @AuthTeacher
    @PostMapping(value = "/second/find/insert")
    @ResponseBody
    public ApiResponse<Object> insert(@RequestParam("person") String person, @RequestParam("book") String book, int price, int exchange, int status){
        return secondHandFindService.insert(person, book, price, exchange, status);
    }

    @AuthStudent
    @PostMapping(value = "/second/find/cancel")
    @ResponseBody
    public ApiResponse<Object> cancel(String id, HttpServletRequest request){
        String studentId = (String) request.getSession().getAttribute("username");
        return secondHandFindService.cancel(id, studentId);
    }

}
