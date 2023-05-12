package kaimon.myapp.lostandfoundv2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);


    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * FROM item_table ORDER BY name ASC")
    LiveData<List<Item> > getAddedItems();
}

