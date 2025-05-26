package net.jjjshop.common.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.page.Page;
import net.jjjshop.common.entity.user.User;
import net.jjjshop.common.entity.user.UserGrade;
import net.jjjshop.common.mapper.page.PageMapper;
import net.jjjshop.common.mapper.user.UserMapper;
import net.jjjshop.common.service.page.PageService;
import net.jjjshop.common.service.user.UserGradeService;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserGradeServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserGradeService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(UserGrade grade) {

    }
}