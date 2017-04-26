package ru.glosav.dstool.entity.rows.dto;

import ru.glosav.dstool.entity.interfaces.IDTORow;

/**
 * Created by abalyshev on 26.04.17.
 */
public class CountDTO implements IDTORow {
    public long cnt;
    public CountDTO(long cnt) {
        this.cnt = cnt;
    }
}
