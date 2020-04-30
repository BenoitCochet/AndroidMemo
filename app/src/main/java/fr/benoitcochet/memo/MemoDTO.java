package fr.benoitcochet.memo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel
@Entity(tableName = "memo")
public class MemoDTO
{
    @PrimaryKey(autoGenerate = true)
    public long memoId = 0;
    public String contenu;

    public MemoDTO() {}

    public MemoDTO(String contenu)
    {
        this.contenu = contenu;
    }
}
