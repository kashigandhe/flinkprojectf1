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

        DataStream<Tuple4<String, Integer, Integer, Double>>  posnStr = textPositionsLine.map(new PositionSplitter());

        DataStream<Tuple3<String, Integer, Integer>> priceStr = textPriceLine.map(new PriceSplitter());

        posnStr.print();
        priceStr.print();

        env.execute("Position Price handling. ");

    }

    public static class PositionSplitter implements MapFunction<String, Tuple4<String,Integer, Integer, Double>>
    {
        public Tuple4<String, Integer, Integer, Double> map(String value)
        {
            String[] words = value.split(",");
            return new Tuple4<String,Integer, Integer, Double>(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2]),
                    Double.parseDouble(words[3]));
        }
    }

    public static class PriceSplitter implements MapFunction<String, Tuple3<String, Integer, Integer>>
    {
        public Tuple3<String, Integer, Integer> map(String value)
        {
            String[] words = value.split(",");
            return new Tuple3<String, Integer, Integer>(words[0], Integer.parseInt(words[1]),
                    Integer.parseInt(words[2]));
        }
    }

}
