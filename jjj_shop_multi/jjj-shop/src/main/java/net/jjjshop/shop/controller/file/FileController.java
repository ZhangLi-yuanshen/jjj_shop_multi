package net.jjjshop.shop.controller.file;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.jjjshop.common.entity.file.UploadGroup;
import net.jjjshop.common.service.file.UploadFileService;
import net.jjjshop.common.service.file.UploadGroupService;
import net.jjjshop.framework.common.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop/file/file")
public class FileController {
    @Autowired
    private UploadGroupService uploadGroupService;
    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ApiResult<List<UploadGroup> >category (@RequestParam String type){
                return ApiResult.ok(uploadGroupService.getAll(type));
    };

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public ApiResult<String> addGroup(@RequestParam String groupName,@RequestParam String groupType){
        if(uploadGroupService.addGroup(groupName,groupType)){
                return ApiResult.ok(null,"新增成功");
        }else {

            return ApiResult.fail("新增失败");
        }
    }

    @RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)

    public ApiResult<String> deleteGroup(@RequestParam Integer groupId){
        if(uploadGroupService.deleteById(groupId)){
            return ApiResult.ok(null,"删除成功");
        }else {
            return ApiResult.fail("删除失败");
        }
    }
    @RequestMapping(value = "/editGroup", method = RequestMethod.POST)
    public ApiResult<String> editGroup(@RequestParam String groupId,@RequestParam String groupName){
        if(uploadGroupService.editGroup(groupId,groupName)){
            return ApiResult.ok(null,"修改成功");
        }else {
            return ApiResult.fail("修改失败");
        }
    }


}
