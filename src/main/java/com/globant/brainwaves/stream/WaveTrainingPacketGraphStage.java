package com.globant.brainwaves.stream;

import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.stage.AbstractInHandler;
import akka.stream.stage.AbstractOutHandler;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import com.globant.brainwaves.commons.model.*;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;

@Log
public class WaveTrainingPacketGraphStage extends GraphStage<FlowShape<WavePacket, WaveTrainingPacket>> {

    public final Inlet<WavePacket> in = Inlet.create("WaveTrainingPacketGraphStage.in");
    public final Outlet<WaveTrainingPacket> out = Outlet.create("WaveTrainingPacketGraphStage.out");

    private WaveTrainingPacket waveTrainingPacket=WaveTrainingPacket.builder().buffer(Collections.synchronizedList(new ArrayList<WavePacket>())).build();

    private final FlowShape<WavePacket, WaveTrainingPacket> shape = FlowShape.of(in, out);

    @Override
    public FlowShape<WavePacket, WaveTrainingPacket> shape() {
        return shape;
    }

    private synchronized void reset(){
        waveTrainingPacket=WaveTrainingPacket.builder().buffer(Collections.synchronizedList(new ArrayList<WavePacket>())).build();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(shape) {

            {
                setHandler(in, new AbstractInHandler() {
                    @Override
                    public void onPush() {
                        WavePacket packet = grab(in);
                        if(packet.getPacket() instanceof BufferRawPacket){
                            log.log(Level.FINE,"Buffer Element:{0} ",packet.toString());
                            waveTrainingPacket.getBuffer().add(packet);
                        }else if( !(packet.getPacket() instanceof StatusPacket)){
                            if(waveTrainingPacket.getHeader()!=null && waveTrainingPacket.getBuffer().size()>=1){
                                push(out, waveTrainingPacket);
                                reset();
                            }
                            waveTrainingPacket.setHeader(packet);
                            log.log(Level.FINE,"Pushing Element: {0} - {1}", Arrays.asList(packet.getPacket().getClass(),waveTrainingPacket.getBuffer().size()));
                        }
                    }
                });
                setHandler(out, new AbstractOutHandler() {
                    @Override
                    public void onPull() {
                        pull(in);
                    }
                });
            }
        };
    }
}
