package cc.util.android.viewInject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangcccong
 * @version 1.140122
 */
@Target (ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)
@cc.util.java.core.BaseInject
public @interface ResInject {
	int value();          //res id
	int pId() default 0;  //res parentid (when type == ResType.View)
	ResType type() default ResType.View; //res type
}
