package edu.uci.ics.textdb.exp.source.asterix;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.uci.ics.textdb.api.tuple.Tuple;
import edu.uci.ics.textdb.exp.sink.tuple.TupleSink;

public class AsterixSourceTest {
    
    @Test
    public void test1() {
        AsterixSourcePredicate predicate = new AsterixSourcePredicate(
                "localhost", 19002, "twitter", "ds_tweet", "text", "zika", "2000-01-01", "2017-05-18", 2);
        AsterixSource asterixSource = predicate.newOperator();
        
        asterixSource.open();
        Tuple tuple;
        List<Tuple> results = new ArrayList<>();
        while ((tuple = asterixSource.getNextTuple()) != null) {
        	results.add(tuple);
        }
        asterixSource.close();
        
        System.out.println(results);
        
    }

}
