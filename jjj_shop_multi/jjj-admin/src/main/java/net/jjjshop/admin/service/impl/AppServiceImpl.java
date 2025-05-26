package net.jjjshop.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.admin.param.AppPageParam;
import net.jjjshop.admin.param.AppParam;
import net.jjjshop.admin.service.AppService;
import net.jjjshop.admin.service.ShopUserService;
import net.jjjshop.admin.vo.AppVo;
import net.jjjshop.common.entity.app.App;
import net.jjjshop.common.entity.shop.ShopUser;
import net.jjjshop.common.entity.user.UserGrade;
import net.jjjshop.common.mapper.app.AppMapper;
import net.jjjshop.common.util.PageUtils;
import net.jjjshop.framework.common.exception.BusinessException;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import net.jjjshop.framework.core.pagination.PageInfo;
import net.jjjshop.framework.core.pagination.Paging;
import net.jjjshop.framework.shiro.util.SaltUtil;
import net.jjjshop.framework.util.PasswordUtil;
import net.jjjshop.shop.service.user.UserGradeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class AppServiceImpl extends BaseServiceImpl<AppMapper, App> implements AppService {
    @Autowired
    private ShopUserService shopUserService;




    @Override
    public Paging<AppVo> getList(AppPageParam appPageParam) {
        Page<App> page = new PageInfo<>(appPageParam, OrderItem.asc(getLambdaColumn(App::getCreateTime)));
        IPage<App> iPage = this.page(page, new LambdaUpdateWrapper<App>().eq(App::getIsDelete, false));
        // 最终返回分页对象
        IPage<AppVo> resultPage = iPage.convert(result -> {
            AppVo vo = new AppVo();
            BeanUtil.copyProperties(result, vo);
            vo.setUserName(shopUserService.getOne(new LambdaQueryWrapper<ShopUser>()
                    .eq(ShopUser::getAppId, vo.getAppId()).eq(ShopUser::getIsSuper, true)).getUserName());
            return vo;
        });
        return new Paging(resultPage);
    }

    // 更新启用功能
    public boolean editStatusById(Integer appId){
        App app = this.getById(appId);
        App updateBean = new App();
        updateBean.setAppId(appId);
        updateBean.setIsRecycle(app.getIsRecycle()?false:true);
        return this.updateById(updateBean);
    }

}
