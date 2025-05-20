package net.jjjshop.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.admin.param.AppPageParam;
import net.jjjshop.admin.param.RegionPageParam;
import net.jjjshop.admin.param.RegionParam;
import net.jjjshop.admin.service.RegionSevice;
import net.jjjshop.common.cache.RegionCache;
import net.jjjshop.common.vo.RegionVo;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.log.annotation.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "access", tags = {"access"})
@RestController
@RequestMapping("/admin/region")
public class RegionController {
    @Autowired
    private RegionSevice regionService;
    @Autowired
    private RegionCache regionCache;
    @RequestMapping("test")
    public String test(){
        System.out.println(regionCache.getCache());
        return null;
    }
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Map<String,Object>> index(@Validated @RequestBody RegionPageParam regionPageParam) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",regionService.getList(regionPageParam));
        map.put("regionData", regionCache.getCacheTree());
        return ApiResult.ok(map);
    }

}
