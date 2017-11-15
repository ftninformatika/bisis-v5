package com.ftninformatika.bisis;

import com.ftninformatika.bisis.coders.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by Petar on 11/15/2017.
 */
@NoArgsConstructor
@Getter
@Setter
public class LibraryCoders {

    private Map<String, AccessionRegister> accRegCoders;
    private Map<String, Acquisition> acqCoders;
    private Map<String, Availability> availCoders;
    private Map<String, Binding> binCoders;
    private Map<String, Format> formCoders;
    private Map<String, InternalMark> intmCoders;
    private Map<String, ItemStatus> stCoders;
    private Map<String, Sublocation> sublocCoders;
    private Map<String, Location> locCoders;



}
