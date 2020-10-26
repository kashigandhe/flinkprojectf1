package org.myamith.FlinkProjectF1;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.lang.reflect.Parameter;

public class PositionPriceStream {

    public static void main(String[] args) throws Exception{
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Checking input parameters
        final ParameterTool params = ParameterTool.fromArgs(args);

        // make parameters avaialble in the web interface
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> textPositions = env.socketTextStream("localhost", 9898); // positions

        DataStream<String> textPrice = env.socketTextStream("localhost", 9999); // price

        textPositions.print();
        textPrice.print();

        env.execute("Position Price handling. ");

    }
}
