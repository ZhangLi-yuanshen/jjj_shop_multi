package net.jjjshop.admin.service;

import net.jjjshop.admin.param.AppPageParam;
import net.jjjshop.admin.param.AppParam;
import net.jjjshop.admin.vo.AppVo;
import net.jjjshop.common.entity.app.App;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.framework.core.pagination.Paging;

public interface AppService extends BaseService<App> {
    // index
    Paging<AppVo> getList(AppPageParam appPageParam);
    // 更新 启用功能
    boolean editStatusById(Integer appId);
    // 删除功能
    boolean setDelete(Integer appId);
    // 新增添加功能
    boolean add(AppParam appParam);
    // 修改功能
    boolean edit(AppParam appParam);
}
