package edu.cuit.module.sys.enums;

public enum JurCodeEnum {
	/*模块管理*/
	LISTMODULE("ModuleList"),
	ADDMODULE("AddModule"),
	DELETEMODULE("DeleteModule"),
	EDITMODULE("EditModule"),
	
	/*用户*/
	ADDUSER("AddUser"),
	LISTUSER("UserList"),
	DELETEUSER("DeleteUser"),
	EDITUSER("EditUser"),
	
	/*字典管理*/
	LISTDIC("DictionaryList"),
	ADDDIC("AddDictionary"),
	DELETEDIC("DeleteDictionary"),
	EDITDIC("EditDictionary"),
	
	/*角色*/
	LISTROLE("RoleList"),
	ADDROLE("AddRole"),
	DELETEROLE("DeleteRole"),
	EDITROLE("EditRole"),
	
	/*地区管理*/
	LISTAREA("listArea"),
	ADDAREA("addArea"),
	DELETEAREA("deleteArea"),
	EDITAREA("editArea"),
	
	/*权限管理*/
	EDITJURISDICTION("EditJurisdiction"),
	ADDJURISDICTION("AddJurisdiction"),
	DELTEJURISDICTION("DeleteJurisdiction"),
	
	/*专家管理*/
	EDITEXPERT("EditExpert"),
	ADDEXPERT("AddExpert"),
	DELTEEXPERT("DeleteExpert"),
	
	/*专家组管理*/
	EDITGROUP("EDITGROUP"),
	ADDGROUP("ADDGROUP"),
	DELTEGROUP("DELGROUP"),
	
	/*气象站管理*/
	EDITControl("EditControl"),
	DELTEControl("DelControl"),
	
	/*商家管理*/
	EDITBUSINESS("EditBusiness"),
	ADDBUSINESS("AddBusiness"),
	DELTEBUSINESS("DeleteBusiness");
	
	private String value;
	private JurCodeEnum(String value) {
        this.value = value;
    }
	public String value() {
        return this.value;
    }
}
