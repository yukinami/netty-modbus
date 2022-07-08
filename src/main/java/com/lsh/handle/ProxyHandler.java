package com.lsh.handle;

import com.lsh.ModbusFactory;
import com.lsh.ModbusMaster;
import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusHeader;
import com.lsh.entity.exception.ModbusInitException;
import com.lsh.ip.IpParameters;
import com.lsh.ip.tcp.TcpMaster;
import com.lsh.msg.ModbusMessage;
import com.lsh.msg.ModbusRequest;
import com.lsh.msg.ModbusResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProxyHandler extends ChannelInboundHandlerAdapter {

    private TcpMaster tcpMaster;

    public ProxyHandler(String targetHost, Integer targetPort) throws ModbusInitException {
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost(targetHost);
        ipParameters.setPort(targetPort);
        tcpMaster = (TcpMaster) new ModbusFactory().createTcpMaster(ipParameters, (short)1, true);
        tcpMaster.init();
    }
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ModbusFrame) {
            ModbusFrame requestFrame = (ModbusFrame) msg;
            ModbusRequest request = (ModbusRequest) requestFrame.getMessage();
            ModbusFrame responseFrame = tcpMaster.callModbusMessageSync(request);
            ModbusHeader header = responseFrame.getHeader();
            ctx.writeAndFlush(new ModbusFrame(
                new ModbusHeader(requestFrame.getHeader().getTransactionIdentifier(),
                    header.getProtocolIdentifier(),
                    header.getLength(), 
                    header.getUnitIdentifier()), 
                responseFrame.getMessage()));
        }
    }
}
