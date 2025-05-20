package net.jjjshop.admin.service;

import net.jjjshop.admin.param.FieldParam;
import net.jjjshop.common.entity.settings.MessageField;
import net.jjjshop.framework.common.service.BaseService;

import java.util.List;

public interface MessageFieldService extends BaseService<MessageField> {
    /**
     * 获取消息字段
     * @return
     */
    List<MessageField> getAll(Integer messageID);
    /**
     * 保存消息字段
     * @param fieldParam
     * @return
     */
    Boolean saveFieldId(FieldParam fieldParam);
}
