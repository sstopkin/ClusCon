/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cluscon.database;

import java.io.Serializable;

/**
 *
 * @author Serg
 */
public class DataStruct<FIRST, SECOND, THIRD> implements Comparable<DataStruct<FIRST, SECOND, THIRD>>,Serializable {

    public final FIRST first;
    public final SECOND second;
    public final THIRD third;

    public DataStruct(FIRST first, SECOND second, THIRD third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <FIRST, SECOND, THIRD> DataStruct<FIRST, SECOND, THIRD> of(FIRST first,
            SECOND second, THIRD third) {
        return new DataStruct<FIRST, SECOND, THIRD>(first, second, third);
    }

    @Override
    public int compareTo(DataStruct<FIRST, SECOND, THIRD> o) {
        int cmp = compare(first, o.first);
        return cmp == 0 ? compare(second, o.second) : cmp;
    }

    // todo move this to a helper class.
    private static int compare(Object o1, Object o2) {
        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1
                : ((Comparable) o1).compareTo(o2);
    }

    @Override
    public int hashCode() {
        return 31 * hashcode(first) + hashcode(second)+ hashcode(third);
    }

    // todo move this to a helper class.
    private static int hashcode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DataStruct))
            return false;
        if (this == obj)
            return true;
        return equal(first, ((DataStruct) obj).first)
                && equal(second, ((DataStruct) obj).second)
                && equal(third, ((DataStruct) obj).third);
    }

    // todo move this to a helper class.
    //rewrite
    private boolean equal(Object o1, Object o2) {
        return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ')';
    }
}