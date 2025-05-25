package net.jjjshop.shop.controller.order;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.log.annotation.OperationLog;
import net.jjjshop.shop.param.order.OrderPageParam;
import net.jjjshop.shop.service.order.OrderService;
import net.jjjshop.shop.vo.order.OrderVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Api(value = "订单管理", tags = {"订单管理"})
@RestController
@RequestMapping("/shop/order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @OperationLog(name = "index")
    @PostMapping("/index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Map<String,Object>> index(@Validated @RequestBody OrderPageParam orderPageParam) throws Exception{
        Map<String,Object> result = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payment", orderService.getCount("payment"));
        jsonObject.put("delivery", orderService.getCount("delivery"));
        jsonObject.put("received", orderService.getCount("received"));
        result.put("orderList", orderService.getList(orderPageParam));
        result.put("orderCount", jsonObject);
        result.put("deliveryList", orderService.getDeliveryList());
        return ApiResult.ok(result);
    }


    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @RequiresPermissions("/order/order/detail")
    @OperationLog(name = "detail")
    @ApiOperation(value = "detail", response = String.class)
    public ApiResult<OrderVo> detail(Integer orderId) throws Exception{
        return ApiResult.ok(orderService.detail(orderId));
    }


}
