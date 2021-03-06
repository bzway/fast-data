package ltd.fdsa.core.util;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtils {
    public static <T> T getProxyByJDK(T service, Class<T>... serviceType) {
        long begin = System.currentTimeMillis();
        System.out.println("记录程序开始时间getProxyByJDK => " + begin);
        if (serviceType.length <= 0) {
            serviceType = (Class<T>[]) service.getClass().getInterfaces();
        }
        T proxyService = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), serviceType,
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Object object = method.invoke(service, args);
                        long end = System.currentTimeMillis();
                        System.out.println("记录程序结束时间getProxyByJDK => " + end);
                        System.out.println("记录程序运行总时长getProxyByJDK => " + (end - begin));
                        return object;
                    }
                });
        return proxyService;
    }

    public static <T> T getProxyByCglib(T service) {

        long begin = System.currentTimeMillis();
        System.out.println("记录程序开始时间getProxyByCglib => " + begin);

        // 创建增强器
        Enhancer enhancer = new Enhancer();
        // 设置需要增强的类的对象
        enhancer.setSuperclass(service.getClass());
        // 设置回调方法
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

                Object object = methodProxy.invokeSuper(o, args);
                long end = System.currentTimeMillis();
                System.out.println("记录程序结束时间getProxyByCglib => " + end);
                System.out.println("记录程序运行总时长getProxyByCglib => " + (end - begin));
                return object;
            }
        });
        T proxyService = (T) enhancer.create();
        return proxyService;
    }
}
