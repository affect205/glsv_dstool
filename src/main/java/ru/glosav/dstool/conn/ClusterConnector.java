package ru.glosav.dstool.conn;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.glosav.dstool.entity.CqlConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * Created by abalyshev on 14.04.17.
 */
@Component
@Scope(SCOPE_SINGLETON)
public class ClusterConnector {
    private Cluster activeCluster;
    private Session session;
    private Set<CqlConnection> cqlConnPool;

    @PostConstruct
    public void onInit() {
        cqlConnPool = new HashSet<>();
    }

    public void connect(CqlConnection cqlConn) {
        if (cqlConn == null) return;
        cqlConnPool.add(cqlConn);
        if (activeCluster != null)
            activeCluster.close();
        activeCluster = Cluster
                .builder()
                .addContactPoint(cqlConn.getUrl())
                .build();
        if (cqlConn.hasKeySpace()) {
            activeCluster.connect(cqlConn.getKeySpace());
        } else {
            activeCluster.connect();
        }
    }

    public Session getSession() {
        if (activeCluster == null) return null;
        if (session == null || session.isClosed()) {
            session = activeCluster.newSession();
        }
        return session;
    }

    @PreDestroy
    public void onDestroy() {
        cqlConnPool.clear();
        if (session != null && !session.isClosed())
            session.close();
        if (activeCluster != null && !activeCluster.isClosed())
            activeCluster.close();
    }
}
