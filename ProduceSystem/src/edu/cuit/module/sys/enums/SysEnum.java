package edu.cuit.module.sys.enums;

public enum SysEnum {
	SICHUANCODE(1012), //四川code
	DICTOPCODE(0),
	ROLESTATEABLE(1); //用户角色启用
	
	
	private int value = 0;
    private SysEnum(int value) {
        this.value = value;
    }
    //得到枚举对应的整型数值
    public int value() {
        return this.value;
    }
}
