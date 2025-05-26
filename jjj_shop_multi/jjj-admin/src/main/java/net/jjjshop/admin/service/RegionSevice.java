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

    /*新增*/
    boolean add(RegionParam regionParam);

    /*删除*/
    boolean delete(Integer id);


    /*修改*/
    boolean update(RegionParam regionParam);


    /*编辑*/
    RegionVo getEditData(Integer id);
}
