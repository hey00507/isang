package com.isang.api.config;

import com.isang.common.exception.custom.Unauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

   // 컨트롤러 가기전에 무조건 실행
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
/**
 * 요기에서 문제가 없으면, 통과 있으면 안통과!
 */

        log.info("access By IP : {}", request.getRemoteAddr());
        String accessToken = request.getParameter("accessToken");
        log.info("accessToken  >> {}", accessToken);

        if(!ObjectUtils.isEmpty(accessToken)){
            request.setAttribute("userName", accessToken);
            return true;
        }


        log.warn("> Not Valid Authorization");
        throw  new Unauthorized();
    }

    //컨트롤러가 뷰를 반환한 후에 실행되며 무조건 실행
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info(">> Post Handle");
    }

    // 모든 절차가 끝난 후에 실행
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        log.info(">> afterCompletion");
    }
}
