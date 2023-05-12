package kaimon.myapp.lostandfoundv2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "item_table")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    String name;

    String itemName;

    String location;

    String date;


    public Item(int id, String name, String itemName, String location, String date) {
        this.id = id;
        this.name = name;
        this.itemName = itemName;
        this.location = location;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return itemName;
    }

    public String getLocation() {
        return location;
    }
    public String getDate() {
        return date;
    }


}
