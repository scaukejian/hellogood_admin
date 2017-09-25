package com.hellogood.enumeration;

/**
 * Created by kejian on 2017/9/18.
 */
public enum BaseDataType {

    JOB("job", "职位"), DEGREE("degree",  "学历"), FAMILY("family", "家庭情况"),
    ASSET("asset", "资产"),NATION("nation", "民族"), MARRY("marry", "婚姻情况"),
    SMS_CHANNEL("smsChannel", "短信渠道"), SMS_NOTICE("smsNotice", "短信通知"),
    MESSAGE("MESSAGE", "消息"), INTEREST_TABS("interestTabs", "兴趣标签"), PAY_CHANNEL("payChannel", "支付渠道");

    private String code;

    private String name;

    BaseDataType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 查询枚举
     * @param code
     * @return
     */
    public static BaseDataType get(String code) {
        for (BaseDataType baseDataType : BaseDataType.values()) {
            if (baseDataType.getCode().equals(code)) {
                return baseDataType;
            }
        }
        return null;
    }

    public static void main(String[] args){
        System.out.println(BaseDataType.get("degree"));
    }
}
