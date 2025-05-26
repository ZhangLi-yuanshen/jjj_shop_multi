package net.jjjshop.shop.service.order.impl;

import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.order.OrderProduct;
import net.jjjshop.common.mapper.order.OrderProductMapper;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import net.jjjshop.shop.service.order.OrderProductService;
import org.springframework.stereotype.Service;

/**
 * 订单商品记录表 服务实现类
 * @author jjjshop
 * @since 2022-07-04
 */
@Slf4j
@Service
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProductMapper, OrderProduct> implements OrderProductService {
}
