package edu.cuit.common.web.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.cuit.common.exception.CustomException;
import edu.cuit.common.web.controller.BaseController;

@ControllerAdvice
public class DefaultExceptionController extends BaseController {
	
	@ExceptionHandler({ UnauthenticatedException.class })
	public ModelAndView processUnauthenticatedException(
			NativeWebRequest request, UnauthenticatedException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "没有登陆");
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	@ExceptionHandler({ NumberFormatException.class })
	public ModelAndView processNumberFormatExceptionException(
			NativeWebRequest request, NumberFormatException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "参数异常");
		mv.setViewName("error/index");
		return mv;
	}

	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView processUnauthorizedException(
			NativeWebRequest request, UnauthorizedException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "没有权限");
		mv.setViewName("error/unauthorized");
		return mv;
	}
	
	/**
	 * 异常CustomException处理
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value={CustomException.class})
	public ModelAndView processCustomException(CustomException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e.toString());
		mv.setViewName("error/index");
		return mv;
	}

	@ExceptionHandler
	public ModelAndView handleException(Exception e, NativeWebRequest request) {
		log.debug(e.toString());
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "未知异常");
		mv.setViewName("error/index");
		e.printStackTrace();
		return mv;
	}
}
