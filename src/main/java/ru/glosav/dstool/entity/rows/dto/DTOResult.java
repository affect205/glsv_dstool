package ru.glosav.dstool.entity.rows.dto;

import ru.glosav.dstool.entity.interfaces.IDTORow;

import java.util.List;

/**
 * Created by abalyshev on 26.04.17.
 */
public class DTOResult {
    private List<? extends IDTORow> result;
    private Class<? extends IDTORow> type;
    public DTOResult(List<? extends IDTORow> result, Class<? extends IDTORow> type) {
        this.result = result;
        this.type = type;
    }
    public Class<? extends IDTORow> getType() { return type; }
    public List<? extends IDTORow> getResult() { return result; }
    public static DTOResult makeEventResult(List<EventDTO> result) { return new DTOResult(result, EventDTO.class); }
    public static DTOResult makeMessageResult(List<MessageDTO> result) { return new DTOResult(result, MessageDTO.class); }
    public static DTOResult makeDeviceRoutesResult(List<DeviceRoutesDTO> result) { return new DTOResult(result, DeviceRoutesDTO.class); }
    public static DTOResult makeEdgeResult(List<EdgeDTO> result) { return new DTOResult(result, EdgeDTO.class); }
    public static DTOResult makeCountResult(List<CountDTO> result) { return new DTOResult(result, CountDTO.class); }
}
