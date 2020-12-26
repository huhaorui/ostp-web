package top.ostp.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import top.ostp.web.model.Admin
import top.ostp.web.model.Student
import top.ostp.web.model.Teacher
import top.ostp.web.model.annotations.NoAuthority
import top.ostp.web.model.common.ApiResponse
import top.ostp.web.model.common.Responses
import top.ostp.web.model.common.Tokens.ok
import top.ostp.web.service.LoginService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@NoAuthority
@Controller
class LoginController {
    @Autowired
    lateinit var loginService: LoginService

    @PostMapping(path = ["/login"])
    @ResponseBody
    fun login(id: String?, password: String?, request: HttpServletRequest, res: HttpServletResponse): ApiResponse<Any> {
        val session = request.session
        println("login/session: ${session.id}")
        val response = loginService.login(id, password)
        if (response.code == ok.code && response.data != null) {
            session.setAttribute("username", id)
            session["role"] = response.data
        }
        return response
    }

    @PostMapping("/account/status")
    @ResponseBody
    fun status(request: HttpServletRequest): ApiResponse<Any> {
        val session = request.session

        var role = session["role"]
        println("status/session: ${session.id}")
        return if (role != null) {
            role = loginService.findById(role)
            session["role"] = role
            Responses.ok(role)
        } else {
            Responses.fail("未登录")
        }
    }

    @PostMapping("/account/username")
    @ResponseBody
    fun username(request: HttpServletRequest): ApiResponse<Any> {
        var role = request.session["role"]
        return if (role != null) {
            role = loginService.findById(role)
            request.session["role"] = role
            Responses.ok(role)
        } else {
            Responses.fail("未登录")
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    fun logout(request: HttpServletRequest): ApiResponse<String> {
        request.session.setAttribute("role", null)
        return Responses.ok("logout success: ${request.session.getAttribute("role")}")
    }


    @PostMapping(value = ["/account/duplicate"])
    @ResponseBody
    fun duplicate(id: String): ApiResponse<Boolean> {
        return loginService.checkDuplicate(id)
    }

    private operator fun HttpSession.get(attr: String): Any? {
        return this.getAttribute(attr)
    }

    private operator fun HttpSession.set(attr: String, value: Any?) {
        this.setAttribute(attr, value);
    }
}

