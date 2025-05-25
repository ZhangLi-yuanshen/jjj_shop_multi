package net.jjjshop.shop.controller.setting;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.settings.ReturnAddress;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.framework.log.annotation.OperationLog;
import net.jjjshop.shop.param.setting.ReturnAddressPageParam;
import net.jjjshop.shop.param.setting.ReturnAddressParam;
import net.jjjshop.shop.service.settings.ReturnAddressService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "address", tags = {"address"})
@RestController
@RequestMapping("/shop/setting/address")
public class ReturnAddressController {

    @Autowired
    private ReturnAddressService returnAddressService;

    @PostMapping("/index")
    @RequiresPermissions("/setting/address/index")
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Paging<ReturnAddress>> index(@RequestBody @Validated ReturnAddressPageParam returnAddressPageParam) throws Exception {
        return ApiResult.ok(returnAddressService.getList(returnAddressPageParam));
    }

    @PostMapping("/add")
    @RequiresPermissions("/setting/address/add")
    @OperationLog(name = "add")
    @ApiOperation(value = "add", response = String.class)
    public ApiResult<String> add(@RequestBody @Validated ReturnAddressParam returnAddressParam) {
        if (returnAddressService.add(returnAddressParam)) {
            return ApiResult.ok(null, "添加成功");
        } else {
            return ApiResult.fail("添加失败");
        }

    }

    @GetMapping("/toEdit")
    @RequiresPermissions("/setting/address/edit")
    @OperationLog(name = "edit")
    @ApiOperation(value = "edit", response = String.class)
    public ApiResult<ReturnAddress> toEdit(Integer addressId) {
        return ApiResult.ok(returnAddressService.getById(addressId));
    }

    @PostMapping("/edit")
    @RequiresPermissions("/setting/address/edit")
    @OperationLog(name = "edit")
    @ApiOperation(value = "edit", response = String.class)
    public ApiResult<String> edit(@RequestBody @Validated ReturnAddressParam returnAddressParam) {
        if (returnAddressService.edit(returnAddressParam)) {
            return ApiResult.ok(null, "修改成功");
        } else {
            return ApiResult.fail("修改失败");
        }
    }

    @PostMapping("/delete")
    @RequiresPermissions("/setting/address/delete")
    @OperationLog(name = "delete")
    @ApiOperation(value = "delete", response = String.class)
    public ApiResult<String> setDelete(Integer id) {
        if (returnAddressService.setDelete(id)) {
            return ApiResult.ok("删除成功");
        } else {
            return ApiResult.fail("删除失败");
        }
    }
}
