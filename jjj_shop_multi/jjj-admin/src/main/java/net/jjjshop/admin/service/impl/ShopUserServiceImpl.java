package net.jjjshop.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.jjjshop.admin.service.ShopUserService;
import net.jjjshop.common.entity.shop.ShopUser;
import net.jjjshop.common.mapper.shop.ShopUserMapper;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShopUserServiceImpl extends BaseServiceImpl<ShopUserMapper, ShopUser> implements ShopUserService {
}
