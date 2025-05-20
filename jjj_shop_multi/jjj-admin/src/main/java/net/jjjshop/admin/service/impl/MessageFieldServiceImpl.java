package net.jjjshop.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.jjjshop.admin.param.FieldParam;
import net.jjjshop.admin.service.MessageFieldService;
import net.jjjshop.common.entity.settings.MessageField;
import net.jjjshop.common.mapper.settings.MessageFieldMapper;
import net.jjjshop.framework.common.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageFieldServiceImpl extends BaseServiceImpl<MessageFieldMapper,MessageField> implements MessageFieldService {
    @Autowired
    private MessageFieldMapper messageFieldMapper;
    @Override
    public List<MessageField> getAll(Integer messageID) {
        LambdaQueryWrapper <MessageField> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageField::getMessageId,messageID);
        wrapper.eq(MessageField::getIsDelete,false);
        wrapper.orderByAsc(MessageField::getSort);
        return this.list(wrapper);
    }

    @Override
    public Boolean saveFieldId(FieldParam fieldParam) {
        List<MessageField> list= new ArrayList<MessageField>();
        for(MessageField bean:fieldParam.getFieldData()){
            MessageField addField = new MessageField();
            BeanUtils.copyProperties(bean, addField);
            if(bean.getMessageId() == null){
                addField.setMessageId(fieldParam.getMessageId());
            }
            list.add(addField);
        }
        //删除字段
        if(fieldParam.getDeleteIds() != null
                && fieldParam.getDeleteIds().length > 0){
            LambdaQueryWrapper<MessageField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(MessageField::getMessageFieldId, fieldParam.getDeleteIds());

            MessageField deleteField = new MessageField();
            deleteField.setIsDelete(true);
            update(deleteField, lambdaQueryWrapper);
        }
        // 增加字段
        return this.saveOrUpdateBatch(list);
    }
}
