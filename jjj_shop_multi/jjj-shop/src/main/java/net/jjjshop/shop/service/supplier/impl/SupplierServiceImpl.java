package net.jjjshop.shop.service.supplier.impl;


import lombok.extern.slf4j.Slf4j;

import net.jjjshop.common.entity.supplier.Supplier;

import net.jjjshop.common.mapper.supplier.SupplierMapper;

import net.jjjshop.framework.common.service.impl.BaseServiceImpl;

import net.jjjshop.shop.service.supplier.SupplierService;

import org.springframework.stereotype.Service;


/**
 * 供应商表 服务实现类
 *
 * @author jjjshop
 * @since 2022-10-17
 */
@Slf4j
@Service
public class SupplierServiceImpl extends BaseServiceImpl<SupplierMapper, Supplier> implements SupplierService {

}
