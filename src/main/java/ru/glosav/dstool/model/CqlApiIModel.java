package ru.glosav.dstool.model;

import ru.glosav.dstool.entity.CqlApiMethod;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.glosav.dstool.enums.ArgName.*;

/**
 * Created by abalyshev on 25.04.17.
 */
public class CqlApiIModel {
    public static final String GET_TRACKDATA3 = "getTrackData:3";
    public static final String GET_TRACKDATA4 = "getTrackData:4";
    public static final String GET_EVENTDATA3 = "getEventData:3";
    public static final String GET_EVENTDATABYTYPES4 = "getEventDataByTypes:4";
    public static final String GET_LASTPOINT1 = "getLastPoint:1";
    public static final String GET_LASTPOINT2 = "getLastPoint:2";
    public static final String GET_DEVICEROUTES3 = "getDeviceRoutes:3";
    public static final String GET_DEVICEROUTES4 = "getDeviceRoutes:4";
    public static final String GET_DEVOPSBYEDGES3 = "getDevopsByEdges:3";
    public static final String GET_DEVICEROUTES_CNT3 = "getDeviceRoutesCount:3";
    public static final String GET_TRACKDATA_CNT3 = "getTrackDataCount:3";
    public static final String GET_EVENTDATA_CNT3 = "getEventDataCount:3";
    public static final String GET_EVENTDATABYTYPES_CNT3 = "getEventDataByTypesCount:4";
    public static final String GET_DEVOPSBYEDGES_CNT3 = "getDevopsByEdgesCount:3";
    public static final String GET_LASTPOINT_CNT1 = "getLastPointCount:1";

    public static final Map<String, CqlApiMethod> CQL_METHODS = new HashMap<String, CqlApiMethod>(){{
        put(GET_TRACKDATA3, new CqlApiMethod("getTrackData", Collection.class, DEVOPS, FROM, TO));
        put(GET_TRACKDATA4, new CqlApiMethod("getTrackData", Collection.class, DEVICEID, OPERATORID, FROM, TO));
        put(GET_EVENTDATA3, new CqlApiMethod("getEventData", Collection.class, DEVOPS, FROM, TO));
        put(GET_EVENTDATABYTYPES4, new CqlApiMethod("getEventDataByTypes", Collection.class, DEVOPS, FROM, TO, EVTTYPES));
        put(GET_LASTPOINT1, new CqlApiMethod("getLastPoint", Collection.class, DEVOPS));
        put(GET_LASTPOINT2, new CqlApiMethod("getLastPoint", Collection.class, DEVICEID, OPERATORID));
        put(GET_DEVICEROUTES3, new CqlApiMethod("getDeviceRoutes", Collection.class, DEVOPS, FROM, TO));
        put(GET_DEVICEROUTES4, new CqlApiMethod("getDeviceRoutes", Collection.class, DEVICEID, OPERATORID, FROM, TO));
        put(GET_DEVOPSBYEDGES3, new CqlApiMethod("getDevopsByEdges", Collection.class, EDGES, FROM, TO));
        put(GET_DEVICEROUTES_CNT3, new CqlApiMethod("getDeviceRoutesCount", Collection.class, DEVOPS, FROM, TO));
        put(GET_TRACKDATA_CNT3, new CqlApiMethod("getTrackDataCount", Collection.class, DEVOPS, FROM, TO));
        put(GET_EVENTDATA_CNT3, new CqlApiMethod("getEventDataCount", Collection.class, DEVOPS, FROM, TO));
        put(GET_EVENTDATABYTYPES_CNT3, new CqlApiMethod("getEventDataByTypesCount", Collection.class, DEVOPS, FROM, TO, EVTTYPES));
        put(GET_DEVOPSBYEDGES_CNT3, new CqlApiMethod("getDevopsByEdgesCount", Collection.class, EDGES, FROM, TO));
        put(GET_LASTPOINT_CNT1, new CqlApiMethod("getLastPointCount", Collection.class, DEVOPS));
    }};

    public static List<CqlApiMethod> getCqlApiMethods() {
        return CQL_METHODS.values().stream().collect(Collectors.toList());
    }
}