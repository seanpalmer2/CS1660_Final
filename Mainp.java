package com.mycompany.app;
//This is my main class for the docker gui
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.*;
import javax.swing.JFrame;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.gax.paging.Page;
import com.google.api.services.dataproc.*;
import com.google.api.services.dataproc.Dataproc;
import com.google.api.services.dataproc.DataprocScopes;
import com.google.api.services.dataproc.model.HadoopJob;
import com.google.api.services.dataproc.model.Job;
import com.google.api.services.dataproc.model.JobPlacement;
import com.google.api.services.dataproc.model.SubmitJobRequest;
import com.google.cloud.storage.Storage.*;
import com.google.api.gax.paging.Page;
import com.google.api.services.storage.model.StorageObject;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.FileInputStream;
import com.google.cloud.storage.*;
// import org.apache.hadoop.conf.Configuration;
// import org.apache.hadoop.fs.FileSystem;
// import org.apache.hadoop.fs.FileUtil;
// import org.apache.hadoop.fs.Path;

public class Mainp{
  static int flag = 0;
  static ArrayList<String> books = new ArrayList<String>();
  static ArrayList<String> auth = new ArrayList<String>();
  static JFrame f = new JFrame();
  static JTextField tf = new JTextField();
  static ArrayList<String> cBooks = new ArrayList<String>();
  public static void main(String args[]){
    System.out.println("hello world");
    //first I want to build the book list

    buildBooks(books, auth);
    //System.out.println(books.get(20));
    //JFrame f = new JFrame();

    JButton b = new JButton("Choose File");
    b.setBounds(175,350,200, 40);
    f.add(b);

    JLabel title = new JLabel("Sean's Engine");
    title.setBounds(225,75,210, 40);
    f.add(title);

    JLabel ins = new JLabel("Please type the book you want and click choose file after each one");
    ins.setBounds(50,100,500, 40);
    f.add(ins);

    //JTextField tf = new JTextField();
    tf.setBounds(125,250,300, 40);
    f.add(tf);

    JLabel msgG = new JLabel("File Added");
    msgG.setBounds(460,250,210, 40);
    //f.add(msgG);

    JLabel msgB = new JLabel("File Not Found");
    msgB.setBounds(460,250,210, 40);
    //f.add(msgB);


    JButton b2 = new JButton("Construct Inverted Indicies");
    b2.setBounds(125,450,300, 40);
    f.add(b2);

    b.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
     //say file was added
     System.out.println("Got here!!!");
     //found();
     for (String b : auth){
       if(b.equals(tf.getText())){
         System.out.println("Author added");
         cBooks.add(b);
       }
     }
   }
   });


   b2.addActionListener(new ActionListener(){
   public void actionPerformed(ActionEvent e){
    invertI();
   }
   });


    f.setSize(600,600);
    f.setLayout(null);
    f.setVisible(true);
  }

  public static void invertI()
  {
    //pop up to say something

    //here is where I need to connect to the Cloud
    try
    {
  
            Dataproc dataproc = new Dataproc.Builder(new NetHttpTransport(),new JacksonFactory(), new HttpCredentialsAdapter(GoogleCredentials.getApplicationDefault().createScoped(DataprocScopes.all()))).setApplicationName("Cloud Computing Project").build();
            HadoopJob hJob = new HadoopJob();
            hJob.setMainJarFileUri("gs://dataproc-staging-us-west1-844689295070-qwvokjox/JAR/invertedmain3.jar");
            hJob.setArgs(ImmutableList.of("InvertedMain","gs://dataproc-staging-us-west1-844689295070-qwvokjox/Test","gs://dataproc-staging-us-west1-844689295070-qwvokjox/Test/output3"));
            dataproc.projects().regions().jobs().submit("job28" , "us-west1", new SubmitJobRequest()
                         .setJob(new Job()
                            .setPlacement(new JobPlacement()
                                .setClusterName("cluster-b7a7"))
                         .setHadoopJob(hJob)))
                        .execute();

        }
        catch (Exception e)
         {
            System.out.println("You messed up");
            e.printStackTrace();
        }

  }

  // public static void found()
  // {
  //   JLabel msg = new JLabel("File Added");
  //   msg.setBounds(460,250,210, 40);
  //   f.add(msg);
  //   f.setVisible(true);
  //   System.out.println("and here!!!");
  // }

  public static void buildBooks(ArrayList<String> a,ArrayList<String> b)
  {
    b.add("hugo");b.add("shakespeare");b.add("tolstoy");
    a.add("war_and_peace");
    a.add("anna_karenhina");
    a.add("Miserables");//this and the next one might need a .txt extention
    a.add("NotreDame_De_Paris");
    //start of shakespeare comedies
    a.add("allswellthatendswell");a.add("asyoulikeit");a.add("comedyoferrors");a.add("cymbeline");a.add("loveslabourslost");
    a.add("measureforemeasure");a.add("merchantofvenice");a.add("merrywivesofwindsor");a.add("midsummersnightsdream");a.add("muchadoaboutnothing");
    a.add("periclesprinceoftyre");a.add("tamingoftheshrew");a.add("tempest");a.add("troilusandcressida");a.add("twelfthnight");
    a.add("twogentlemenofverona");a.add("winterstale");
    //tragedies
    a.add("antonyandcleopatra");a.add("coriolanus");a.add("hamlet");a.add("juliuscaesar");a.add("kinglear");
    a.add("macbeth");a.add("othello");a.add("romeoandjuliet");a.add("timonofathens");a.add("titusandronicus");
    //histories
    a.add("1kinghenryiv");a.add("1kinghenryvi");a.add("2kinghenryiv");a.add("2kinghenryvi");a.add("3kinghenryvi");
    a.add("kinghenryv");a.add("kinghenryviii");a.add("kingjohn");a.add("kingrichardii");a.add("kingrichardiii");
    //poetry
    a.add("loverscomplaint");a.add("rapeoflucrece");a.add("sonnets");a.add("various");a.add("venusandadonis");
  }

}
