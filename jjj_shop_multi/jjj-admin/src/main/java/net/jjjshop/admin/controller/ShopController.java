package net.jjjshop.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.admin.param.AppPageParam;
import net.jjjshop.admin.service.AppService;
import net.jjjshop.admin.vo.AppVo;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.framework.log.annotation.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "test", tags = {"test"})
@RestController
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Paging<AppVo>> index(@Validated @RequestBody AppPageParam appPageParam) {
        return ApiResult.ok(appService.getList(appPageParam));
    }
}
