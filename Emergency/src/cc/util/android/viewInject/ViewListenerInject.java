package cc.util.android.viewInject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangcccong
 * @version 1.140122
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
@cc.util.java.core.BaseInject
public @interface ViewListenerInject {
	int[] value();
	int[] pid() default 0;
	ViewListenerType type();
}
