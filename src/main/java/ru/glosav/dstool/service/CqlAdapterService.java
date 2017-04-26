package ru.glosav.dstool.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glosav.cassandra.dao.CDeviceRoutes;
import ru.glosav.cassandra.dao.impl.CassandraGate;
import ru.glosav.dstool.conn.ClusterConnector;
import ru.glosav.dstool.entity.rows.dto.*;
import ru.glosav.dstool.service.adapters.ICqlAdapter;
import ru.glosav.kiask.protobuf.generated.event.EventProto;
import ru.glosav.kiask.protobuf.generated.message.MessageProto;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static ru.glosav.cassandra.utils.DateUtils.atTheBeginOfSpan;
import static ru.glosav.cassandra.utils.DeviceKeySet.generate;

/**
 * Created by abalyshev on 26.04.17.
 */
@Service
public class CqlAdapterService implements ICqlAdapter {

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
    public List<MessageDTO> getTrackData(Collection<Long> devops, long from, long to) {
        Map<Long, List<MessageProto.MESSAGE>> rawData = CassandraGate.getTrackData(connector.getSession(), devops, from, to);
        List<MessageDTO> result = getTrackDataInternal(rawData);
        return result;
    }

    @Override
    public List<MessageDTO> getTrackData(int deviceId, int operatorId, long from, long to) {
        long devOp = generate(deviceId, operatorId);
        return getTrackData(asList(devOp), from, to);
    }

    @Override
    public List<EventDTO> getEventData(Collection<Long> devops, long from, long to) {
        Map<Long, List<EventProto.EVENT>> rawData = CassandraGate.getEventData(connector.getSession(), devops, from, to);
        List<EventDTO> result = getEventDataInternal(rawData);
        return result;
    }

    @Override
    public List<EventDTO> getEventData(int deviceId, int operatorId, long from, long to) {
        long devOp = generate(deviceId, operatorId);
        return getEventData(asList(devOp), from, to);
    }

    @Override
    public List<EventDTO> getEventDataByTypes(Collection<Long> devops, long from, long to, Collection<Integer> evtTypes) {
        Map<Long, List<EventProto.EVENT>> rawData = CassandraGate.getEventDataByTypes(connector.getSession(), devops, from, to, evtTypes);
        List<EventDTO> result = getEventDataInternal(rawData);
        return result;
    }

    @Override
    public List<MessageDTO> getLastPoint(Collection<Long> devops) {
        Map<Long, MessageProto.MESSAGE> rawData = CassandraGate.getLastPoint(connector.getSession(), devops);
        List<MessageDTO> result = getTrackDataInternal(rawData.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue() == null ? emptyList() : asList(e.getValue())
                ))
        );
        return result;
    }

    @Override
    public List<MessageDTO> getLastPoint(int deviceId, int operatorId) {
        long devOp = generate(deviceId, operatorId);
        return getLastPoint(asList(devOp));
    }

    @Override
    public List<DeviceRoutesDTO> getDeviceRoutes(Collection<Long> devops, long from, long to) {
        List<DeviceRoutesDTO> result = new ArrayList<>();
        Map<Long, List<CDeviceRoutes>> rawData = CassandraGate.getDeviceRoutes(connector.getSession(), devops, from, to);
        rawData.values().forEach(v -> v.forEach(dr -> {
            DeviceRoutesDTO dto = new DeviceRoutesDTO();
            result.add(dto);
        }));
        return result;
    }

    @Override
    public List<DeviceRoutesDTO> getDeviceRoutes(int deviceId, int operatorId, long from, long to) {
        long devOp = generate(deviceId, operatorId);
        return getDeviceRoutes(asList(devOp), from, to);
    }

    @Override
    public List<EdgeDTO> getDevopsByEdges(Collection<Long> edges, long from, long to) {
        List<EdgeDTO> result = new ArrayList<>();
        List<Long> rawData = CassandraGate.getDevopsByEdges(connector.getSession(), edges, from, to);
        rawData.forEach(e -> {
            result.add(new EdgeDTO());
        });
        return result;
    }

    @Override
    public List<CountDTO> getDeviceRoutesCount(Collection<Long> devops, long from, long to) {
        long rawData = CassandraGate.getDeviceRoutesCount(connector.getSession(), devops, from, to);
        return asList(new CountDTO(rawData));
    }

    @Override
    public List<CountDTO> getTrackDataCount(Collection<Long> devops, long from, long to) {
        long rawData = CassandraGate.getTrackDataCount(connector.getSession(), devops, from, to);
        return asList(new CountDTO(rawData));
    }

    @Override
    public List<CountDTO> getEventDataCount(Collection<Long> devops, long from, long to) {
        long rawData = CassandraGate.getEventDataCount(connector.getSession(), devops, from, to);
        return asList(new CountDTO(rawData));
    }

    @Override
    public List<CountDTO> getEventDataByTypesCount(Collection<Long> devops, long from, long to, Collection<Integer> evtTypes) {
        long rawData = CassandraGate.getEventDataByTypesCount(connector.getSession(), devops, from, to, evtTypes);
        return asList(new CountDTO(rawData));
    }

    @Override
    public List<CountDTO> getDevopsByEdgesCount(Collection<Long> edges, long from, long to) {
        long rawData = CassandraGate.getDevopsByEdgesCount(connector.getSession(), edges, from, to);
        return asList(new CountDTO(rawData));
    }

    @Override
    public List<CountDTO> getLastPointCount(Collection<Long> devops) {
        long rawData = CassandraGate.getLastPointCount(connector.getSession(), devops);
        return asList(new CountDTO(rawData));
    }

    private List<MessageDTO> getTrackDataInternal(Map<Long, List<MessageProto.MESSAGE>> rawData) {
        List<MessageDTO> result = new ArrayList<>();
        rawData.values().forEach(v -> v.forEach(msg -> {
            int devId   = msg.getHeader().getOid();
            int opId    = msg.getHeader().getOpid();
            long devOp  = generate(devId, opId);
            long ts     = msg.getHeader().getTm();
            long span   = atTheBeginOfSpan(ts, ChronoUnit.MONTHS);
            MessageDTO dto = new MessageDTO(devOp, devId, opId, span, ts, msg);
            result.add(dto);
        }));
        return result;
    }

    private List<EventDTO> getEventDataInternal(Map<Long, List<EventProto.EVENT>> rawData) {
        List<EventDTO> result = new ArrayList<>();
        rawData.values().forEach(v -> v.forEach(evt -> {
            int devId   = evt.getHeader().getDeviceId();
            int opId    = evt.getHeader().getOperatorId();
            long devOp  = generate(devId, opId);
            int type    = evt.getHeader().getEventType().getNumber();
            long ts     = evt.getHeader().getUtc();
            long span   = atTheBeginOfSpan(ts, ChronoUnit.MONTHS);
            EventDTO dto = new EventDTO(devOp, devId, opId, type, span, ts, evt);
            result.add(dto);
        }));
        return result;
    }
}
