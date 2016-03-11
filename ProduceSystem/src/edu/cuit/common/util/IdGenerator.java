package edu.cuit.common.util;

import java.util.UUID;

public class IdGenerator {
	public static String genRamId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
