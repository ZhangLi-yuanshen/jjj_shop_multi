package net.jjjshop.shop.controller.setting;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.enums.SettingEnum;
import net.jjjshop.common.util.SettingUtils;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.log.annotation.OperationLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "sms", tags = {"sms"})
@RestController
@RequestMapping("/shop/setting/sms")
public class SmsController {
    @Autowired
    private SettingUtils settingUtils;

    @GetMapping("/index")
    @RequiresPermissions("/setting/sms/index")
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<JSONObject> index() throws Exception{
        JSONObject vo = settingUtils.getSetting(SettingEnum.SMS.getKey(), null);
        return ApiResult.ok(vo);
    }

    @PostMapping("/index")
    @RequiresPermissions("/setting/sms/index")
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<String> index(@RequestBody JSONObject jsonData) throws Exception{
        settingUtils.saveSetting(SettingEnum.SMS.getKey(), jsonData);
        return ApiResult.ok("保存成功");
    }
}
