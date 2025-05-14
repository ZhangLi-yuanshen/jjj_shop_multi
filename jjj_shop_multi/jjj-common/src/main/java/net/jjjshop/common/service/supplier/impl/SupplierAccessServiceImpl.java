package net.jjjshop.common.service.supplier.impl;

import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.supplier.SupplierAccess;
import net.jjjshop.common.mapper.supplier.SupplierAccessMapper;
import net.jjjshop.common.service.supplier.SupplierAccessService;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SupplierAccessServiceImpl extends BaseServiceImpl<SupplierAccessMapper, SupplierAccess> implements SupplierAccessService {

    @Autowired
    private SupplierAccessMapper supplierAccessMapper;

}