package org.cluscon.database;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Serg
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Lyosha Chikov rilian-la-te
 */
public class Storage implements java.io.Serializable, Iterable {

    private List<DataStruct<Integer, String, String>> internal_data;
    public long user_id;

    public Storage() {
        internal_data = new LinkedList<DataStruct<Integer, String, String>>();
    }

    public DataStruct<Integer, String, String> getPair(int id) {
        if (id < internal_data.size()) {
            return internal_data.get(id);
        }
        return null;
    }

    public void addPair(Integer node_id, String ip, String mac) {
        internal_data.add(new DataStruct<Integer, String, String>(node_id, ip, mac));
    }

    public void clean() {
        internal_data.clear();
    }

    @Override
    public Iterator iterator() {
        Iterator it = new Iterator() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < internal_data.size() && internal_data.get(currentIndex) != null;
            }

            @Override
            public DataStruct next() {
                return internal_data.get(currentIndex++);
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
            }
        };
        return it;
    }
}
