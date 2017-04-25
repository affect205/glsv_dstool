package ru.glosav.dstool.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glosav.cassandra.dao.CDeviceRoutes;
import ru.glosav.cassandra.dao.ICassandraDaoLocal;
import ru.glosav.cassandra.dao.impl.CassandraGate;
import ru.glosav.dstool.conn.ClusterConnector;
import ru.glosav.kiask.protobuf.generated.event.EventProto;
import ru.glosav.kiask.protobuf.generated.message.MessageProto;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

/**
 * Created by abalyshev on 24.04.17.
 */
@Service
public class CqlService implements ICassandraDaoLocal {

    @Autowired
    ClusterConnector connector;

    public List<String> execute(String query) {
        List<String> result = new LinkedList<>();
        ResultSet resultSet = connector.getSession().execute(query);
        for(Row row: resultSet.all()) {
            String json = row.getString(0);
            result.add(json);
        }
        return result;
    }

    @Override
    public void persistTrack(MessageProto.MESSAGE msg) {

    }

    @Override
    public void persistEvent(EventProto.EVENT evt) {

    }

    @Override
    public Map<Long, List<MessageProto.MESSAGE>> getTrackData(Collection<Long> keys, long from, long to) {
        return CassandraGate.getTrackData(connector.getSession(), keys, from, to);
    }

    @Override
    public List<MessageProto.MESSAGE> getTrackData(int deviceId, int operatorId, long from, long to) {
        return emptyList();
    }

    @Override
    public List<EventProto.EVENT> getEventData(int deviceId, int operatorId, long from, long to) {
        return emptyList();
    }

    @Override
    public Map<Long, List<EventProto.EVENT>> getEventData(Collection<Long> keys, long from, long to) {
        return emptyMap();
    }

    @Override
    public Map<Long, List<EventProto.EVENT>> getEventDataByTypes(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes) {
        Map<Long, List<EventProto.EVENT>> result = CassandraGate.getEventDataByTypes(connector.getSession(), keys, from, to, evtTypes);
        return result;
    }

    @Override
    public Map<Long, MessageProto.MESSAGE> getLastPoint(Collection<Long> keys) {
        return emptyMap();
    }

    @Override
    public MessageProto.MESSAGE getLastPoint(int deviceId, int operatorId) {
        return null;
    }

    @Override
    public Map<Long, List<CDeviceRoutes>> getDeviceRoutes(Collection<Long> keys, long from, long to) {
        return emptyMap();
    }

    @Override
    public List<CDeviceRoutes> getDeviceRoutes(int deviceId, int operatorId, long from, long to) {
        return emptyList();
    }

    @Override
    public Map<Long, List<MessageProto.MESSAGE>> getTrackDataByEdges(Collection<Long> edges, long from, long to) {
        return emptyMap();
    }

    @Override
    public Map<Long, List<ByteBuffer>> getEventDataAsRaw(Collection<Long> keys, long from, long to) {
        return emptyMap();
    }

    @Override
    public Map<Long, List<ByteBuffer>> getEventDataByTypesAsRaw(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes) {
        return emptyMap();
    }

    @Override
    public Map<Long, List<ByteBuffer>> getTrackDataAsRaw(Collection<Long> keys, long from, long to) {
        return emptyMap();
    }

    @Override
    public Map<Long, ByteBuffer> getLastPointAsRaw(Collection<Long> keys) {
        return emptyMap();
    }

    @Override
    public Map<Long, List<ByteBuffer>> getTrackDataByEdgesAsRaw(Collection<Long> edges, long from, long to) {
        return emptyMap();
    }

    @Override
    public List<Long> getDevopsByEdges(Collection<Long> edges, long from, long to) {
        return emptyList();
    }

    @Override
    public long getDeviceRoutesCount(Collection<Long> keys, long from, long to) {
        return -1;
    }

    @Override
    public long getTrackDataCount(Collection<Long> keys, long from, long to) {
        return -1;
    }

    @Override
    public long getEventDataCount(Collection<Long> keys, long from, long to) {
        return CassandraGate.getEventDataCount(connector.getSession(), keys, from, to);
    }

    @Override
    public long getEventDataByTypesCount(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes) {
        return -1;
    }

    @Override
    public long getDevopsByEdgesCount(Collection<Long> edges, long from, long to) {
        return -1;
    }

    @Override
    public long getLastPointCount(Collection<Long> keys) {
        return -1;
    }
}
