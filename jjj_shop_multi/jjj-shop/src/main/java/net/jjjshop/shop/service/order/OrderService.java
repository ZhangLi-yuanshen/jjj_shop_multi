package net.jjjshop.shop.service.order;


import com.alibaba.fastjson.JSONObject;
import net.jjjshop.common.entity.order.Order;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.shop.param.order.OrderPageParam;
import net.jjjshop.shop.vo.order.OrderVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单记录表 服务类
 * @author jjjshop
 * @since 2022-07-04
 */
public interface OrderService extends BaseService<Order> {

    /**
     * 通过订单状态查询订单数量
     * @return order
     */
    Integer getCount(String dataType);


    /**
     * 订单列表
     * @param orderPageParam
     * @return
     */
    Paging<OrderVo> getList(OrderPageParam orderPageParam);



    /**
     * 获取所有运送方式
     * @param
     * @return
     */
    List<JSONObject> getDeliveryList();

    /**
     * 单个订单信息
     * @param orderId
     * @return
     */
    OrderVo detail(Integer orderId);


    /**
     * 导出订单
     * @param orderPageParam
     * @param httpServletResponse
     * @return
     */
    void exportList(OrderPageParam orderPageParam, HttpServletResponse httpServletResponse) throws Exception;
}
