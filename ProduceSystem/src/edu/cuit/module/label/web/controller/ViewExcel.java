package edu.cuit.module.label.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import edu.cuit.module.label.entity.Labermanagement;

public class ViewExcel extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		
		// 产生Excel表头  
        HSSFSheet sheet = workbook.createSheet("labelList");  
        HSSFRow header = sheet.createRow(0); // 第0行  
        // 产生标题列  
        header.createCell(0).setCellValue("二维码地址");  
        header.createCell(1).setCellValue("标签查询码"); 
		
        // 填充数据
        String address = basePath + "labelscan/ScanResult?id=";
		List<?> list = (List<?>) model.get("list");
		for(int i=0;i<list.size();i++)
		{
			Labermanagement lm = (Labermanagement) list.get(i);
			String url =  address + lm.getLabelId();
			String code = lm.getLabelQueryId();
			HSSFRow row = sheet.createRow(i+1); 
			row.createCell(0).setCellValue(url);  
			row.createCell(1).setCellValue(code);  
		}
		
        String filename = (String) model.get("filename");//设置下载时客户端Excel的名称     
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");     
        OutputStream outputStream = response.getOutputStream();     
        try  
        {  
            workbook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }  
	}
	
}
