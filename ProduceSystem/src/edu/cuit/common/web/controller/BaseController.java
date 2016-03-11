package edu.cuit.common.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import edu.cuit.common.util.Constant;
import edu.cuit.module.sys.entity.TbcuitmoonUser;

public class BaseController {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Value("#{settings['image.showpath']}")
	public String imageShowPath;
	@Value("#{settings['file.showpath']}")
	public String fileShowPath;
	@Value("#{settings['image.path']}")
	public String imgeRootPath;
	@Value("#{settings['file.path']}")
	public String fileRootPath;

	/**
	 * 检验对象时候是null
	 * 
	 * @param objects
	 * @return 是null 返回true
	 */
	protected boolean isEmpty(Object... objects) {
		boolean flag = false;
		for (Object obj : objects) {
			if (StringUtils.isEmpty(obj)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 检查字符串是否是null， 或含有非空格字符 StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true StringUtils.hasText(" 12345 ") = true
	 * 
	 * @param str
	 * @return
	 */
	protected boolean hasText(String... str) {
		boolean flag = true;
		for (String s : str) {
			if (!StringUtils.hasText(s)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 检查字符串是否是null，或长度是否是0
	 * 
	 * StringUtils.hasLength(null) = false StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true StringUtils.hasLength("Hello") = true
	 * 
	 * @param str
	 * @return
	 */
	protected boolean hasLength(String... str) {
		boolean flag = true;
		for (String s : str) {
			if (!StringUtils.hasLength(s)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 获取登录用户名
	 * 
	 * @return
	 */
	protected String getLoginUserName() {
		Subject subject = SecurityUtils.getSubject();
		return subject.getPrincipal().toString();
	}

	/**
	 * 从httpsession中获取登录的用户信息
	 * 
	 * @param session
	 * @return
	 */
	protected TbcuitmoonUser getLoginUser(HttpSession session) {
		return (TbcuitmoonUser) session.getAttribute(Constant.LOGINUSER);
	}

	/**
	 * 修改session中保存 的user
	 * 
	 * @param session
	 * @return
	 */
	protected void modifyLoginUser(HttpSession session, TbcuitmoonUser user) {
		session.removeAttribute(Constant.LOGINUSER);
		session.setAttribute(Constant.LOGINUSER, user);
	}

	/**
	 * 将iso-8859-1 转换成 utf-8
	 * 
	 * @param param
	 * @return
	 */
	protected String toUTF8(String param) {
		String newParam = null;
		try {
			newParam = new String(param.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			newParam = param;
			e.printStackTrace();
		}

		return newParam;
	}

	/**
	 * 检查是否含有指定的权限， 如果没有 抛出 AuthorizationException 异常
	 * 
	 * @param permissions
	 */
	protected void checkPermissions(String... permissions) {
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermissions(permissions);
	}

	/**
	 * 检查权限，有返回 true, 一一对应
	 * 
	 * @param permissions
	 * @return
	 */
	protected boolean[] isPermissions(String... permissions) {
		Subject subject = SecurityUtils.getSubject();
		return subject.isPermitted(permissions);
	}

	/**
	 * 检查权限，有返回 true
	 * 
	 * @param permission
	 * @return
	 */
	protected boolean isPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject.isPermitted(permission);
	}

	/**
	 * 检查权限，有返回 true
	 * 
	 * @param permission
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	protected String uploadFile(HttpServletRequest request,
			String fileDirectory, boolean remane) throws IllegalStateException,
			IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// //记录上传过程起始时的时间，用来计算上传时间
				// int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						String fileName = UUID.randomUUID().toString().replace("-", "")+myFileName.substring(myFileName.lastIndexOf("."));
						// 定义上传路径
						File localFile = new File(fileDirectory);
						if (!localFile.exists()) {
							localFile.mkdirs();
						}
						String filePath = fileDirectory + "/"+ fileName;
						localFile = new File(filePath);
						file.transferTo(localFile);
						String path = request.getContextPath();
						String fileUrl = path + fileShowPath + "/";
						return "{\"fileName\":\""
								+ URLEncoder.encode(myFileName, "UTF-8")
								+ "\",\"filePath\":\"" + fileUrl
								+ fileName
								+ "\"}";
					}
				}
				// 记录上传该文件后的时间
				// int finaltime = (int) System.currentTimeMillis();
				// System.out.println(finaltime - pre);
			}
		}
		return "error";
	}
	
	/**
	 * 检查权限，有返回 true
	 * 
	 * @param permission
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	protected String uploadFile(HttpServletRequest request, String fileDirectory)
			throws IllegalStateException, IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// //记录上传过程起始时的时间，用来计算上传时间
				// int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						// 重命名上传后的文件名
						String fileName = formatter.format(currentTime) + "."
								+ myFileName.split("\\.")[1];
						// 定义上传路径
						File localFile = new File(fileDirectory);
						if (!localFile.exists()) {
							localFile.mkdirs();
						}
						String filePath = fileDirectory + "/" + fileName;
						localFile = new File(filePath);
						file.transferTo(localFile);
						String path = request.getContextPath();
						String imgPath = path + imageShowPath + "/" + fileName;
						return "{\"fileName\":\"" + fileName
								+ "\",\"filePath\":\"" + imgPath + "\"}";
					}
				}
				// 记录上传该文件后的时间
				// int finaltime = (int) System.currentTimeMillis();
				// System.out.println(finaltime - pre);
			}
		}
		return "error";
	}

	/**
	 * type 0 for file;1 for image
	 */
	public void delFile(String fileName, HttpServletRequest request, int type) {
		String fileDirectory = request.getServletContext().getRealPath(
				imgeRootPath)
				+ "/" + fileName;
		switch (type) {
		case 0:
			fileDirectory = request.getServletContext().getRealPath(
					fileRootPath)
					+ "/" + fileName;
			break;
		case 1:
			fileDirectory = request.getServletContext().getRealPath(
					imgeRootPath)
					+ "/" + fileName;
			break;
		default:
			break;
		}
		File file = new File(fileDirectory);
		if (file.exists()) {
			file.delete();
		}
	}
}
