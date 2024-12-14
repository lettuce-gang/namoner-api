package com.toy.namoneo.common.logs;

import com.toy.namoneo.common.threadLocal.ThreadLocalVariable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final String RESPONSE_LOG_FORMAT = "[{}] {} {}, Elapsed Time: {}ms";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String requestURI = request.getRequestURI();
            ThreadLocalVariable.setRequestTime(System.currentTimeMillis());

            log.info("{} {}", request.getMethod(), requestURI);
        } catch (Exception e) {
            log.error("Exception in Interceptor: {}", e);
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        final long elapsedTime = System.currentTimeMillis() - ThreadLocalVariable.getRequestTime();

        log.info(RESPONSE_LOG_FORMAT, response.getStatus(), request.getMethod(), request.getRequestURI(), elapsedTime);
    }


}
