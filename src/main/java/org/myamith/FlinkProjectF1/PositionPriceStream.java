package org.myamith.FlinkProjectF1;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.lang.reflect.Parameter;
import java.util.Date;

public class PositionPriceStream {

    public static void main(String[] args) throws Exception{
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Checking input parameters
        final ParameterTool params = ParameterTool.fromArgs(args);

        // make parameters avaialble in the web interface
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> textPositionsLine = env.socketTextStream("localhost", 9898); // positions

        DataStream<String> textPriceLine = env.socketTextStream("localhost", 9999); // price

        DataStream<Tuple4<Integer, Integer, Double, String>>  posnStr = textPositionsLine.map(new PositionSplitter());

        DataStream<Tuple3<Integer, Integer, String>> priceStr = textPriceLine.map(new PriceSplitter());

        posnStr.print();
        priceStr.print();

        env.execute("Position Price handling. ");

    }

    public static class PositionSplitter implements MapFunction<String, Tuple4<Integer, Integer, Double, String>>
    {
        public Tuple4<Integer, Integer, Double, String> map(String value)
        {
            String[] words = value.split(",");
            return new Tuple4<Integer, Integer, Double, String>(Integer.parseInt(words[0]),Integer.parseInt(words[1]),
                    Double.parseDouble(words[2]),(new Date()).toString());
        }
    }

    public static class PriceSplitter implements MapFunction<String, Tuple3<Integer, Integer, String>>
    {
        public Tuple3<Integer, Integer, String> map(String value)
        {
            String[] words = value.split(",");
            return new Tuple3<Integer, Integer, String>(Integer.parseInt(words[0]),
                    Integer.parseInt(words[1]),(new Date()).toString());
        }
    }

}
