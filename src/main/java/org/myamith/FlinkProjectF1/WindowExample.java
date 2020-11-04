package org.myamith.FlinkProjectF1;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.tuple.Tuple9;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.apache.flink.core.fs.FileSystem.WriteMode.OVERWRITE;

public class WindowExample {
    public static void main(String[] args) throws Exception{
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // Checking input parameters
        final ParameterTool params = ParameterTool.fromArgs(args);

        // make parameters avaialble in the web interface
        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> textPositionsLine = env.socketTextStream("localhost", 9898); // positions

        DataStream<String> textPriceLine = env.socketTextStream("localhost", 9999); // price

        DataStream<Tuple4<Long, Integer, Integer, Double>>  posnStr = textPositionsLine.map(new WindowExample.PositionSplitter())
                                                                                        .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple4<Long, Integer, Integer, Double>>() {
                                                                                            @Override
                                                                                            public long extractAscendingTimestamp(Tuple4<Long, Integer, Integer, Double> element) {
                                                                                                return 0;
                                                                                            }
                                                                                        });

        DataStream<Tuple3<Long, Integer, Integer>> priceStr = textPriceLine.map(new WindowExample.PriceSplitter())
                                                                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple3<Long, Integer, Integer>>() {
                                                                    @Override
                                                                    public long extractAscendingTimestamp(Tuple3<Long, Integer, Integer> element) {
                                                                        return 0;
                                                                    }
                                                                });

        DataStream<Tuple9<Long, Long, String, String, Integer, Integer, Integer, Double, Double>> formulatedPositionStream =
        posnStr.join(priceStr)
                .where(t ->t.f2).equalTo(t ->t.f1)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .apply(new JoinFunction<Tuple4<Long, Integer, Integer, Double>
                       ,Tuple3<Long, Integer, Integer>
                        , Tuple9<Long, Long, String, String,Integer, Integer, Integer, Double, Double>>(){
                    public Tuple9<Long, Long, String, String, Integer, Integer, Integer, Double, Double> join(Tuple4<Long, Integer, Integer, Double> position,
                                                                                              Tuple3<Long, Integer, Integer> price){
                        return new Tuple9<Long, Long, String, String, Integer, Integer, Integer, Double, Double>
                                (position.f0, price.f0, convertTime(position.f0), convertTime(price.f0), position.f1, position.f2, price.f2, position.f3, Double.parseDouble(price.f2.toString()) * position.f3);
                    }
                });

       // posnStr.print();
       //  priceStr.print();

      //  formulatedPositionStream.writeAsText(params.get("output"));

//        final StreamingFileSink<Tuple7<Long, Long, Integer, Integer, Integer, Double, Double>> sink = StreamingFileSink
//                .forRowFormat(new Path("/Users/amithhegde/Documents/Flink/flinkProjV2/data/formulatedPositionStream"), new SimpleStringEncoder<Tuple7<Long, Long, Integer, Integer, Integer, Double, Double>>("UTF-8"))
//                .withRollingPolicy(
//                        DefaultRollingPolicy.builder()
//                                .withRolloverInterval(TimeUnit.MINUTES.toMillis(5))
//                                .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
//                                .withMaxPartSize(1024 * 1024 * 1024)
//                                .build())
//                .build();
//
//        formulatedPositionStream.addSink(sink);

      //   Sunday, November 1, 2020 6:38:04.537 PM, Sunday, November 1, 2020 6:38:08.546 PM
        // 1,3,20,24.0,480.0

        //formulatedPositionStream.print();

        formulatedPositionStream.writeAsText("/Users/amithhegde/Documents/Flink/flinkProjV2/data/formulatedPositionStream", OVERWRITE);

        env.execute("Tumbling Window handling Example. ");

    }

    public static class PositionSplitter implements MapFunction<String, Tuple4<Long,Integer, Integer, Double>>
    {
        public Tuple4<Long, Integer, Integer, Double> map(String value)
        {
            String[] words = value.split(",");
            return new Tuple4<Long,Integer, Integer, Double>(Long.parseLong(words[0]),Integer.parseInt(words[1]),Integer.parseInt(words[2]),
                    Double.parseDouble(words[3]));
        }
    }

    public static class PriceSplitter implements MapFunction<String, Tuple3<Long, Integer, Integer>>
    {
        public Tuple3<Long, Integer, Integer> map(String value)
        {
            String[] words = value.split(",");
            return new Tuple3<Long, Integer, Integer>(Long.parseLong(words[0]), Integer.parseInt(words[1]),
                    Integer.parseInt(words[2]));
        }
    }

    private static String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
