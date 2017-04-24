package ru.glosav.dstool.gui.tabs.cassandra;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Created by abalyshev on 18.04.17.
 */
//@Configuration
//@Lazy
public class PanelConfig {
    //@Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CassandraQueryPanel createCassandraQueryPanel() {
        return new CassandraQueryPanel();
    }
}
