package net.jjjshop.front.controller.supplier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.user.User;
import net.jjjshop.common.service.supplier.SupplierService;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.framework.log.annotation.OperationLog;
import net.jjjshop.front.controller.BaseController;
import net.jjjshop.front.param.order.OrderPageParam;
import net.jjjshop.front.service.order.OrderService;
import net.jjjshop.front.vo.order.OrderListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "store", tags = {"订单核销"})
@RestController
@RequestMapping("/front/supplier/order")
public class SupplierOrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @OperationLog(name = "index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<Paging<OrderListVo>> index(@RequestBody OrderPageParam orderPageParam) throws Exception {
        User user = this.getUser(true);
        orderPageParam.setUserId(user.getUserId());
        return ApiResult.ok(orderService.getList(orderPageParam));
    }

}
