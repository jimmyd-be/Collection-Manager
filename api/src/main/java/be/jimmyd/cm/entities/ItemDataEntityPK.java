package be.jimmyd.cm.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ItemDataEntityPK implements Serializable {
    private long itemId;
    private long fieldId;
    private long valueCount;

    @Column(name = "itemId")
    @Id
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Column(name = "fieldId")
    @Id
    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    @Column(name = "valueCount")
    @Id
    public long getValueCount() {
        return valueCount;
    }

    public void setValueCount(long valueCount) {
        this.valueCount = valueCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDataEntityPK that = (ItemDataEntityPK) o;
        return itemId == that.itemId &&
                fieldId == that.fieldId &&
                valueCount == that.valueCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, fieldId, valueCount);
    }
}
