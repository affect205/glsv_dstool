package ru.glosav.dstool.conn;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.glosav.cassandra.dao.impl.CassandraGate;
import ru.glosav.dstool.entity.CqlConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * Created by abalyshev on 14.04.17.
 */
@Component
@Scope(SCOPE_SINGLETON)
public class ClusterConnector {
    private Cluster activeCluster;
    private CqlConnection activeCqlConn;
    private Session session;
    private Set<CqlConnection> cqlConnPool;

    @PostConstruct
    public void onInit() {
        cqlConnPool = new HashSet<>();
    }

    public void connect(CqlConnection cqlConn) {
        if (cqlConn == null) return;
        activeCqlConn = cqlConn;
        cqlConnPool.add(activeCqlConn);
        if (activeCluster != null)
            activeCluster.close();
        activeCluster = Cluster
                .builder()
                .addContactPoint(cqlConn.getUrl())
                .build();
        if (activeCqlConn.hasKeySpace()) {
            session = activeCluster.connect(activeCqlConn.getKeySpace());
        } else {
            session = activeCluster.connect();
        }
        CassandraGate.ensureStatementsPrepared(session);
    }

    public Session getSession() {
        if (activeCluster == null || activeCqlConn == null) return null;
        if (session == null || session.isClosed()) {
            session = activeCluster.connect(activeCqlConn.getKeySpace());
            CassandraGate.ensureStatementsPrepared(session);
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
