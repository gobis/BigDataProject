package com.saitech.ganges;

import java.io.Serializable;
import java.util.List;
import java.util.function.ToDoubleFunction;

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
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.catalyst.expressions.ToDate;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

import com.saitech.spark.filterfuncClass;

@Path("/products")
public class ProductSQL implements Serializable{
	
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
	public Response showSchema() {
		init();

		loadTable();
		
		return Response.status(200).entity("Check your console").build();

		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public Response getAllProduct() {

		init();

		loadTable();
		
		DataFrame results = sqlContext.sql("SELECT retailer_product_master.ITEM_DESCRIPTION FROM retailer_product_master");
		JavaRDD<Row> resultRDD = results.toJavaRDD();

		filterfuncClass obj = new filterfuncClass();

		JavaRDD<String> finalRDD = resultRDD.map(obj.mapFunc);

		List<String> outputList = finalRDD.collect();

		GenericEntity<List<String>> result = new GenericEntity<List<String>>(outputList) {
		};

	//	results.show();
		
		return Response.status(200).entity(result).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/topbills/{param1}")
	public Response getTopNBills(@PathParam("param1") final String filterStr) {
		
		int topBills = Integer.parseInt(filterStr);

		init();

		loadTable();
		
		DataFrame results = sqlContext.sql("SELECT BILL_NUM,BILL_TOTAL FROM retailer_bill_header order by BILL_TOTAL desc limit "+topBills);
		JavaRDD<Row> resultRDD = results.toJavaRDD();

		filterfuncClass obj = new filterfuncClass();

		JavaRDD<String> finalRDD = resultRDD.map(obj.mapFunc);

		List<String> outputList = finalRDD.collect();

		GenericEntity<List<String>> result = new GenericEntity<List<String>>(outputList) {
		};

	//	results.show();
		
		return Response.status(200).entity(result).build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/topProduct/{param1}")
	public Response getTopNProductsSold(@PathParam("param1") final String filterStr) {
		
		int topProducts = Integer.parseInt(filterStr);

		init();

		loadTable();
		
		DataFrame results = sqlContext.sql("SELECT retailer_product_master.ITEM_DESCRIPTION,sum(retailer_bill_detail.UNIT_SOLD) as UnitSold "
				+ "FROM retailer_bill_detail join "
				+ "retailer_product_master on retailer_bill_detail.PRODUCT_UUID =  retailer_product_master.PRODUCT_UUID "
				+ "group by retailer_product_master.ITEM_DESCRIPTION  order by UnitSold desc limit "+topProducts);
		
		JavaRDD<Row> resultRDD = results.toJavaRDD();

		filterfuncClass obj = new filterfuncClass();

		JavaRDD<String> finalRDD = resultRDD.map(obj.mapFunc);

		List<String> outputList = finalRDD.collect();

		GenericEntity<List<String>> result = new GenericEntity<List<String>>(outputList) {
		};

	//	results.show();
		
		return Response.status(200).entity(result).build();

	}
	
	

     private void loadTable(){
    	// need to download separate jar file to support csv file
 		DataFrame rbd_df = sqlContext.read().format("com.databricks.spark.csv").option("header", "true")
 				// .load("names_ages.txt");
 				.load("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//ganges//RETAILER_BILL_DETAIL.csv");
 		
 		rbd_df = rbd_df
 				.withColumn("UNIT_PRICE", rbd_df.col("UNIT_PRICE").cast(DataTypes.DoubleType))
 				.withColumn("UNIT_SOLD", rbd_df.col("UNIT_SOLD").cast(DataTypes.DoubleType))
 				.withColumn("CREATED_TIME", rbd_df.col("CREATED_TIME").cast(DataTypes.DateType))
 				.withColumn("UPDATED_TIME",rbd_df.col("UPDATED_TIME").cast(DataTypes.DateType));
 		
 		rbd_df.printSchema();

 		rbd_df.registerTempTable("retailer_bill_detail");

 		// retailer bill header 
 		DataFrame rbh_df = sqlContext.read().format("com.databricks.spark.csv").option("header", "true")
 				// .load("names_ages.txt");
 				.load("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//ganges//RETAILER_BILL_HEADER.csv");
 		
 		
 		
 		rbh_df = rbh_df
 				.withColumn("BILL_DATE", rbh_df.col("BILL_DATE").cast(DataTypes.DateType))
 				.withColumn("BILL_DISCOUNT", rbh_df.col("BILL_DISCOUNT").cast(DataTypes.DoubleType))
 				.withColumn("BILL_NUM", rbh_df.col("BILL_NUM").cast(DataTypes.IntegerType))
 				.withColumn("BILL_TOTAL", rbh_df.col("BILL_TOTAL").cast(DataTypes.DoubleType))
 				.withColumn("CREATED_TIME", rbh_df.col("CREATED_TIME").cast(DataTypes.DateType))
 				.withColumn("UPDATED_TIME",rbh_df.col("UPDATED_TIME").cast(DataTypes.DateType))
 				.withColumn("BILL_STATUS",rbh_df.col("BILL_STATUS").cast(DataTypes.IntegerType));
 		
 		rbh_df.printSchema();

 		rbh_df.registerTempTable("retailer_bill_header");
 		
 		
 		DataFrame rpm_df = sqlContext.read().format("com.databricks.spark.csv").option("header", "true")
 				// .load("names_ages.txt");
 				.load("///Users//Gobi//Documents//workspace//BigDataExample//src//com//saitech//ganges//RETAILER_PRODUCT_MASTER.csv");

 		rpm_df = rpm_df
 				.withColumn("UNIT_MRP", rpm_df.col("UNIT_MRP").cast(DataTypes.DoubleType))
 				.withColumn("UNIT_SP", rpm_df.col("UNIT_SP").cast(DataTypes.DoubleType))
 				.withColumn("UNIT_WEIGHT", rpm_df.col("UNIT_WEIGHT").cast(DataTypes.DoubleType))
 				.withColumn("VAT_PERCENTAGE", rpm_df.col("VAT_PERCENTAGE").cast(DataTypes.DoubleType))
 				.withColumn("UNIT_CP", rpm_df.col("UNIT_CP").cast(DataTypes.DoubleType))
 				.withColumn("ACTIVE_STATUS", rpm_df.col("ACTIVE_STATUS").cast(DataTypes.IntegerType))
 				.withColumn("CREATED_TIME", rpm_df.col("CREATED_TIME").cast(DataTypes.DateType))              
 				.withColumn("UPDATED_TIME",rpm_df.col("UPDATED_TIME").cast(DataTypes.DateType));
 		
 		rpm_df.printSchema();

 		rpm_df.registerTempTable("retailer_product_master");
 		
    	 
     }


}
