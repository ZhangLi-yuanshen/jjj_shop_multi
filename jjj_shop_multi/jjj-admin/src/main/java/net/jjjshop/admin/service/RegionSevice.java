package net.jjjshop.admin.service;

import net.jjjshop.admin.param.RegionPageParam;
import net.jjjshop.admin.param.RegionParam;
import net.jjjshop.admin.vo.RegionVo;
import net.jjjshop.common.entity.settings.Region;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.framework.core.pagination.Paging;


public interface RegionSevice extends BaseService<Region> {


    /*地区列表*/
    Paging<Region> getList(RegionPageParam regionPageParam);
}
