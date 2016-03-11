package edu.cuit.module.label.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import edu.cuit.common.web.controller.BaseController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/label")
public class SettingController extends BaseController{
	@Value("#{settings['file.path']}")
	private String fileDirectory;
	
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	public String manageList(
			@RequestParam(required = false) Integer requsetPageNo, Model model) {
		
		return "label/Setting";
	}
	
	@RequestMapping(value = "file", method = RequestMethod.POST)
	@ResponseBody
	public String file( HttpServletRequest request, HttpServletResponse response) throws IOException {
		return uploadFile(request, fileDirectory);
	}
	
	@RequestMapping(value = "set", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doSet( String selType,String txtImgUrl) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "设置成功";
		String state = "success";
		try {
            FileInputStream in=new FileInputStream(txtImgUrl);  
            FileOutputStream out= new FileOutputStream("e:/2.jpg");  
            BufferedInputStream bufferedIn=new BufferedInputStream(in);  
            BufferedOutputStream bufferedOut=new BufferedOutputStream(out);  
            byte[] by=new byte[1];
            while (bufferedIn.read(by)!=-1) {  
                bufferedOut.write(by);  
            }  
            //将缓冲区中的数据全部写出  
            bufferedOut.flush();  
            bufferedIn.close();  
            bufferedOut.close();  
            //删除源文件
            File  file = new File(txtImgUrl);  
            // 路径为文件且不为空则进行删除  
            if (file.isFile() && file.exists()) {  
                file.delete();  
            } 
		} catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}

		map.put("state", state);
		map.put("result", massage);
		return map;
	}
}
