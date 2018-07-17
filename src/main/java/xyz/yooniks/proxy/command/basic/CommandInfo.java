package xyz.yooniks.proxy.command.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface CommandInfo {

  String name();

  String[] aliases() default "";

  String description() default "Brak opisu.";

  boolean gameOnly() default true;

}
