package ru.glosav.dstool.service.adapters;

import ru.glosav.dstool.entity.rows.dto.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by abalyshev on 26.04.17.
 */
public interface ICqlAdapter {
    List<MessageDTO> getTrackData(Collection<Long> devops, long from, long to);
    List<MessageDTO> getTrackData(int deviceId, int operatorId, long from, long to);
    List<EventDTO> getEventData(int deviceId, int operatorId, long from, long to);
    List<EventDTO> getEventData(Collection<Long> keys, long from, long to);
    List<EventDTO> getEventDataByTypes(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes);
    List<MessageDTO> getLastPoint(Collection<Long> keys);
    List<MessageDTO> getLastPoint(int deviceId, int operatorId);
    List<DeviceRoutesDTO> getDeviceRoutes(Collection<Long> keys, long from, long to);
    List<DeviceRoutesDTO> getDeviceRoutes(int deviceId, int operatorId, long from, long to);
    List<EdgeDTO> getDevopsByEdges(Collection<Long> edges, long from, long to);
    List<CountDTO> getDeviceRoutesCount(Collection<Long> keys, long from, long to);
    List<CountDTO> getTrackDataCount(Collection<Long> keys, long from, long to);
    List<CountDTO> getEventDataCount(Collection<Long> keys, long from, long to);
    List<CountDTO> getEventDataByTypesCount(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes);
    List<CountDTO> getDevopsByEdgesCount(Collection<Long> edges, long from, long to);
    List<CountDTO> getLastPointCount(Collection<Long> keys);
}
