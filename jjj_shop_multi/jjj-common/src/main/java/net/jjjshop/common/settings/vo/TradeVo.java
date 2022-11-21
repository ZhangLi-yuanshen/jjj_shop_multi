package net.jjjshop.common.settings.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel("交易设置VO")
public class TradeVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer freightRule;
    private Integer closeDays;
    private Integer receiveDays;
    private Integer refundDays;

    public TradeVo() {
        this.freightRule = 10;
        this.closeDays = 3;
        this.receiveDays = 7;
        this.refundDays = 7;
    }

}
