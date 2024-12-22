package com.toy.namoner.common.threadLocal;

public class ThreadLocalVariable {
    public static final ThreadLocal<Long> requestTime = new ThreadLocal<>();

    public static void setRequestTime(Long time) {
        requestTime.set(time);
    }
    public static Long getRequestTime() {
        return requestTime.get();
    }

    public static void clearAllVariable() {
        requestTime.remove();
    }
}
