package net.jjjshop.shop.service.order.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.order.Order;
import net.jjjshop.common.entity.order.OrderAddress;
import net.jjjshop.common.entity.order.OrderExtract;
import net.jjjshop.common.entity.order.OrderProduct;
import net.jjjshop.common.entity.store.Store;
import net.jjjshop.common.entity.user.User;
import net.jjjshop.common.enums.DeliveryTypeEnum;
import net.jjjshop.common.enums.OrderPayTypeEnum;
import net.jjjshop.common.enums.OrderSourceEnum;
import net.jjjshop.common.mapper.order.OrderMapper;
import net.jjjshop.common.service.order.OrderProductService;
import net.jjjshop.common.service.settings.RegionService;
import net.jjjshop.common.service.supplier.SupplierService;
import net.jjjshop.common.service.user.UserService;
import net.jjjshop.common.util.OrderUtils;
import net.jjjshop.common.util.UploadFileUtils;
import net.jjjshop.common.vo.order.OrderProductVo;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import net.jjjshop.framework.core.pagination.PageInfo;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.shop.param.order.OrderPageParam;
import net.jjjshop.shop.service.export.ExportService;
import net.jjjshop.shop.service.order.OrderAddressService;
import net.jjjshop.shop.service.order.OrderExtractService;
import net.jjjshop.shop.service.order.OrderService;
import net.jjjshop.shop.service.settings.ExpressService;
import net.jjjshop.shop.service.store.StoreClerkService;
import net.jjjshop.shop.service.store.StoreService;
import net.jjjshop.shop.vo.order.OrderAddressVo;
import net.jjjshop.shop.vo.order.OrderVo;
import net.jjjshop.shop.vo.store.ExtractStoreVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单记录表 服务实现类
 * @author jjjshop
 * @since 2022-07-04
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private UploadFileUtils uploadFileUtils;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private OrderAddressService orderAddressService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private StoreClerkService storeClerkService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private OrderExtractService orderExtractService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private ExportService exportService;

    /**
     * 通过订单状态查询订单数量
     * @param dataType
     * @return
     */
    public Integer getCount(String dataType) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper = this.transferDataType(wrapper, dataType);
        wrapper.eq(Order::getIsDelete, 0);
        return this.count(wrapper);
    }

    /**
     * 订单列表
     * @param orderPageParam
     * @return
     */
    public Paging<OrderVo> getList(OrderPageParam orderPageParam) {
        Page<Order> page = new PageInfo<>(orderPageParam);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper = this.transWrapper(wrapper, orderPageParam);
        IPage<Order> iPage = this.page(page, wrapper);
        IPage<OrderVo> resultPage = iPage.convert(item -> {
            OrderVo vo = new OrderVo();
            BeanUtil.copyProperties(item, vo);
            vo.setNickname(userService.getById(vo.getUserId()).getNickname());
            vo.setProductList(this.getProductList(vo.getOrderId()));
            vo.setOrderSourceText(OrderSourceEnum.getName(vo.getOrderSource()));
            vo.setOrderStatusText(OrderUtils.getOrderStatusText(item));
            vo.setPayTypeText(OrderPayTypeEnum.getName(vo.getPayType()));
            vo.setDeliveryTypeText(DeliveryTypeEnum.getName(vo.getDeliveryType()));
            if(vo.getShopSupplierId() != null && vo.getShopSupplierId() > 0) {
                vo.setSupplierName(supplierService.getById(vo.getShopSupplierId()).getName());
            }
            return vo;
        });
        return new Paging(resultPage);
    }

    /**
     * 获取所有运送方式
     * @param
     * @return
     */
    public List<JSONObject> getDeliveryList() {
        return DeliveryTypeEnum.getDeliveryList();
    }



    /**
     * 获取不同状态的订单
     * @param wrapper
     * @param dataType
     * @return
     */
    private LambdaQueryWrapper<Order> transferDataType(LambdaQueryWrapper<Order> wrapper, String dataType) {
        switch (dataType) {
            case "all":
                break;
            case "payment":
                wrapper.eq(Order::getPayStatus, 10);
                wrapper.eq(Order::getOrderStatus, 10);
                break;
            case "delivery":
                wrapper.eq(Order::getPayStatus, 20);
                wrapper.eq(Order::getOrderStatus, 10);
                wrapper.eq(Order::getDeliveryStatus, 10);
                break;
            case "received":
                wrapper.eq(Order::getPayStatus, 20);
                wrapper.eq(Order::getOrderStatus, 10);
                wrapper.eq(Order::getDeliveryStatus, 20);
                wrapper.eq(Order::getReceiptStatus, 10);
                break;
            case "comment":
                wrapper.eq(Order::getIsComment, 0);
                wrapper.eq(Order::getOrderStatus, 30);
                break;
            case "six":
                wrapper.eq(Order::getIsComment, 1);
                wrapper.eq(Order::getOrderStatus, 30);
                break;
            case "cancel":
                wrapper.eq(Order::getOrderStatus, 20);
                break;
            case "cancelApply":
                wrapper.eq(Order::getOrderStatus, 21);
                break;
        }
        return wrapper;
    }


    /**
     * 获取订单查询条件
     * @param wrapper
     * @param orderPageParam
     * @return
     */
    public LambdaQueryWrapper<Order> transWrapper(LambdaQueryWrapper<Order> wrapper, OrderPageParam orderPageParam) {
        wrapper = this.transferDataType(wrapper, orderPageParam.getDataType());
        wrapper.eq(Order::getIsDelete, false);
        wrapper.orderByDesc(Order::getCreateTime);
        //根据订单号查询
        if (StringUtils.isNotEmpty(orderPageParam.getOrderNo())) {
            List<Integer> list = this.list(new LambdaQueryWrapper<Order>().like(Order::getOrderNo, orderPageParam.getOrderNo()))
                    .stream().map(e -> {
                        return e.getOrderId();
                    }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(list)) {
                wrapper.in(Order::getOrderId, list);
            } else {
                wrapper.eq(Order::getOrderId, -1);
                return wrapper;
            }
        }
        //查询参数：开始时间
        if (StringUtils.isNotEmpty(orderPageParam.getStartDate())) {
            Date startTime = DateUtil.parse(orderPageParam.getStartDate() + " 00:00:00");
            wrapper.ge(Order::getCreateTime, startTime);
        }
        //查询参数：结束时间
        if (StringUtils.isNotEmpty(orderPageParam.getEndDate())) {
            Date endTime = DateUtil.parse(orderPageParam.getEndDate() + " 23:59:59");
            wrapper.le(Order::getCreateTime, endTime);
        }
        //查询参数：订单运送类型
        if (orderPageParam.getDeliveryType() != null && orderPageParam.getDeliveryType() > 0) {
            wrapper.eq(Order::getDeliveryType, orderPageParam.getDeliveryType());
        }
        return wrapper;
    }


    /**
     * 获取订单内所有商品
     * @param orderId
     * @return
     */
    private List<OrderProductVo> getProductList(Integer orderId) {
        List<OrderProduct> list = orderProductService.list(new LambdaQueryWrapper<OrderProduct>()
                .eq(OrderProduct::getOrderId, orderId).orderByAsc(OrderProduct::getOrderProductId));
        return list.stream().map(e -> {
            OrderProductVo vo = new OrderProductVo();
            BeanUtils.copyProperties(e, vo);
            vo.setImagePath(uploadFileUtils.getFilePath(vo.getImageId()));
            return vo;
        }).collect(Collectors.toList());
    }
    /**
     * 单个订单信息
     * @param orderId
     * @return
     */
    public OrderVo detail(Integer orderId) {
        Order order = this.getById(orderId);
        OrderVo vo = new OrderVo();
        BeanUtils.copyProperties(order, vo);
        User user = userService.getById(vo.getUserId());
        //设置昵称
        vo.setNickname(user.getNickname());
        vo.setMobile(user.getMobile());
        vo.setProductList(this.getProductList(vo.getOrderId()));
        vo.setOrderSourceText(OrderSourceEnum.getName(vo.getOrderSource()));
        vo.setOrderStatusText(OrderUtils.getOrderStatusText(order));
        vo.setPayTypeText(OrderPayTypeEnum.getName(vo.getPayType()));
        vo.setDeliveryTypeText(DeliveryTypeEnum.getName(vo.getDeliveryType()));
        //10未付款 20已付款
        vo.setPayStatusText(vo.getPayStatus() == 10 ? "未付款" : "已付款");
        //10未收货 20已收货
        vo.setDeliveryStatusText(vo.getDeliveryStatus() == 10 ? "未发货" : "已发货");
        //10未收货 20已收货
        vo.setReceiptStatusText(vo.getReceiptStatus() == 10 ? "未收货" : "已收货");
        //设置后台修改价格
        if (vo.getUpdatePrice().compareTo(BigDecimal.ZERO) > 0) {
            vo.setUpdatePriceSymbol("+");
        } else if (vo.getUpdatePrice().compareTo(BigDecimal.ZERO) < 0) {
            vo.setUpdatePriceSymbol("-");
        } else {
            vo.setUpdatePriceSymbol("");
        }
        if (vo.getDeliveryType().intValue() == DeliveryTypeEnum.EXPRESS.getValue().intValue()) {
            OrderAddressVo orderAddressVo = new OrderAddressVo();
            OrderAddress oa = orderAddressService.getOne(new LambdaQueryWrapper<OrderAddress>().eq(OrderAddress::getOrderId, orderId));
            BeanUtils.copyProperties(oa, orderAddressVo);
            //设置详细收货地址
            JSONObject detailRegion = new JSONObject();
            detailRegion.put("province", regionService.getById(orderAddressVo.getProvinceId()).getName());
            detailRegion.put("city", regionService.getById(orderAddressVo.getCityId()).getName());
            detailRegion.put("region", regionService.getById(orderAddressVo.getRegionId()).getName());
            orderAddressVo.setRegion(detailRegion);
            vo.setAddress(orderAddressVo);
        } else if (vo.getDeliveryType().intValue() == DeliveryTypeEnum.EXTRACT.getValue().intValue()) {
            if (vo.getExtractClerkId() != null && vo.getExtractClerkId() > 0) {
                //如果已经有了自提门店店员信息，直接设置自提门店名称和自提门店店员
                vo.setExtractClerkName(storeClerkService.getById(vo.getExtractClerkId()).getRealName());
                vo.setExtractStoreName(storeService.getById(vo.getExtractStoreId()).getStoreName());
            } else {
                //如果没有则传入门店信息和门店店员列表
                Store store = storeService.getById(vo.getExtractStoreId());
                ExtractStoreVo extractStoreVo = new ExtractStoreVo();
                BeanUtils.copyProperties(store, extractStoreVo);
                vo.setExtractStore(extractStoreVo);
                vo.setShopClerkList(storeClerkService.getClerkByStoreId(vo.getExtractStoreId()));
            }
            OrderExtract orderExtract = orderExtractService.getOne(new LambdaQueryWrapper<OrderExtract>().eq(OrderExtract::getOrderId, orderId));
            vo.setOrderExtract(orderExtract);
        }
        if (vo.getDeliveryStatus() == 20) {
            vo.setExpress(expressService.getById(vo.getExpressId()));
        } else {
            vo.setExpressList(expressService.getAll());
        }
        return vo;
    }
    /**
     * 获取所有订单
     * @param orderPageParam
     * @return
     */
    private List<OrderVo> getListAll(OrderPageParam orderPageParam) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper = this.transWrapper(wrapper, orderPageParam);
        List<OrderVo> result = this.list(wrapper).stream().map(e -> {
            OrderVo vo = new OrderVo();
            BeanUtil.copyProperties(e, vo);
            vo.setNickname(userService.getById(vo.getUserId()).getNickname());
            vo.setProductList(this.getProductList(vo.getOrderId()));
            vo.setOrderSourceText(OrderSourceEnum.getName(vo.getOrderSource()));
            vo.setOrderStatusText(OrderUtils.getOrderStatusText(e));
            vo.setPayTypeText(OrderPayTypeEnum.getName(vo.getPayType()));
            vo.setDeliveryTypeText(DeliveryTypeEnum.getName(vo.getDeliveryType()));
            //如果是快递配送
            if (vo.getDeliveryType().intValue() == DeliveryTypeEnum.EXPRESS.getValue().intValue()) {
                OrderAddressVo orderAddressVo = new OrderAddressVo();
                OrderAddress oa = orderAddressService.getOne(new LambdaQueryWrapper<OrderAddress>().eq(OrderAddress::getOrderId, vo.getOrderId()));
                BeanUtils.copyProperties(oa, orderAddressVo);
                //设置详细收货地址
                JSONObject detailRegion = new JSONObject();
                detailRegion.put("province", regionService.getById(orderAddressVo.getProvinceId()).getName());
                detailRegion.put("city", regionService.getById(orderAddressVo.getCityId()).getName());
                detailRegion.put("region", regionService.getById(orderAddressVo.getRegionId()).getName());
                orderAddressVo.setRegion(detailRegion);
                vo.setAddress(orderAddressVo);
                //如果是自提配送
            } else if (vo.getDeliveryType().intValue() == DeliveryTypeEnum.EXTRACT.getValue().intValue()) {
                if (vo.getExtractClerkId() != null && vo.getExtractClerkId() > 0) {
                    //如果选择了店员，设置相应的店员信息
                    vo.setExtractClerkName(storeClerkService.getById(vo.getExtractClerkId()).getRealName());
                    vo.setExtractStoreName(storeService.getById(vo.getExtractStoreId()).getStoreName());
                } else if (vo.getExtractStoreId() != null && vo.getExtractStoreId() > 0) {
                    //如果设置了自提门店，设置相应的自提门店信息
                    Store store = storeService.getById(vo.getExtractStoreId());
                    ExtractStoreVo extractStoreVo = new ExtractStoreVo();
                    BeanUtils.copyProperties(store, extractStoreVo);
                    vo.setExtractStore(extractStoreVo);
                    vo.setShopClerkList(storeClerkService.getClerkByStoreId(vo.getExtractStoreId()));
                }
            }
            vo.setExpress(vo.getDeliveryStatus() == 20 ? expressService.getById(vo.getExpressId()) : null);
            vo.setPayStatusText(vo.getPayStatus() == 10 ? "未付款" : "已付款");
            vo.setDeliveryStatusText(vo.getDeliveryStatus() == 10 ? "未发货" : "已发货");
            return vo;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * 导出订单
     * @param orderPageParam
     * @param httpServletResponse
     * @return
     */
    public void exportList(OrderPageParam orderPageParam, HttpServletResponse httpServletResponse) throws Exception {
        List<OrderVo> orderList = this.getListAll(orderPageParam);
        exportService.orderList(orderList, httpServletResponse);
    }
}
