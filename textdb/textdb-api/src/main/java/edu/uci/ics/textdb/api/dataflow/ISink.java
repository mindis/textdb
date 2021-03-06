package edu.uci.ics.textdb.api.dataflow;

import edu.uci.ics.textdb.api.exception.TextDBException;
import edu.uci.ics.textdb.api.tuple.Tuple;

/**
 * Created by chenli on 5/11/16.
 *
 * An ISink object is used as the root of a query plan tree to consume all the
 * tuples generated by its subtree.
 *
 */
public interface ISink extends IOperator {
    void open() throws TextDBException;

    void processTuples() throws TextDBException;

    void close() throws TextDBException;
    
    default Tuple getNextTuple() throws TextDBException {
        throw new TextDBException("temp", new UnsupportedOperationException());
    }
    
}
