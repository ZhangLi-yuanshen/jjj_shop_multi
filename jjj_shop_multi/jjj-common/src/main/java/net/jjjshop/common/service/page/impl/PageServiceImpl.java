package net.jjjshop.common.service.page.impl;

<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
>>>>>>> origin/master
import net.jjjshop.common.entity.page.Page;
import net.jjjshop.common.mapper.page.PageMapper;
import net.jjjshop.common.service.page.PageService;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageServiceImpl  extends BaseServiceImpl<PageMapper, Page> implements PageService {
    @Autowired
    private PageMapper pageMapper;
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * diy页面表 服务实现类
 *
 * @author jjjshop
 * @since 2022-07-28
 */
@Slf4j
@Service
public class PageServiceImpl extends BaseServiceImpl<PageMapper, Page> implements PageService {

    @Autowired
    private PageMapper pageMapper;

>>>>>>> origin/master
}
