package com.ftninformatika.bisis.records.serializers;


import com.ftninformatika.bisis.records.Record;

public interface RecordListener {
  public void handleRecord(Record rec);
}
