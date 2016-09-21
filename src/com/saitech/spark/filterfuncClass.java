package com.saitech.spark;

import java.io.Serializable;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;

public class filterfuncClass implements Serializable{

	private String mFilterStr;
	
	public void setFilterData(String dataIn){
		mFilterStr = dataIn;
	}
	

	public Function Filterfunc = new Function<String,Boolean>() {

		@Override
		public Boolean call(String s) throws Exception {
			if(s != null && s.contains(mFilterStr)) {
				return true;
				}
				return false;
		}
	};
	
	
	public Function mapFunc = new Function<Row,String>() {

		@Override
		public String call(Row rowdata) throws Exception {
			StringBuilder resultdata = new StringBuilder();
			for(int i= 0 ; i<rowdata.length(); i++){
				resultdata.append(rowdata.get(i).toString());
				
				if(i != rowdata.length()-1)
				   resultdata.append(",");
			}
			
			return resultdata.toString();
		}
	};
	
}
