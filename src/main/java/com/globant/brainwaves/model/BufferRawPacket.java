package com.globant.brainwaves.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BufferRawPacket implements Packet, Serializable {

    public int[] bufferRawEeg;

}
