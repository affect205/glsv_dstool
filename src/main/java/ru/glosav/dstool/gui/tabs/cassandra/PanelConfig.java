package ru.glosav.dstool.gui.tabs.cassandra;

/**
 * Created by abalyshev on 18.04.17.
 */
//@Configuration
//@Lazy
public class PanelConfig {
    //@Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CqlQueryPanel createCassandraQueryPanel() {
        return new CqlQueryPanel();
    }
}
