package ru.glosav.dstool.service.adapters;

import ru.glosav.dstool.entity.rows.dto.DTOResult;

import java.util.Collection;

/**
 * Created by abalyshev on 26.04.17.
 */
public interface ICqlAdapter {
    DTOResult getTrackData(Collection<Long> devops, long from, long to);
    DTOResult getTrackData(int deviceId, int operatorId, long from, long to);
    DTOResult getEventData(int deviceId, int operatorId, long from, long to);
    DTOResult getEventData(Collection<Long> keys, long from, long to);
    DTOResult getEventDataByTypes(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes);
    DTOResult getLastPoint(Collection<Long> keys);
    DTOResult getLastPoint(int deviceId, int operatorId);
    DTOResult getDeviceRoutes(Collection<Long> keys, long from, long to);
    DTOResult getDeviceRoutes(int deviceId, int operatorId, long from, long to);
    DTOResult getDevopsByEdges(Collection<Long> edges, long from, long to);
    DTOResult getDeviceRoutesCount(Collection<Long> keys, long from, long to);
    DTOResult getTrackDataCount(Collection<Long> keys, long from, long to);
    DTOResult getEventDataCount(Collection<Long> keys, long from, long to);
    DTOResult getEventDataByTypesCount(Collection<Long> keys, long from, long to, Collection<Integer> evtTypes);
    DTOResult getDevopsByEdgesCount(Collection<Long> edges, long from, long to);
    DTOResult getLastPointCount(Collection<Long> keys);
}
