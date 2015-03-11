namespace java com.easemob.tsdb.thrift.models

/*
    TSData stands for Time Series Data

In OpenTSDB, a time series data point consists of:

A metric name.
A UNIX timestamp (seconds or millisecinds since Epoch).
A value (64 bit integer or single-precision floating point value).
A set of tags (key-value pairs) that describe the time series the point belongs to.

*/
struct TSData {
    /*
    some rules about the metrics name
    1. Strings are case sensitive, i.e. "Sys.Cpu.User" will be stored separately from "sys.cpu.user"
    2. Spaces are not allowed
    3. Only the following characters are allowed: 
    a to z, A to Z, 0 to 9, -, _, ., / or Unicode letters (as per the specification)*/
    1: required string name,
    2: required double value,
//    3: optional double v2,
    //should be timestamp in seconds, not millisecond
    3: required i64 timestamp,
    //must have at least one tags
    4: required map<string, string> tags
}


service	ThriftTsdbRpcService{
	oneway void putTSData(1:TSData tsdata),
	//use OpenTSDB's metric syntax
	oneway void putString(1:string metrics)
	
}