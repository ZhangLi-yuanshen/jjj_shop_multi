package net.jjjshop.admin.service;

import net.jjjshop.admin.param.AppPageParam;
import net.jjjshop.admin.vo.AppVo;
import net.jjjshop.common.entity.app.App;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.framework.core.pagination.Paging;

public interface AppService extends BaseService<App> {
    Paging<AppVo> getList(AppPageParam appPageParam);
}
