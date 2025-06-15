package net.jjjshop.front.service.settings;

import net.jjjshop.common.entity.settings.Express;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.front.vo.settings.ExpressListVo;

import java.util.List;

public interface ExpressService extends BaseService<Express> {

    /**
     * 获取所有物流公司
     * @param
     * @return
     */
    List<ExpressListVo> getAll();

}
