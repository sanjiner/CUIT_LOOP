package edu.cuit.common.enums;

public enum DictionaryEnum {
	COMPANYTYPE(100012),//单位性质
	ENTERPRISETYPE(10009),//企业类型
	SHOPTYPE(100013), //商家类型
	EXPERTCATEGORY(100011), //专家类别
	SHOPROLEID("EC795401FA8D4CC29DBD4B6BE09FDB3D"),//商家角色的主键
	PRIMARYEXPERTID("7957F244413A435BB7CD863D5FEEC0C7"),//基层专家
	PROVINCALEXPERTID("349EA3AF83BE4E38AD54D4D29942D83C"),//省级专家
	AUTHING(100081), //认证中
	AUTHED(100084); //认证通过
	
	
	private int value = 0;
	private String stringValue;
    private DictionaryEnum(int value) {
        this.value = value;
    }
    //得到枚举对应的整型数值
    public int value() {
        return this.value;
    }
    private DictionaryEnum(String value) {
        this.stringValue = value;
    }
    public String toString() {
        return this.stringValue;
    }
    
   //得到枚举的原始值
    public static DictionaryEnum valueOf(int value) {
        switch (value) {
        case 100012:
        	return COMPANYTYPE;
        case 10009:
        	return ENTERPRISETYPE;
        case 100013:
        	return SHOPTYPE;
        case 100011:
        	return EXPERTCATEGORY;
        default:
            return null;
        }
    }
}
