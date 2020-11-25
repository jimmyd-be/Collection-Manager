package be.jimmyd.cm.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cm_itemdata")
@Data
@IdClass(ItemDataEntityPK.class)
public class ItemDataEntity {
    private long itemId;
    private long fieldId;
    private long valueCount;
    private String fieldValue;

    @Id
    @Column(name = "itemId")
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Id
    @Column(name = "fieldId")
    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    @Id
    @Column(name = "valueCount")
    public long getValueCount() {
        return valueCount;
    }

    public void setValueCount(long valueCount) {
        this.valueCount = valueCount;
    }

    @Basic
    @Column(name = "fieldValue")
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDataEntity that = (ItemDataEntity) o;
        return itemId == that.itemId &&
                fieldId == that.fieldId &&
                valueCount == that.valueCount &&
                Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, fieldId, valueCount, fieldValue);
    }
}
