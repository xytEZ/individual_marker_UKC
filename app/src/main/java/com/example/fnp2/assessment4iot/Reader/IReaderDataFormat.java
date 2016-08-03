package com.example.fnp2.assessment4iot.Reader;

import com.example.fnp2.assessment4iot.Exception.ReaderException;
import com.example.fnp2.assessment4iot.Helper.IUnaryFunction;

/**
 * Created by xytez on 3/16/16.
 */
public interface IReaderDataFormat<T, U, V, W>
{
    T read(U src, IUnaryFunction<V, W> fn) throws ReaderException;
    String getDataFormat();
}
