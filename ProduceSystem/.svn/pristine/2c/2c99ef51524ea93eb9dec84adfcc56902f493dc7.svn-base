package edu.cuit.module.infom.tools;

import java.util.UUID;
/**
 * 
 * @author wc
 *用于生产uuid的唯一码，用于数据库的主键
 */
public class UuidMake {
	UUID uuid;
	public UuidMake(){
		
	 uuid=UUID.randomUUID();
		
	}
	public String make(){
		
		String s=uuid.toString();
		String w=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
		return w;
	}
}
