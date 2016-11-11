package com.yyyu.barbecue.ezbooking_base.annotate;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 功能：实现注入
 * Created by yyyu on 2016/6/24.
 */
public class ViewInjectUtils {

    private static final String TAG = "ViewInjectUtils";

    private static final String FIND_VIEW_BY_ID = "findViewById";

    private static final String SET_CONTENT_VIEW = "setContentView";


    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    /**
     * 事件的注入
     * 1.得到所有的方法
     * 2.得到方法上的注解
     * 3.得到方法注解上的注解
     * 4得到监听类型、set方法、调用方法
     */
    private static void injectEvents(Activity activity){
        Method[] methods = activity.getClass().getMethods();//1
        for (Method method:methods) {
            Annotation[] annotations = method.getAnnotations();//2
            for (Annotation annotation : annotations){
                //---Returns the type of this annotation.
                Class<?extends Annotation> annotationType = annotation.annotationType();
                EventBase evnetAnnotation = annotationType.getAnnotation(EventBase.class);//3
                if(evnetAnnotation!=null){//4
                    Class<?> listenerType = evnetAnnotation.ListenerType();
                    String listenerSetter = evnetAnnotation.ListenerSetter();
                    String methodName = evnetAnnotation.methodName();
                    try {
                        //通过反射得到onClick方法中的value方法
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        //取出所有的viewId
                        int[] viewIds = (int[]) aMethod.invoke(annotation);
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(
                                listenerType.getClassLoader(),
                                new Class<?>[] { listenerType }, handler);
                        for (int viewId:viewIds) {
                            View view = activity.findViewById(viewId);
                            Method setEventMethod = view.getClass().getMethod(listenerSetter , listenerType);
                            setEventMethod.invoke(view ,listener );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 注入Activity的布局
     */
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            try {
                Method setContentView = clazz.getMethod(SET_CONTENT_VIEW, int.class);
                int activityId = contentView.value();
                if (activityId != -1) {
                    setContentView.setAccessible(true);
                    setContentView.invoke(activity, activityId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "injectContentView出现异常！！" + e.getMessage());
            }
        }
    }

    /**
     * 注入所有的View
     *
     * @param activity
     */
    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject fieldAnnotation = field.getAnnotation(ViewInject.class);
            if (fieldAnnotation != null) {//该View被注解了
                int viewId = fieldAnnotation.value();
                if (viewId != -1) {
                    try {
                        Method findView = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
                        Object resView = findView.invoke(activity, viewId);
                        field.setAccessible(true);
                        field.set(activity, resView);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "injectViews出现异常！！" + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 注入Fragment中的控件
     */
    public static void injectView(Fragment fragment , View target){
        Class<? extends Fragment> clazz = fragment.getClass();
        Class<? extends View> targetClazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject fieldAnnotation = field.getAnnotation(ViewInject.class);
            if (fieldAnnotation != null) {//该View被注解了
                int viewId = fieldAnnotation.value();
                if (viewId != -1) {
                    try {
                        Method findView = targetClazz.getMethod(FIND_VIEW_BY_ID, int.class);
                        Object resView = findView.invoke(target, viewId);
                        field.setAccessible(true);
                        field.set(fragment, resView);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "injectViews出现异常！！" + e.getMessage());
                    }
                }
            }
        }
    }

    public static void injectEvents(Fragment fragment , View target){
        Method[] methods = fragment.getClass().getMethods();//1
        for (Method method:methods) {
            Annotation[] annotations = method.getAnnotations();//2
            for (Annotation annotation : annotations){
                //---Returns the type of this annotation.
                Class<?extends Annotation> annotationType = annotation.annotationType();
                EventBase evnetAnnotation = annotationType.getAnnotation(EventBase.class);//3
                if(evnetAnnotation!=null){//4
                    Class<?> listenerType = evnetAnnotation.ListenerType();
                    String listenerSetter = evnetAnnotation.ListenerSetter();
                    String methodName = evnetAnnotation.methodName();
                    try {
                        //通过反射得到onClick方法中的value方法
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        //取出所有的viewId
                        int[] viewIds = (int[]) aMethod.invoke(annotation);
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(fragment);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(
                                listenerType.getClassLoader(),
                                new Class<?>[] { listenerType }, handler);
                        for (int viewId:viewIds) {
                            View view = target.findViewById(viewId);
                            Method setEventMethod = view.getClass().getMethod(listenerSetter , listenerType);
                            setEventMethod.invoke(view ,listener );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
