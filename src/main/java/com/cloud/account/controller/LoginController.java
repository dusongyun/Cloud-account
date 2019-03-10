package com.cloud.account.controller;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cloud.account.entity.UserAdmin;
import com.cloud.account.service.UserAdminService;
import com.cloud.account.vo.CaptchaVo;
import com.cloud.account.vo.LoginVo;
import com.cloud.util.StringUtils;
import com.cloud.util.captcha.Captcha;
import com.cloud.util.http.result.BaseResp;
import com.cloud.util.http.result.BusinessMsgConstants;
import com.cloud.util.http.result.MsgConstants;


/**
 * @描述: 登录控制层
 * @作者: dusai
 * @作成时间: 2018年4月16日 下午3:39:19
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	 private Logger logger = LoggerFactory.getLogger(LoginController.class);
	 /**
	     * @throws IOException
	     * @描述: 未登錄信息
	     * @作者: dusai
	     * @时间: 2018年4月19日 下午4:35:31
	     */
	    @RequestMapping(value = "/loginMsg")
	    public void loginMsg(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) throws IOException {

	        response.setHeader("Content-Type", "text/html;charset=UTF-8");
	        PrintWriter pw = response.getWriter();
	        pw.write("{\"success\":false,\"code\":-1,\"msg\":\"用户未登录\"}");

	        pw.flush();
	        pw.close();
	    }
	    
	    /**
	     * @描述: 发送图形验证码
	     * @作者: dusai
	     * @时间: 2018年4月16日 下午3:40:35
	     */
	    @RequestMapping(value = "/captchaImage")
	    public void captchaImage(HttpServletRequest request, HttpServletResponse response) {
	        logger.debug("RegisterController#captchaImage START");

	        // 设置响应的类型格式为图片格式
	        response.setContentType("image/jpeg");
	        // 禁止图像缓存。
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        // 实例生成验证码对象
	        // 调用工具类生成的验证码和验证码图片
	        Map<String, Object> codeMap = Captcha.generateCodeAndPic();
	        System.out.println(codeMap.get("code").toString());
	        // 将验证码存入session
	        request.getSession().setAttribute("verification", codeMap.get("code").toString());
	        // 获取session
	        HttpSession session = request.getSession();
	        // 获取session中所有的键值
	        String attrs = session.getId();
	        // 打印结果
	        System.out.println("------" + attrs + ":" + attrs + "--------\n");
	        // 向页面输出验证码图片
	        try {
	            // 将图像输出到Servlet输出流中。
	            ServletOutputStream sos = response.getOutputStream();
	            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
	            sos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        logger.debug("RegisterController#captchaImage END");
	    }

	    /**
	     * @描述: 检测图形验证码
	     * @作者: dusai
	     * @时间: 2018年4月16日 下午3:40:35
	     */
	    @RequestMapping(value = "/checkCaptcha")
	    @ResponseBody
	    public Object checkCaptcha(@RequestParam(value = "info", required = false) String info, HttpServletRequest request,
	            HttpServletResponse response) {
	        logger.debug("RegisterController#checkCaptcha START");
	        CaptchaVo captchaVo = JSON.parseObject(info, CaptchaVo.class);
	        String code = captchaVo.getVerification();
	        String sessionCode = (String) request.getSession().getAttribute("verification");
	        System.out.println("sessionCode---->>" + sessionCode);
	        // 获取session
	        HttpSession session = request.getSession();
	        // 获取session中所有的键值
	        Enumeration<String> attrs = session.getAttributeNames();
	        // 遍历attrs中的
	        while (attrs.hasMoreElements()) {
	            // 获取session键值
	            String name = attrs.nextElement().toString();
	            // 根据键值取session中的值
	            Object vakue = session.getAttribute(name);
	            // 打印结果
	            System.out.println("------" + name + ":" + vakue + "--------\n");
	        }

	        // 忽略验证码大小写
	        if (!sessionCode.equalsIgnoreCase(code)) {
	            return BaseResp.fail("9999", MsgConstants.ERROR_CAPTCHA_MSG);
	        }

	        logger.debug("RegisterController#checkCaptcha END");

	        return BaseResp.defaultSuccess();
	    }
	}