package net.jjjshop.front.service.settings;

import net.jjjshop.common.entity.settings.Message;
import net.jjjshop.framework.common.service.BaseService;

import java.util.List;

public interface MessageService extends BaseService<Message> {
    /**
     * 获取消息
     */
    List<String> getMessageByNameArr(String platform, String[] messageENameArr);
}