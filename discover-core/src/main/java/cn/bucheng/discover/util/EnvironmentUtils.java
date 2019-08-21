package cn.bucheng.discover.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * @author ：yinchong
 * @create ：2019/8/21 10:49
 * @description：
 * @modified By：
 * @version:
 */
public class EnvironmentUtils implements EnvironmentPostProcessor {
    private static Environment environment;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        EnvironmentUtils.environment = environment;
    }

    public static String getValue(String key){
        return environment.getProperty(key);
    }
}
