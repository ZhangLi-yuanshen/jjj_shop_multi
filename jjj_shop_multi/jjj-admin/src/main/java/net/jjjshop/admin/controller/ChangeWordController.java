
package net.jjjshop.admin.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.admin.service.AdminUserService;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.common.api.ApiCode;
//新添加的
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

//修改密码
@Slf4j
@Api(value = "user", tags = {"user"})
@RestController
@RequestMapping("/admin/user")
public class ChangeWordController {

    @Autowired
    private AdminUserService adminUserService;
    //修改密码
    @RequestMapping("/renew")
    public ApiResult<String> renew(@RequestParam String pass, String checkPass, HttpServletRequest request) {
        //是否获取到密码
        System.out.println(pass+ checkPass);
        // 验证两次密码是否一致
        if (StringUtils.isBlank(pass) || StringUtils.isBlank(checkPass)) {
            return ApiResult.fail("密码不能为空");
        }

        if (!pass.equals(checkPass)) {
            return ApiResult.fail("两次输入的密码不一致");
        }

        try {
            boolean result = adminUserService.renew(pass);
            if (result) {
                // 先返回成功状态码，让前端显示成功提示
                ApiResult<String> successResult = ApiResult.ok("密码修改成功，请重新登录");
                // 密码修改成功后，调用退出登录
                adminUserService.logout(request);
                // 修改状态码为未登录
                successResult.setCode(ApiCode.NOT_LOGIN.getCode());
                return successResult;
            } else {
                return ApiResult.fail("密码修改失败");
            }
        } catch (IllegalArgumentException e) {
            return ApiResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("修改密码异常", e);
            return ApiResult.fail("系统错误，修改密码失败");
        }
//        return null;
    }
}
