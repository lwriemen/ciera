package io.ciera.runtime.template.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;

public interface T {

    public void append(String s) throws XtumlException;

    default public void append(boolean b) throws XtumlException {
        append(new String(Boolean.toString(b)));
    }

    default public void append(int i) throws XtumlException {
        append(new String(Integer.toString(i)));
    }

    default public void append(double d) throws XtumlException {
        append(new String(Double.toString(d)));
    }

    default public void append(IXtumlType o) throws XtumlException {
        append(new String(o.toString()));
    }

    public String body() throws XtumlException;

    public void clear() throws XtumlException;

    public void emit(String file) throws XtumlException;

    public void include(String file, Object ... symbols) throws XtumlException;

    public void pop_buffer() throws XtumlException;

    public void push_buffer() throws XtumlException;

    public void set_output_directory(String dir) throws XtumlException;

    public String sub(String format, String s) throws XtumlException;

    default public String sub(String format, boolean b) throws XtumlException {
        return sub(format, Boolean.toString(b));
    }

    default public String sub(String format, int i) throws XtumlException {
        return sub(format, Integer.toString(i));
    }

    default public String sub(String format, double d) throws XtumlException {
        return sub(format, Double.toString(d));
    }

    default public String sub(String format, IXtumlType o) throws XtumlException {
        return sub(format, o.serialize());
    }

}
