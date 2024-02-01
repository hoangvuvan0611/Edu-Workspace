package com.vvh.attendancesv.util.factoryMethod;

import java.awt.*;

public interface ReaderImage<T, S> {
    public T read(S s);
}
