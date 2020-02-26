package com.globant.brainwaves.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


public class BufferRawPacket implements Packet {

    private int[] bufferRawEeg;

    public BufferRawPacket(int[] bufferRawEeg) {
        this.bufferRawEeg = bufferRawEeg;
    }

    public int[] getBufferRawEeg() {
        return bufferRawEeg;
    }


}
