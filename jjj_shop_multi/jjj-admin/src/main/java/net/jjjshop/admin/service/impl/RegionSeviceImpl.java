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

    @Override
    public boolean add(RegionParam regionParam) {
        Region region = new Region();
        BeanUtils.copyProperties(regionParam, region);
        region.setPid(getPid(regionParam));
        return this.save(region);
    }



    private Integer getPid(RegionParam regionParam) {
        if (regionParam.getLevel() == 1) {
            return 0;
        } else if (regionParam.getLevel() == 2) {
            return regionParam.getProvinceId();
        }else if (regionParam.getLevel() == 3){
            return regionParam.getCityId();
        }
        return 0;
    }

    /*删除*/
    @Override
    public boolean delete(Integer id){
        Region updateBean = new Region();
        updateBean.setId(id);
        updateBean.setIsDelete(true);
        return this.updateById(updateBean);
    }


    /*修改*/
    @Override
    public boolean update(RegionParam regionParam){
        Region region = new Region();
        BeanUtils.copyProperties(regionParam, region);
        region.setPid(getPid(regionParam));
        return this.updateById(region);
    }


    /*编辑*/
    @Override
    public RegionVo getEditData(Integer id) {
        Region region = this.getById(id);
        RegionVo vo = new RegionVo();
        BeanUtils.copyProperties(region, vo);
        if(region.getLevel() == 1){
            vo.setProvinceId("");
            vo.setCityId("");
        }
        if(region.getLevel() == 2){
            vo.setProvinceId(""+region.getPid());
            vo.setCityId("");
        }
        if(region.getLevel() == 3){
            vo.setProvinceId(""+this.getById(region.getPid()).getPid());
            vo.setCityId(""+region.getPid());
        }
        return vo;
    }
}
