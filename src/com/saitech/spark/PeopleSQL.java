package com.saitech.spark;

import java.io.Serializable;
import java.util.HashMap;
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
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import org.apache.spark.api.java.function.Function;


@Path("/peoplesql")
public class PeopleSQL implements Serializable{

	
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
	public Response getAllPeople(){
			
			init();
			
			
            // need to download separate jar file to support csv file 			
			DataFrame df = sqlContext.read().format("com.databricks.spark.csv")
			.option("header", "true")
			.load("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//spark//names_ages.txt");
			
			long output =  df.count();
			df.printSchema();
			
			df.registerTempTable("people");
			DataFrame results = sqlContext.sql("SELECT * FROM people");
			JavaRDD<Row> resultRDD =  results.toJavaRDD();
			
			
			filterfuncClass obj = new filterfuncClass();
			
			JavaRDD<String> finalRDD = resultRDD.map(obj.mapFunc);
			
			List<String> outputList =  finalRDD.collect();
			
			GenericEntity<List<String>> result = 
		            new GenericEntity<List<String>>(outputList) {};
			
			
			results.show();
			DataFrame nameDF  = df.select("name");
		//	 GenericEntity<List<DataFrame>> result = 
		 //          new GenericEntity<List<DataFrame>>((List<DataFrame>) nameDF) {};
			
			// String result = String.valueOf(output);
			
			return Response.status(200).entity(result).build();
			
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{param1}")
	public Response getFilterPeople(@PathParam("param1") final String filterStr){
			
			init();
			
			
            // need to download separate jar file to support csv file 			
			DataFrame df = sqlContext.read().format("com.databricks.spark.csv")
			.option("header", "true")
			.load("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//spark//names_ages.txt");
			
			
			df.registerTempTable("people");
			DataFrame results = sqlContext.sql("SELECT * FROM people");
			JavaRDD<Row> resultRDD =  results.toJavaRDD();
			
			
			filterfuncClass obj = new filterfuncClass();
			obj.setFilterData(filterStr);
			
			JavaRDD<String> finalRDD1 = resultRDD.map(obj.mapFunc);
			JavaRDD<String> finalRDD = finalRDD1.filter(obj.Filterfunc);
			
			List<String> outputList =  finalRDD.collect();
			
			GenericEntity<List<String>> result = 
		            new GenericEntity<List<String>>(outputList) {};
			
			
			results.show();
			DataFrame nameDF  = df.select("name");
		//	 GenericEntity<List<DataFrame>> result = 
		 //          new GenericEntity<List<DataFrame>>((List<DataFrame>) nameDF) {};
			
			// String result = String.valueOf(output);
			
			return Response.status(200).entity(result).build();
			
	}

	
	
}
