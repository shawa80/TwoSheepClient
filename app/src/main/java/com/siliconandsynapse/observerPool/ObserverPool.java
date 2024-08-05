package com.siliconandsynapse.observerPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
 
public class ObserverPool<T> {
        private final List<T> observers;       
        private final T dispatcher;
 
        @SuppressWarnings("unchecked")
		public ObserverPool(Class<?> observerClass) {
                observers = new ArrayList<T>();  
                dispatcher = (T) Proxy.newProxyInstance(
                		observerClass.getClassLoader(),
                        new Class[] { observerClass },
                        new Dispatcher()
                );
        }
 
        public void add(T observer) {
                synchronized (observers) {
                        observers.add(observer);
                }
        }
 
        public void remove(T observer) {
                synchronized (observers) {
                        observers.remove(observer);
                }
        }
        public void clear() {
                synchronized (observers) {
                        observers.clear();
                }
        }
       
        public T getDispatcher() {

                return dispatcher;
        }              
               
        public boolean hasObservers() {
                return !observers.isEmpty();
        }
 
        private class Dispatcher implements InvocationHandler {
                public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                        List<Throwable> exceptions = new ArrayList<Throwable>();
                        return dispatchToObservers(method, exceptions, args);         
                }
 
                private Object dispatchToObservers(Method method, List<Throwable> exceptionsThrown, Object... args) {
                        try {
                                synchronized (observers) {
                                        for (Object observer : observers) {
                                                try {
                                                        method.invoke(observer, args);
                                                } catch (RuntimeException re) {
                                                        exceptionsThrown.add(re);
                                                } catch (Exception e) {
                                                        exceptionsThrown.add(e);
                                                }
                                        }
                                }
                        } catch (IllegalArgumentException e) {
                                exceptionsThrown.add(e);                               
                        }
                       
                        if (exceptionsThrown.isEmpty()) {
                                return null;
                        } else {
                                throw new DispatchException(exceptionsThrown);
                        }
                }
 
        }
       
        public static class DispatchException extends RuntimeException {
                /**
			 * 
			 */
			private static final long serialVersionUID = 6726380404230408924L;
				private final List<Throwable> exceptionsThrown;
 
                public DispatchException(List<Throwable> exceptionsThrown) {
                        this.exceptionsThrown = exceptionsThrown;
                }
               
                public List<Throwable> getExceptionsThrown() {
                        return exceptionsThrown;
                }
        }
}