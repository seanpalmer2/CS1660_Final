import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.lang.ClassNotFoundException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class InvertedMain
{
// first I am going to put the mapper and reducer classes in Here
public static class InvertedMapper extends Mapper<LongWritable, Text, Text, Text>
{

  private final static IntWritable one = new IntWritable(1);
  private Text word = new Text();

  public void map(LongWritable key, Text value, Context context)
    throws IOException, InterruptedException
    {

      String line = value.toString();
		  StringTokenizer tokenizer = new StringTokenizer(line);
      String id = ((FileSplit) context.getInputSplit()).getPath().getName();



      while(tokenizer.hasMoreTokens())
      {
        word.set(tokenizer.nextToken().replaceAll("[^a-zA-Z]", "").toLowerCase());

        context.write(word,new Text(id));
      }
    }
}

//now the reducer class
public static class InvertedReducer extends Reducer<Text, Text, Text, Text>
{
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
    throws IOException, InterruptedException
  {
    int sum = 0;
    HashMap<String, Integer> map = new HashMap<String,Integer>();
    //Here I build the map
    for(IntWritable value : values)
    {
      if(!map.containsKey(value.toString()))
      {
        map.put(value.toString(), 1);//value was not found so now we add it to the map
      }
      else
      {
        map.put(value.toString(), map.get(value.toString() + 1));//value was found so add 1 to amount
      }
      //sum += value.get(); old code from mr
    }

    //next step is to sort by the ids that we are given
    StringBuilder idList = new StringBuilder();
    for(String id : map.keySet())
    {
      idList.append(id + ":" + map.get(id) + " ");//not postive about this line doing what I want it to
    }

    context.write(key, new Text(idList.toString()));//might be IntWritable
  }
}

  public static void main(String[] args)
    throws IOException, InterruptedException, ClassNotFoundException
  {
    if(args.length != 2)//
    {
      System.err.println("Use was incorrect"); // fix and add the format that it should be in
      System.exit(-1);
    }

    Job job = new Job();
    job.setJarByClass(InvertedMain.class);
    job.setJobName("Inverted Indexing");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(InvertedMapper.class);
    job.setReducerClass(InvertedReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    job.waitForCompletion(true);
  }
}
