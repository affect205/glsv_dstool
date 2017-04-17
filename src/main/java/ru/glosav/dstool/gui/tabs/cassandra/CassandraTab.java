package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abalyshev on 14.04.17.
 */
@Component
public class CassandraTab extends Tab {
    @Autowired
    private CassandraPanel cassandraPanel;

    public CassandraTab() {
        super();
        setText("Cassandra");
        setClosable(false);
    }

    @PostConstruct
    public void onInit() {
        setContent(cassandraPanel);
    }
}
