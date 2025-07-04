package net.jjjshop.shop.controller.file;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.file.UploadFile;
import net.jjjshop.common.util.UploadFileUtils;
import net.jjjshop.framework.common.api.ApiResult;
import net.jjjshop.framework.log.annotation.OperationLog;
import net.jjjshop.framework.util.ShopLoginUtil;
import net.jjjshop.framework.util.UploadUtil;
import net.jjjshop.shop.service.file.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/shop/file/upload")
@Api(value = "文件上传", tags = {"文件上传"})
public class UploadController {
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private UploadFileUtils uploadFileUtils;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @OperationLog(name = "上传单个图片")
    @ApiOperation(value = "上传单个图片", response = String.class)
    public ApiResult<String> upload(@RequestParam("iFile") MultipartFile multipartFile,
                                    @RequestParam Integer groupId, @RequestParam String fileType) throws Exception {
        // 保存文件到数据库
        UploadFile file = new UploadFile();
        file.setGroupId(groupId);
        file.setFileSize(multipartFile.getSize());
        file.setFileType(fileType);
        file.setRealName(multipartFile.getOriginalFilename());
        // 上传文件
        uploadUtil.upload(multipartFile, file);
        // 文件后缀
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        file.setExtension(extension);
        file.setAppId(ShopLoginUtil.getAppId());
        if(uploadFileService.addFile(file)) {
            String filePath =  uploadFileUtils.getFilePathByFile(file);
            return ApiResult.ok(filePath, "上传成功");
        }else{
            return ApiResult.fail("上传失败");
        }
    }

}
