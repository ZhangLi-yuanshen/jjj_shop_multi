package net.jjjshop.common.service.file;

import net.jjjshop.common.entity.file.UploadFile;
import net.jjjshop.common.param.file.UploadFilePageParam;
import net.jjjshop.common.vo.file.UploadFileVo;
import net.jjjshop.framework.common.service.BaseService;
import net.jjjshop.framework.core.pagination.Paging;

/**
 * 文件库记录表 服务类
 *
 * @author jjjshop
 * @since 2022-06-28
 */

public interface UploadFileService extends BaseService<UploadFile> {


   public boolean addFile(UploadFile file);
    Paging<UploadFileVo> getList(UploadFilePageParam uploadFilePageParam);

}

