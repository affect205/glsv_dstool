package ru.glosav.dstool.utils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 20.07.16.
 */
public class ClusterProvider {

    private static List<ClusterProvider> connections;
    private static int nextConnectionPoint = 0;

    private static final int DEF_CONNECTIONS = 4;

    public static ClusterProvider getConnection() {
        if (connections == null) {
            initConnections(DEF_CONNECTIONS);
        }
        ClusterProvider clusterConnector = connections.get(nextConnectionPoint);
        nextConnectionPoint++;
        if (nextConnectionPoint >= DEF_CONNECTIONS)
            nextConnectionPoint = 0;
        return clusterConnector;
    }

    public static void initConnections(int CONNECTION_COUNT) {
        System.out.println("initConnections...");
        connections = new ArrayList<>(CONNECTION_COUNT);
        for (int i = 0; i < CONNECTION_COUNT; i++) {
            connections.add(get());
        }
    }

    public static void destroyConnections() {
        System.out.println("destroyConnections...");
        connections.forEach((ClusterProvider::close));
    }

    private static ClusterProvider get() {
        Cluster cluster = Cluster.builder()
                .addContactPoint("10.0.1.28")
                .addContactPoint("127.0.0.1")
                .addContactPoint("10.0.5.146")
                .addContactPoint("10.0.1.194")
                .build();
        Session session = cluster.connect("ks");
        return new ClusterProvider(cluster, session);
    }

    private Cluster cluster;
    private Session session;


    private ClusterProvider(Cluster cluster, Session session) {
        this.cluster = cluster;
        this.session = session;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        if (cluster != null) cluster.close();
    }
}
