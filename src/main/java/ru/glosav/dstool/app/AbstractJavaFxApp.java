package ru.glosav.dstool.app;

import javafx.application.Application;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by abalyshev on 14.04.17.
 */
public abstract class AbstractJavaFxApp extends Application {
    protected static String[] savedArgs;

    protected ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
//        context = SpringApplication.run(getClass(), savedArgs);
//        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApp> clazz, String[] args) {
        AbstractJavaFxApp.savedArgs = args;
        Application.launch(clazz, args);
    }
}