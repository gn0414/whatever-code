package cn.simon.entity;

import java.util.List;
import java.util.Map;

public class CollectionParam {
    private List<String> lists;

    private Map<String,String> maps;

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public List<String> getLists() {
        return lists;
    }


}
