package net.jjjshop.shop.service.order;


import net.jjjshop.common.entity.order.OrderRefund;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.shop.param.order.OrderRefundPageParam;
import net.jjjshop.shop.param.order.OrderRefundParam;
import net.jjjshop.shop.vo.order.OrderRefundVo;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * 售后单记录表 服务类
 * @author jjjshop
 * @since 2022-07-04
 */
public interface OrderRefundService extends BaseService<OrderRefund> {

    /**
     * 获取售后单数据
     * @param orderRefundPageParam
     * @return
     */
    Integer getCountByStatus(OrderRefundPageParam orderRefundPageParam);

    /**
     * 获取售后订单分页列表
     * @param orderRefundPageParam
     * @return
     */
    Paging<OrderRefundVo> getList(OrderRefundPageParam orderRefundPageParam);

    /**
     * 获取售后订单详情
     * @param orderRefundId
     * @return
     */
    OrderRefundVo detail(Integer orderRefundId);


    /**
     * 获取售后订单分页列表
     * @param orderRefundPageParam
     * @return
     */
    Paging<OrderRefundVo> getPlateList(OrderRefundPageParam orderRefundPageParam);

    /**
     * 获取售后单数据
     * @param orderRefundPageParam
     * @return
     */
    Integer getCountByPlateStatus(OrderRefundPageParam orderRefundPageParam);
}
