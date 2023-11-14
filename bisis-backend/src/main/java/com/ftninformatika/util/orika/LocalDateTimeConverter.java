//package com.ftninformatika.util.orika;
//
//import ma.glasnost.orika.converter.BidirectionalConverter;
//import ma.glasnost.orika.metadata.Type;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
///**
// * Converter needed to avoid known issue when mapping LocalDateTime objects with Orika.
// * @see <a href="http://stackoverflow.com/questions/30805753/how-to-map-java-time-localdate-field-with-orika">
// *     http://stackoverflow.com/questions/30805753/how-to-map-java-time-localdate-field-with-orika</a>
// */
//@Component
//public class LocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, LocalDateTime> {
//
//  @Override
//  public LocalDateTime convertTo(final LocalDateTime source, final Type<LocalDateTime> destinationType) {
//    return source;
//  }
//
//  @Override
//  public LocalDateTime convertFrom(final LocalDateTime source, final Type<LocalDateTime> destinationType) {
//    return source;
//  }
//
//}
