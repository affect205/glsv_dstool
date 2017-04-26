package ru.glosav.dstool.gui.utils;

import javafx.scene.control.TableColumn;
import ru.glosav.dstool.entity.interfaces.IDTORow;
import ru.glosav.dstool.entity.rows.dto.CountDTO;
import ru.glosav.dstool.entity.rows.dto.EventDTO;
import ru.glosav.dstool.entity.rows.dto.MessageDTO;
import ru.glosav.dstool.entity.rows.meta.CountDTOMeta;
import ru.glosav.dstool.entity.rows.meta.DTOMeta;
import ru.glosav.dstool.entity.rows.meta.EventDTOMeta;
import ru.glosav.dstool.entity.rows.meta.MessageDTOMeta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by abalyshev on 26.04.17.
 */
public class TableUtils {
    public static final DTOMeta EVENT_DTO_META = new EventDTOMeta();
    public static final DTOMeta MESSAGE_DTO_META = new MessageDTOMeta();
    public static final DTOMeta COUNT_DTO_META = new CountDTOMeta();
    public static final Map<Class<? extends IDTORow>, DTOMeta> DTO_ROW_META = new HashMap<Class<? extends IDTORow>, DTOMeta>(){{
        put(MessageDTO.class, MESSAGE_DTO_META);
        put(EventDTO.class, EVENT_DTO_META);
        put(CountDTO.class, COUNT_DTO_META);
    }};

    public static <K> List<TableColumn<K, ?>> makeColumns(Class<? extends IDTORow> clazz) {
        DTOMeta dtoMeta = DTO_ROW_META.get(clazz);
        if (dtoMeta != null) {
            return dtoMeta.columns();
        }
        return new LinkedList<>();
    }

}
