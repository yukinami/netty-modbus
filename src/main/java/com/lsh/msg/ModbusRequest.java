package com.lsh.msg;

import com.lsh.Modbus;
import com.lsh.base.ModbusUtils;
import com.lsh.entity.exception.ModbusTransportException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ModbusRequest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 22:00
 * @Version
 */
public abstract class ModbusRequest extends ModbusMessage {

    protected int startOffset;
    protected int numberOfRegisters;

    protected ModbusRequest(short functionCode) {
        super(functionCode);
    }

    protected ModbusRequest(short functionCode, int startOffset, int numberOfRegisters) {
        super(functionCode);
        this.startOffset = startOffset;
        this.numberOfRegisters = numberOfRegisters;
    }

    @Override
    public void validate(Modbus modbus) throws ModbusTransportException {
        ModbusUtils.validateOffset(startOffset);
        modbus.validateNumberOfRegisters(numberOfRegisters);
        ModbusUtils.validateEndOffset(startOffset + numberOfRegisters - 1);
    }

    /**
     * ++++++++++++++++++++++++++++++
     *  |功能码         |  1个字节    |
     *  |起始寄存器地址 |  2个字节    |
     *  |寄存器个数    |  2个字节    |
     * ++++++++++++++++++++++++++++++
     * @return
     */
    @Override
    public int calculateLength() {
        return 1 + 2 + 2;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(startOffset);
        buf.writeShort(numberOfRegisters);
        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        startOffset = data.readUnsignedShort();
        numberOfRegisters = data.readUnsignedShort();
    }



}
