package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abalyshev on 14.04.17.
 */
@Component
public class CqlTab extends Tab {
    @Autowired
    private CqlPanel cassandraPanel;

    public CqlTab() {
        super();
        setText("Cassandra");
        setClosable(false);
    }

    @PostConstruct
    public void onInit() {
        setContent(cassandraPanel);
    }
}
