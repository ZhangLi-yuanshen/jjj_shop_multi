package net.jjjshop.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.admin.param.RegionPageParam;
import net.jjjshop.admin.param.RegionParam;
import net.jjjshop.admin.service.RegionSevice;
import net.jjjshop.admin.vo.RegionVo;
import net.jjjshop.common.cache.RegionCache;
import net.jjjshop.common.entity.settings.Region;
import net.jjjshop.common.mapper.settings.RegionMapper;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import net.jjjshop.framework.core.pagination.PageInfo;
import net.jjjshop.framework.core.pagination.Paging;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class RegionSeviceImpl extends BaseServiceImpl<RegionMapper, Region> implements RegionSevice {

    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private RegionCache regionCache;

    @Override
    public Paging<Region> getList(RegionPageParam regionPageParam) {
        Page<Region> page = new PageInfo<>(regionPageParam);
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Region::getIsDelete, false);
        wrapper.orderByAsc(Region::getId);
        wrapper.orderByAsc(Region::getSort);
        if (StringUtils.isNotBlank(regionPageParam.getName())) {
            wrapper.and(i -> i.or().like(Region::getName, regionPageParam.getName())
                    .or().like(Region::getMergerName, regionPageParam.getName())
                    .or().like(Region::getShortname, regionPageParam.getName()));
        }
        if(regionPageParam.getLevel() != null && regionPageParam.getLevel() > 0){
            wrapper.eq(Region::getLevel, regionPageParam.getLevel());
        }
        if (regionPageParam.getProvinceId() != null && regionPageParam.getProvinceId() > 0) {
            if (regionPageParam.getCityId() != null && regionPageParam.getCityId() > 0) {
                wrapper.eq(Region::getPid, regionPageParam.getCityId());
            }else{
                wrapper.eq(Region::getPid, regionPageParam.getProvinceId());
            }
        }
        IPage<Region> iPage = regionMapper.selectPage(page, wrapper);
        return new Paging(iPage);
    }
}
