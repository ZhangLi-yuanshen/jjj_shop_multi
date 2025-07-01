package net.jjjshop.front.Controller.file;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.file.UploadFile;
import net.jjjshop.common.util.UploadFileUtils;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.log.annotation.OperationLog;
import net.jjjshop.framework.util.UploadUtil;
import net.jjjshop.front.Controller.index.BaseController;
import net.jjjshop.front.service.file.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/front/file/upload")
@Api(value = "文件上传", tags = {"文件上传"})
public class UploadController extends BaseController {
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private UploadFileUtils uploadFileUtils;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @OperationLog(name = "上传单个图片")
    @ApiOperation(value = "上传单个图片", response = String.class)
    public ApiResult<Map<String, Object>> upload(@RequestParam("iFile") MultipartFile multipartFile) throws Exception {
        Map<String, Object> result = new HashMap<>();
        this.getUser(true);
        // 保存文件到数据库
        UploadFile file = new UploadFile();
        file.setFileSize(multipartFile.getSize());
        file.setFileType("image");
        file.setRealName(multipartFile.getOriginalFilename());
        file.setIsUser(1);
        // 上传文件
        uploadUtil.upload(multipartFile, file);
        // 文件后缀
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        file.setExtension(extension);
        uploadFileService.addFile(file);
        result.put("fileId", file.getFileId());
        result.put("filePath", uploadFileUtils.getFilePath(file.getFileId()));
        return ApiResult.ok(result, "上传成功");
    }

}
