package kaimon.myapp.lostandfoundv2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    ItemRepository itemRepository;
    LiveData<List<Item>> items;


    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        items = itemRepository.getAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return items;
    }

    public void insert(Item item){
        itemRepository.insert(item);
    }

    public void delete(Item item){
        itemRepository.delete(item);
    }


}
