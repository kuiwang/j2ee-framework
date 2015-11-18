package com.fengjing.framework.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.utils.Exceptions;

/**
 * ShiroAuthController����򿪵�¼ҳ��(GET����)�͵�¼����ҳ��(POST����)
 * ������¼��POST������Filter���
 * 
 * @author scott
 *
 */

@Controller
@RequestMapping(value = "/shiro")
public class ShiroAuthController {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroAuthController.class);

    private static final String LOGIN_PAGE = "shiro/login";

    private static final String unauthorizedUrl = "error/401";

    /**
     * ����򿪵�¼ҳ��
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        return LOGIN_PAGE;
    }

    /**
     * �����δ��Ȩҳ��
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/unauthorized")
    public String unauthorized(HttpServletRequest request) {
        return unauthorizedUrl;
    }

    /**
     * ��¼����ҳ��(POST����)
     * 
     * @param username
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(
            @RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,
            Model model, HttpServletRequest request) {
        String msg = parseException(request);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, msg);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        return LOGIN_PAGE;
    }

    private String parseException(HttpServletRequest request) {
        String errorString = (String) request
                .getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        Class<?> error = null;
        try {
            if (errorString != null) {
                error = Class.forName(errorString);
            }
        } catch (ClassNotFoundException e) {
            LOG.error(Exceptions.getStackTraceAsString(e));
        }

        String msg = "δ֪����";
        if (error != null) {
            if (error.equals(AccountException.class)) msg = "�ʺŲ����ڣ�";
            else if (error.equals(UnknownAccountException.class)) msg = "�ʺŲ����ڣ�";
            else if (error.equals(IncorrectCredentialsException.class)) msg = "�������";
            else if (error.equals(IncorrectCaptchaException.class)) msg = "��֤�����";
            else if (error.equals(AuthenticationException.class)) msg = "��֤ʧ�ܣ�";
            else if (error.equals(DisabledAccountException.class)) msg = "�˺ű����ᣡ";
        }
        return "��¼ʧ�ܣ�" + msg;
    }

}
