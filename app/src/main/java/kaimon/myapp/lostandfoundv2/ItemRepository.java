package kaimon.myapp.lostandfoundv2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {


    ItemDAO itemDAO;

    LiveData<List<Item>> itemList;

    public ItemRepository(Application application) {

        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        itemDAO = db.itemDAO();
        itemList = itemDAO.getAddedItems();
    }


    public LiveData<List<Item>> getAllItems() {
        return  itemList;
    }

    public void insert(Item item){
        ItemRoomDatabase.databaseWriterExecutor.execute(()->{
            itemDAO.insert(item);
        });

    }

    public void delete(Item item)
    {
        ItemRoomDatabase.databaseWriterExecutor.execute(()->{
            itemDAO.delete(item);
        });
    }
}