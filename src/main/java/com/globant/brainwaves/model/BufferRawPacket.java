package com.globant.brainwaves.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BufferRawPacket implements Packet {

    public int[] bufferRawEeg;

}
