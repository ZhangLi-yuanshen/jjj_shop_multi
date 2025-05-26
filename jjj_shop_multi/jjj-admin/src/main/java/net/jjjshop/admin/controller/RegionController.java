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

    /*地区列表*/
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Map<String,Object>> index(@Validated @RequestBody RegionPageParam regionPageParam) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",regionService.getList(regionPageParam));
        map.put("regionData", regionCache.getCacheTree());
        return ApiResult.ok(map);
    }

    /*增加功能*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @OperationLog(name = "add")
    @ApiOperation(value = "add", response = String.class)
    public ApiResult<String> add(@Validated @RequestBody RegionParam regionParam) {
        if (regionService.add(regionParam)) {
            return ApiResult.ok(null,"新增成功");
        }else {
            return ApiResult.fail("新增失败");
        }
    }
    /*这个接口是打开添加页面时，把已有的地区层级数据喂给前端，方便选择新地区的上级是谁*/
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @OperationLog(name = "add")
    @ApiOperation(value = "add", response = String.class)
    public ApiResult<List<RegionVo>> toAdd() {
        return ApiResult.ok(regionCache.getCacheTree());
    }

    /*删除功能*/
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @OperationLog(name = "delete")
    @ApiOperation(value = "delete",response = String.class)
    public ApiResult<String> delete(Integer id){
        if(regionService.delete(id)){
            return ApiResult.ok(null,"删除成功");
        } else {
            return ApiResult.fail("删除失败");
        }
    }

    /*修改功能*/
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @OperationLog(name = "edit")
    @ApiOperation(value = "edit", response = String.class)
    public ApiResult<String> edit(@RequestBody @Validated RegionParam regionParam) {
        if(regionService.update(regionParam)) {
            return ApiResult.ok(null, "修改成功");
        }else{
            return ApiResult.fail("修改失败");
        }
    }

    /*编辑功能*/
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    @OperationLog(name = "edit")
    @ApiOperation(value = "edit",response = String.class)
    public ApiResult<Map<String,Object>> toEdit(Integer id){
        Map<String,Object> map = new HashMap<>();
        map.put("model", regionService.getEditData(id));
        map.put("regionData", regionCache.getCacheTree());
        return ApiResult.ok(map,"编辑成功");
    }
}
