package net.jjjshop.front.service.settings.impl;

import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.settings.MessageSettings;
import net.jjjshop.common.mapper.settings.MessageSettingsMapper;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import net.jjjshop.front.service.settings.MessageSettingsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageSettingsServiceImpl extends BaseServiceImpl<MessageSettingsMapper, MessageSettings> implements MessageSettingsService {
}