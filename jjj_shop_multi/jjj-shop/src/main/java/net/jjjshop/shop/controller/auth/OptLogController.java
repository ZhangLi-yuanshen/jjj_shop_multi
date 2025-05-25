package net.jjjshop.shop.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.framework.log.annotation.OperationLog;
import net.jjjshop.shop.param.shop.OptLogPageParam;
import net.jjjshop.shop.service.shop.ShopOptLogService;
import net.jjjshop.shop.vo.shop.OptLogVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "setting", tags = {"setting"})
@RestController
@RequestMapping("/shop/auth/optlog")
public class OptLogController {

    @Autowired
    private ShopOptLogService shopOptLogService;

    @PostMapping("/index")
    @RequiresPermissions("/auth/optlog/index")
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Paging<OptLogVo>> index(@RequestBody OptLogPageParam optLogPageParam) throws Exception{
        return ApiResult.ok(shopOptLogService.getList(optLogPageParam));
    }
}
