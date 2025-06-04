package net.jjjshop.common.service.page.impl;

import lombok.extern.slf4j.Slf4j;

import net.jjjshop.common.entity.page.Page;
import net.jjjshop.common.mapper.page.PageMapper;
import net.jjjshop.common.service.page.PageService;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageServiceImpl  extends BaseServiceImpl<PageMapper, Page> implements PageService {
    @Autowired
    private PageMapper pageMapper;
}
