package net.jjjshop.common.service.user;

import net.jjjshop.common.entity.page.Page;
import net.jjjshop.common.entity.user.User;
import net.jjjshop.common.entity.user.UserGrade;
import net.jjjshop.framework.common.service.BaseService;

public interface UserGradeService extends BaseService<User> {
    void save(UserGrade grade);
}
