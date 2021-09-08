package com.ftninformatika.bisis.opac.dto;

import com.ftninformatika.bisis.opac.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@NoArgsConstructor
public class MessageSenderDTO implements Comparable<MessageSenderDTO>{
    private MemberCardDTO memberCardDTO;
    private Message message;

    public MessageSenderDTO(MemberCardDTO memberCardDTO, Message message) {
        this.memberCardDTO = memberCardDTO;
        this.message = message;
    }

    @Override
    public int compareTo(MessageSenderDTO msgSender) {
        return msgSender.message.getDate().compareTo(this.message.getDate());
    }
}
