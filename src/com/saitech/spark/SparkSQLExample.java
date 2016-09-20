package com.saitech.spark;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.rdd.RDD;
//import org.apache.spark.sql.SparkSession;


// Spark sql related libs 
import org.apache.spark.sql.SQLContext;




@Path("/sparksql")
public class SparkSQLExample implements Serializable{
	
	SparkContext sc;
	SQLContext sqlContext;

	public void init() {
		SparkConf conf = new SparkConf();
		conf.setAppName("testAPP");
		conf.setMaster("local");
		conf.set("spark.driver.allowMultipleContexts", "true");
		
		if (null == sc)
			sc = new SparkContext(conf);
		
		if (null == sqlContext)
			sqlContext = new SQLContext(sc);
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		
		init();
		
		JavaRDD<String> textFileRDD = sc.textFile("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//spark//logs.txt", 2).toJavaRDD();
		
		
				
		String result = "Hi, hava a nice day from spark sql"; // customerBo.getMsg();
		List<String> output =  textFileRDD.collect();
		
		GenericEntity<List<String>> entity = 
	            new GenericEntity<List<String>>(output) {};
		
		return Response.status(200).entity(entity).build();
	}

	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{param1}")
	public Response getFilteredClass(@PathParam("param1") final String filterStr) {
		
		init();
		
		JavaRDD<String> textFileRDD = sc.textFile("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//spark//logs.txt", 2).toJavaRDD();
		
		filterfuncClass obj = new filterfuncClass();
		obj.setFilterData(filterStr);
		
		JavaRDD<String> dectorRDD = textFileRDD.filter(obj.Filterfunc);
		
		/*JavaRDD<String> words = textFile.flatMap(new FlatMapFunction<String, String>() {
			  public Iterable<String> call(String s) { return Arrays.asList(s.split(" ")); }
			});
			JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
			  public Tuple2<String, Integer> call(String s) { return new Tuple2<String, Integer>(s, 1); }
			});
			JavaPairRDD<String, Integer> counts = (JavaPairRDD<String, Integer>) pairs.reduceByKeyLocally(new Function2<Integer, Integer, Integer>() {
			  public Integer call(Integer a, Integer b) { return a + b; }
			});
		*/
		
		/*JavaRDD<String> words = rdd.flatMap(new FlatMapFunction<String, String>() {
		      @Override
		      public Iterator<String> call(String s) {
		        return Arrays.asList(SPACE.split(s)).iterator();
		      }
		    });*/
		
		
		String result = "Hi, hava a nice day from spark sql"; // customerBo.getMsg();
		 List<String> output =  dectorRDD.collect();
		
		GenericEntity<List<String>> entity = 
	            new GenericEntity<List<String>>(output) {};
		
		return Response.status(200).entity(entity).build();
	}
	

	
	
	public void LoadData(){
	
//		JavaRDD<String> people = sc.textFile("examples/src/main/resources/people.txt", 1).toJavaRDD();
	//	String schemaString = "BILL_HEADER_UUID String , STORE_UUID String , BILL_DATE TIMESTAMP , BILL_DISCOUNT DOUBLE , BILL_NUM String , BILL_TOTAL DOUBLE , CREATED_BY String, CREATED_TIME TIMESTAMP , REF_COLUMN_1 String, REF_COLUMN_2 UPDATE_BY UPDATED_TIME BILL_STATUS";

		
	}
	
	
	

}
