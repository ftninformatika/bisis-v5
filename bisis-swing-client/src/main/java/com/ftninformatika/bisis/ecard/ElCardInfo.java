package com.ftninformatika.bisis.ecard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.devbase.jfreesteel.EidInfo;

@Getter
@Setter
@NoArgsConstructor
public class ElCardInfo {

    private String message;
    private boolean success = false;
    private EidInfo info = null;
}
