package net.jjjshop.shop.service.order.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.order.*;
import net.jjjshop.common.entity.supplier.Supplier;
import net.jjjshop.common.mapper.order.OrderRefundMapper;
import net.jjjshop.common.service.order.OrderProductService;
import net.jjjshop.common.service.user.UserService;
import net.jjjshop.common.util.OrderRefundUtils;
import net.jjjshop.common.util.UploadFileUtils;
import net.jjjshop.common.util.message.MessageUtils;
import net.jjjshop.common.vo.setting.ImageVo;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import net.jjjshop.framework.core.pagination.PageInfo;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.shop.param.order.OrderRefundPageParam;
import net.jjjshop.shop.service.order.OrderRefundAddressService;
import net.jjjshop.shop.service.order.OrderRefundImageService;
import net.jjjshop.shop.service.order.OrderRefundService;
import net.jjjshop.shop.service.order.OrderService;
import net.jjjshop.shop.service.settings.ExpressService;
import net.jjjshop.shop.service.settings.ReturnAddressService;
import net.jjjshop.shop.service.supplier.SupplierService;
import net.jjjshop.shop.vo.order.OrderProductVo;
import net.jjjshop.shop.vo.order.OrderRefundVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 售后单记录表 服务实现类
 * @author jjjshop
 * @since 2022-07-04
 */
@Slf4j
@Service
public class OrderRefundServiceImpl extends BaseServiceImpl<OrderRefundMapper, OrderRefund> implements OrderRefundService {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UploadFileUtils uploadFileUtils;
    @Autowired
    private OrderRefundUtils orderRefundUtils;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private ReturnAddressService returnAddressService;
    @Autowired
    private OrderRefundAddressService orderRefundAddressService;
    @Autowired
    private OrderRefundImageService orderRefundImageService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private SupplierService supplierService;

    /**
     * 获取售后单数据
     * @param orderRefundPageParam
     * @return
     */
    public Integer getCountByStatus(OrderRefundPageParam orderRefundPageParam) {
        LambdaQueryWrapper<OrderRefund> wrapper = new LambdaQueryWrapper<>();
        //根据订单号模糊搜索
        if (StringUtils.isNotEmpty(orderRefundPageParam.getOrderNo())) {
            List<Integer> list = orderService.list(new LambdaQueryWrapper<Order>().like(Order::getOrderNo, orderRefundPageParam.getOrderNo()))
                    .stream().map(e -> {
                        return e.getOrderId();
                    }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(list)) {
                wrapper.in(OrderRefund::getOrderId, list);
            }
        }
        //查询参数：开始时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getStartDate())) {
            Date startTime = DateUtil.parse(orderRefundPageParam.getStartDate() + " 00:00:00");
            wrapper.ge(OrderRefund::getCreateTime, startTime);
        }
        //查询参数：结束时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getEndDate())) {
            Date endTime = DateUtil.parse(orderRefundPageParam.getEndDate() + " 23:59:59");
            wrapper.le(OrderRefund::getCreateTime, endTime);
        }
        //查询参数：售后单种类
        if (orderRefundPageParam.getType() != null && orderRefundPageParam.getType() > 0) {
            wrapper.eq(OrderRefund::getType, orderRefundPageParam.getType());
        }
        wrapper.orderByDesc(OrderRefund::getCreateTime);
        wrapper.eq(OrderRefund::getStatus, orderRefundPageParam.getStatus());
        return this.count(wrapper);
    }

    /**
     * 获取售后订单分页列表
     * @param orderRefundPageParam
     * @return
     */
    public Paging<OrderRefundVo> getList(OrderRefundPageParam orderRefundPageParam) {
        LambdaQueryWrapper<OrderRefund> wrapper = new LambdaQueryWrapper<>();
        //根据订单号模糊搜索
        if (StringUtils.isNotEmpty(orderRefundPageParam.getOrderNo())) {
            List<Integer> list = orderService.list(new LambdaQueryWrapper<Order>().like(Order::getOrderNo, orderRefundPageParam.getOrderNo()))
                    .stream().map(e -> {
                        return e.getOrderId();
                    }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(list)) {
                wrapper.in(OrderRefund::getOrderId, list);
            }
        }
        //查询参数：开始时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getStartDate())) {
            Date startTime = DateUtil.parse(orderRefundPageParam.getStartDate() + " 00:00:00");
            wrapper.ge(OrderRefund::getCreateTime, startTime);
        }
        //查询参数：结束时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getEndDate())) {
            Date endTime = DateUtil.parse(orderRefundPageParam.getEndDate() + " 23:59:59");
            wrapper.le(OrderRefund::getCreateTime, endTime);
        }
        //查询参数：售后单种类
        if (orderRefundPageParam.getType() != null && orderRefundPageParam.getType() > 0) {
            wrapper.eq(OrderRefund::getType, orderRefundPageParam.getType());
        }
        //查询参数：售后单状态
        if (orderRefundPageParam.getStatus() != null && orderRefundPageParam.getStatus() >= 0) {
            wrapper.eq(OrderRefund::getStatus, orderRefundPageParam.getStatus());
        }
        wrapper.orderByDesc(OrderRefund::getCreateTime);
        Page page = new PageInfo(orderRefundPageParam);
        IPage<OrderRefund> iPage = this.page(page, wrapper);
        IPage<OrderRefundVo> result = iPage.convert(item -> {
            OrderRefundVo vo = new OrderRefundVo();
            BeanUtils.copyProperties(item, vo);
            OrderProduct orderProduct = orderProductService.getById(vo.getOrderProductId());
            //设置售后单商品信息
            OrderProductVo orderProductVo = new OrderProductVo();
            BeanUtils.copyProperties(orderProduct, orderProductVo);
            orderProductVo.setImagePath(uploadFileUtils.getFilePath(orderProductVo.getImageId()));
            vo.setOrderProduct(orderProductVo);
            //设置售后单用户信息
            vo.setUser(userService.getById(item.getUserId()));
            //设置售后单订单信息
            Order order = orderService.getById(item.getOrderId());
            vo.setOrderNo(order.getOrderNo());
            vo.setOrderCreateTime(order.getCreateTime());
            vo.setIsAgreeText(this.getIsAgreeAttr(vo.getIsAgree()));
            vo.setStatusText(this.getStateAttr(vo.getStatus()));
            vo.setTypeText(this.getTypeAttr(vo.getType()));
            vo.setOrderCreateTime(order.getCreateTime());
            return vo;
        });
        return new Paging(result);
    }

    /**
     * 获取售后订单详情
     * @param orderRefundId
     * @return
     */
    public OrderRefundVo detail(Integer orderRefundId) {
        OrderRefund orderRefund = this.getById(orderRefundId);
        OrderRefundVo vo = new OrderRefundVo();
        BeanUtils.copyProperties(orderRefund, vo);
        OrderProduct orderProduct = orderProductService.getById(vo.getOrderProductId());
        OrderProductVo orderProductVo = new OrderProductVo();
        BeanUtils.copyProperties(orderProduct, orderProductVo);
        orderProductVo.setImagePath(uploadFileUtils.getFilePath(orderProductVo.getProductId()));
        vo.setOrderProduct(orderProductVo);
        vo.setUser(userService.getById(orderRefund.getUserId()));
        Order order = orderService.getById(orderRefund.getOrderId());
        vo.setOrder(order);
        vo.setIsAgreeText(this.getIsAgreeAttr(vo.getIsAgree()));
        vo.setStatusText(this.getStateAttr(vo.getStatus()));
        vo.setTypeText(this.getTypeAttr(vo.getType()));
        if (vo.getIsAgree() == 0) {
            vo.setAddressList(returnAddressService.getAll());
        } else if (vo.getIsAgree() == 10) {
            vo.setAddress(orderRefundAddressService.getOne(new LambdaQueryWrapper<OrderRefundAddress>().eq(OrderRefundAddress::getOrderRefundId, vo.getOrderRefundId())));
        }
        if (vo.getIsUserSend() == 1) {
            vo.setExpress(expressService.getById(vo.getExpressId()));
        }
        if (vo.getType() == 20 && vo.getStatus() == 0) {
            vo.setExpressList(expressService.getAll());
        }
        if (vo.getIsPlateSend() == 1) {
            vo.setSendExpressName(expressService.getById(vo.getSendExpressId()).getExpressName());
        }
        List<OrderRefundImage> refundImages = orderRefundImageService.list(new LambdaQueryWrapper<OrderRefundImage>()
                .eq(OrderRefundImage::getOrderRefundId, vo.getOrderRefundId()));
        List<ImageVo> images = refundImages.stream().map(e -> {
            ImageVo image = new ImageVo();
            image.setFileId(e.getImageId());
            image.setFilePath(uploadFileUtils.getFilePath(e.getImageId()));
            return image;
        }).collect(Collectors.toList());
        Supplier supplier = supplierService.getById(vo.getShopSupplierId());
        vo.setSupplierName(supplier.getName());
        vo.setImages(images);
        return vo;


    }

    /**
     * 获取售后订单分页列表
     * @param orderRefundPageParam
     * @return
     */
    public Paging<OrderRefundVo> getPlateList(OrderRefundPageParam orderRefundPageParam) {
        LambdaQueryWrapper<OrderRefund> wrapper = new LambdaQueryWrapper<>();
        //根据订单号模糊搜索
        if (StringUtils.isNotEmpty(orderRefundPageParam.getOrderNo())) {
            List<Integer> list = orderService.list(new LambdaQueryWrapper<Order>().like(Order::getOrderNo, orderRefundPageParam.getOrderNo()))
                    .stream().map(e -> {
                        return e.getOrderId();
                    }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(list)) {
                wrapper.in(OrderRefund::getOrderId, list);
            }
        }
        //查询参数：开始时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getStartDate())) {
            Date startTime = DateUtil.parse(orderRefundPageParam.getStartDate() + " 00:00:00");
            wrapper.ge(OrderRefund::getCreateTime, startTime);
        }
        //查询参数：结束时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getEndDate())) {
            Date endTime = DateUtil.parse(orderRefundPageParam.getEndDate() + " 23:59:59");
            wrapper.le(OrderRefund::getCreateTime, endTime);
        }
        //查询参数：售后单种类
//        if (orderRefundPageParam.getType() != null && orderRefundPageParam.getType() > 0) {
//            wrapper.eq(OrderRefund::getType, orderRefundPageParam.getType());
//        }
        //售后类型(10退货退款 20换货 30退款)
        wrapper.eq(OrderRefund::getType, 30);
        //查询参数：售后单状态
        if (orderRefundPageParam.getStatus() != null && orderRefundPageParam.getStatus() >= 0) {
            wrapper.eq(OrderRefund::getStatus, orderRefundPageParam.getStatus());
        }

        //售后单状态(选项卡)
        if(orderRefundPageParam.getPlateStatus() != null && orderRefundPageParam.getPlateStatus() == -1) {
            wrapper.gt(OrderRefund::getPlateStatus, 0);
        }else if(orderRefundPageParam.getPlateStatus() != null && orderRefundPageParam.getPlateStatus() == 40){
            wrapper.gt(OrderRefund::getPlateStatus, 10);
            wrapper.eq(OrderRefund::getStatus, 20);
        }else if(orderRefundPageParam.getPlateStatus() != null ) {
            wrapper.eq(OrderRefund::getPlateStatus, orderRefundPageParam.getPlateStatus());
        }

        wrapper.orderByDesc(OrderRefund::getCreateTime);
        Page page = new PageInfo(orderRefundPageParam);
        IPage<OrderRefund> iPage = this.page(page, wrapper);
        IPage<OrderRefundVo> result = iPage.convert(item -> {
            OrderRefundVo vo = new OrderRefundVo();
            BeanUtils.copyProperties(item, vo);
            OrderProduct orderProduct = orderProductService.getById(vo.getOrderProductId());
            //设置售后单商品信息
            OrderProductVo orderProductVo = new OrderProductVo();
            BeanUtils.copyProperties(orderProduct, orderProductVo);
            orderProductVo.setImagePath(uploadFileUtils.getFilePath(orderProductVo.getImageId()));
            vo.setOrderProduct(orderProductVo);
            //设置售后单用户信息
            vo.setUser(userService.getById(item.getUserId()));
            //设置售后单订单信息
            Order order = orderService.getById(item.getOrderId());
            vo.setOrderNo(order.getOrderNo());
            vo.setOrderCreateTime(order.getCreateTime());
            vo.setIsAgreeText(this.getIsAgreeAttr(vo.getIsAgree()));
            vo.setStatusText(this.getStateAttr(vo.getStatus()));
            vo.setPlateStatusText(this.getPlateStateAttr(vo.getPlateStatus()));
            vo.setTypeText(this.getTypeAttr(vo.getType()));
            vo.setOrderCreateTime(order.getCreateTime());
            vo.setSupplierName(supplierService.getById(vo.getShopSupplierId()).getName());
            return vo;
        });
        return new Paging(result);
    }

    /**
     * 获取售后单数据
     * @param orderRefundPageParam
     * @return
     */
    public Integer getCountByPlateStatus(OrderRefundPageParam orderRefundPageParam) {
        LambdaQueryWrapper<OrderRefund> wrapper = new LambdaQueryWrapper<>();
        //根据订单号模糊搜索
        if (StringUtils.isNotEmpty(orderRefundPageParam.getOrderNo())) {
            List<Integer> list = orderService.list(new LambdaQueryWrapper<Order>().like(Order::getOrderNo, orderRefundPageParam.getOrderNo()))
                    .stream().map(e -> {
                        return e.getOrderId();
                    }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(list)) {
                wrapper.in(OrderRefund::getOrderId, list);
            }
        }
        //查询参数：开始时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getStartDate())) {
            Date startTime = DateUtil.parse(orderRefundPageParam.getStartDate() + " 00:00:00");
            wrapper.ge(OrderRefund::getCreateTime, startTime);
        }
        //查询参数：结束时间
        if (StringUtils.isNotEmpty(orderRefundPageParam.getEndDate())) {
            Date endTime = DateUtil.parse(orderRefundPageParam.getEndDate() + " 23:59:59");
            wrapper.le(OrderRefund::getCreateTime, endTime);
        }
        //查询参数：售后单种类
        if (orderRefundPageParam.getType() != null && orderRefundPageParam.getType() > 0) {
            wrapper.eq(OrderRefund::getType, orderRefundPageParam.getType());
        }
        if(orderRefundPageParam.getStatus() != null){
            wrapper.eq(OrderRefund::getStatus, orderRefundPageParam.getStatus());
            wrapper.gt(OrderRefund::getPlateStatus, 10);
        }else {
            wrapper.eq(OrderRefund::getPlateStatus, orderRefundPageParam.getPlateStatus());
        }
        wrapper.orderByDesc(OrderRefund::getCreateTime);
        return this.count(wrapper);
    }


    private String getIsAgreeAttr(Integer value) {
        switch (value) {
            case 0:
                return "进行中";
            case 10:
                return "已同意";
            case 20:
                return "已拒绝";
        }
        return "";
    }

    private String getStateAttr(Integer value) {
        //售后单状态(0进行中 10已拒绝 20已完成 30已取消)
        switch (value) {
            case 0:
                return "进行中";
            case 10:
                return "已拒绝";
            case 20:
                return "已完成";
            case 30:
                return "已取消";
        }
        return "";
    }

    private String getTypeAttr(Integer value) {
        //售后类型(10退货退款 20换货 30退款)
        switch (value) {
            case 10:
                return "退货退款";
            case 20:
                return "换货";
            case 30:
                return "退款";
        }
        return "";
    }
    private String getPlateStateAttr(Integer value) {
        //售后单状态(0进行中 10已拒绝 20已完成 30已取消)
        switch (value) {
            case 10:
                return "申请平台介入";
            case 20:
                return "已同意";
            case 30:
                return "已拒绝";
        }
        return "";
    }

}
