package com.lls.comics.core.extension;

import java.util.Comparator;

/************************************
 * SequenceComparator
 * @author liliangshan
 * @date 2019-01-31
 ************************************/
public class SequenceComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        Activation activation1 = o1.getClass().getAnnotation(Activation.class);
        Activation activation2 = o2.getClass().getAnnotation(Activation.class);
        if (activation1 == null) {
            return 1;
        }
        if (activation2 == null) {
            return -1;
        }
        return activation1.sequence() - activation2.sequence();
    }

}
