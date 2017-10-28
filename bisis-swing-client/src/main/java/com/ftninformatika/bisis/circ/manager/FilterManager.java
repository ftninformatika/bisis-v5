package com.ftninformatika.bisis.circ.manager;

import java.util.List;
import java.util.Vector;

import com.ftninformatika.bisis.BisisApp;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;



public class FilterManager {
  
//  private CachingWrapperFilter filter = null;
//
//
//  public FilterManager(List filterInvs){
//    filter = new CachingWrapperFilter(new BisisFilter(filterInvs));
//  }
//  public Result getRecordsId(){
//	  return BisisApp.getRecordManager().selectAll3(new MatchAllDocsQuery(), filter, "TI_sort");
//  }
//
//  public List bestBookUDK(List l,String udk){
//
//	    Result res;
//	    Query q=new WildcardQuery(new Term("DC",udk+"*"));
//	    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q), SerializationUtils.serialize(filter), "TI_sort");
//	    List resultList = ListUtils.intersection(res.getInvs(),l);
//	    return resultList;
//  }
//  public int [] selectUDKcountGroup(List izgrupepom){
//    int udkcount[]=new int [10];
//    Result res;
//    Query q=new WildcardQuery(new Term("DC","0*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    List resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[0]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","1*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[1]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","2*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[2]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","3*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[3]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","4*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[4]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","5*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[5]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","6*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[6]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","7*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[7]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","8*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[8]=resultList.size();
//
//    q=new WildcardQuery(new Term("DC","9*"));
//    res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//    resultList = ListUtils.intersection(izgrupepom,res.getInvs());
//    udkcount[9]=resultList.size();
//
//    return udkcount;
//   }
//  public Vector[] lendreturnUDK(List svi){
//	  Vector[]udkcount=new Vector[10];
//	  Result res;
//	  CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(svi));
//	  Query q=new WildcardQuery(new Term("DC","0*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  List resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[0]=new Vector();
//	  udkcount[0].add(resultList.size());
//	  udkcount[0].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","1*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[1]=new Vector();
//	  udkcount[1].add(resultList.size());
//	  udkcount[1].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","2*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[2]=new Vector();
//	  udkcount[2].add(resultList.size());
//	  udkcount[2].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","3*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[3]=new Vector();
//	  udkcount[3].add(resultList.size());
//	  udkcount[3].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","4*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[4]=new Vector();
//	  udkcount[4].add(resultList.size());
//	  udkcount[4].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","5*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[5]=new Vector();
//	  udkcount[5].add(resultList.size());
//	  udkcount[5].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","6*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[6]=new Vector();
//	  udkcount[6].add(resultList.size());
//	  udkcount[6].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","7*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[7]=new Vector();
//	  udkcount[7].add(resultList.size());
//	  udkcount[7].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","8*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[8]=new Vector();
//	  udkcount[8].add(resultList.size());
//	  udkcount[8].add(res.getRecords().length);
//
//	  q=new WildcardQuery(new Term("DC","9*"));
//	  res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//	  resultList = ListUtils.intersection(res.getInvs(),svi);
//	  udkcount[9]=new Vector();
//	  udkcount[9].add(resultList.size());
//	  udkcount[9].add(res.getRecords().length);
//
//	  return udkcount;
//	 }
//
  

}
