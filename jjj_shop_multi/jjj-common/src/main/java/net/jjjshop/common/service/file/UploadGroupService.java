package net.jjjshop.common.service.file;

import net.jjjshop.common.entity.file.UploadGroup;
import net.jjjshop.framework.common.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文件库分组记录表 服务类
 *
 * @author jjjshop
 * @since 2022-06-28
 */
public interface UploadGroupService extends BaseService<UploadGroup> {

    List<UploadGroup> getAll(String type);

    boolean addGroup(String groupName, String groupType);

    boolean deleteById(Integer groupId);

    boolean editGroup(String groupId, String groupName);

    boolean editGroup(Integer groupId, String groupName);

    @Transactional(rollbackFor = Exception.class)
    boolean deleteGroup(Integer groupId);
}
