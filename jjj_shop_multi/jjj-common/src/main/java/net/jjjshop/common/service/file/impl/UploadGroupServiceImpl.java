package net.jjjshop.common.service.file.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.file.UploadFile;
import net.jjjshop.common.entity.file.UploadGroup;
import net.jjjshop.common.mapper.file.UploadGroupMapper;
import net.jjjshop.common.service.file.UploadFileService;
import net.jjjshop.common.service.file.UploadGroupService;
import net.jjjshop.framework.common.exception.BusinessException;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件库分组记录表 服务实现类
 * @author jjjshop
 * @since 2022-06-28
 */
@Slf4j
@Service
public class UploadGroupServiceImpl extends BaseServiceImpl<UploadGroupMapper, UploadGroup> implements UploadGroupService {
    @Autowired
    private UploadFileService uploadFileService;
    /**
     * 所有文件分组
     * @return
     */
    @Override
    public List<UploadGroup> getAll(String groupType){
        return this.list(new LambdaQueryWrapper<UploadGroup>().eq(UploadGroup::getGroupType, groupType)
                .eq(UploadGroup::getShopSupplierId, 0)
                .eq(UploadGroup::getIsDelete, false)
                .orderByAsc(UploadGroup::getSort)
                .orderByAsc(UploadGroup::getCreateTime));
    }
    /**
     * 新增分组
     * @param groupName
     * @param groupType
     * @return
     */
    @Override
    public boolean addGroup(String groupName, String groupType){
        UploadGroup group = new UploadGroup();
        group.setGroupName(groupName);
        group.setGroupType(groupType);
        return this.save(group);
    }

    @Override
    public boolean deleteById(Integer groupId) {
        UploadGroup group = this.getById(groupId);
        if(group == null){
            throw new BusinessException("分组不存在");
        }
        // 更新该分组下的所有文件
        uploadFileService.update(new LambdaUpdateWrapper<UploadFile>().eq(UploadFile::getGroupId, groupId)
                .set(UploadFile::getGroupId, 0));
        // 删除分组
        this.update(new LambdaUpdateWrapper<UploadGroup>().eq(UploadGroup::getGroupId, groupId)
                .set(UploadGroup::getIsDelete, 1));
        return true;
    }

    @Override
    public boolean editGroup(String groupId, String groupName) {
        return false;
    }

    /**
     * 修改分组
     * @param groupId
     * @param groupName
     * @return
     */
    @Override
    public boolean editGroup(Integer groupId, String groupName){
        UploadGroup group = this.getById(groupId);
        if(group == null){
            throw new BusinessException("分组不存在");
        }
        return this.update(new LambdaUpdateWrapper<UploadGroup>().eq(UploadGroup::getGroupId, groupId)
                .set(UploadGroup::getGroupName, groupName));
    }

    /**
     * 删除分组
     * @param groupId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGroup(Integer groupId){
        UploadGroup group = this.getById(groupId);
        if(group == null){
            throw new BusinessException("分组不存在");
        }
        // 更新该分组下的所有文件
        uploadFileService.update(new LambdaUpdateWrapper<UploadFile>().eq(UploadFile::getGroupId, groupId)
                .set(UploadFile::getGroupId, 0));
        // 删除分组
        this.update(new LambdaUpdateWrapper<UploadGroup>().eq(UploadGroup::getGroupId, groupId)
                .set(UploadGroup::getIsDelete, 1));
        return true;
    }

    public boolean moveFiles(Integer groupId, String fileIds){
        return uploadFileService.update(new LambdaUpdateWrapper<UploadFile>().in(UploadFile::getFileId, this.transFileIds(fileIds))
                .set(UploadFile::getGroupId, groupId));
    }
    private List<Integer> transFileIds(String fileIds) {
        String[] split = StringUtils.split(fileIds, ",");
        List<Integer> files = new ArrayList<>();
        for (String fileId : split) {
            files.add(Integer.parseInt(fileId));
        }
        return files;
    }

    public boolean deleteFiles(String fileIds){
        return uploadFileService.update(new LambdaUpdateWrapper<UploadFile>().in(UploadFile::getFileId, this.transFileIds(fileIds))
                .set(UploadFile::getIsDelete, 1));
    }

}
