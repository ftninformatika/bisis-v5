package com.ftninformatika.bisis.opac.admin.dto;

import com.ftninformatika.bisis.opac.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements Serializable, Comparable<MessageDTO> {
    Message message;
    String senderFirstname;
    String senderLastname;

    public MessageDTO(Message message) {
        this.message = message;
    }

    @Override
    public int compareTo(MessageDTO messageDTO) {
        return this.message.getDate().compareTo(messageDTO.message.getDate());
    }
}
