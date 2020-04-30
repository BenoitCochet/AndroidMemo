package fr.benoitcochet.memo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class MemoDAO
{
    @Query("SELECT * FROM memo")
    public abstract List<MemoDTO> getListeMemos();

    @Insert
    public abstract void insert(MemoDTO... memo);

    @Delete
    public abstract void delete(MemoDTO... memo);
}
