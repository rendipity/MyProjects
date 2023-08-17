package com.publicapi.apimanage.web.base.interceptor;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.publicapi.apimanage.common.Result;
import com.publicapi.apimanage.common.UserContext;
import com.publicapi.apimanage.common.UserInfo;
import com.publicapi.apimanage.common.utils.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.publicapi.apimanage.common.enums.ErrorResultEnum.ACCESS_FORBIDDEN;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // springboot跨域请求  放行OPTIONS请求
        if(request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;//通过所有OPTION请求
        }
        String token = request.getHeader("Authorization");
        // token不存在或者token校验失败
        if (ObjectUtil.isEmpty(token) || !TokenUtil.verify(token)) {
            printResponse(response);
            return false;
        }
        // token解析
        UserInfo userInfo = TokenUtil.parse(token);
        // todo 从redis获取状态有变更的用户，如果包含该用户则从数据库获取最新的用户信息
        UserContext.set(userInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
    public void printResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.getValue());
        response.getWriter().println(JSONUtil.toJsonStr(Result.fail(ACCESS_FORBIDDEN)));
    }

}
