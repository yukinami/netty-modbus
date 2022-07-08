package com.lsh.msg;

import com.lsh.Modbus;
import com.lsh.code.FunctionCode;
import com.lsh.entity.exception.ModbusTransportException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 线圈寄存器响应.
 */
public class ReadNumericResponse extends ModbusResponse {

    private short byteCount;
    /**
     * 寄存器数据
     */
    private int[] registers;

    public ReadNumericResponse() {
        super(FunctionCode.READ_COILS);
    }

    public ReadNumericResponse(int[] registers) {
        super(FunctionCode.READ_COILS);
        if (registers.length > 125) {
            throw new IllegalArgumentException();
        }
        this.byteCount = (short)registers.length;
        this.registers = registers;
    }

    public short getByteCount() {
        return byteCount;
    }

    public int[] getRegisters() {
        return registers;
    }

    @Override
    public void validate(Modbus modbus) throws ModbusTransportException {

    }

    /**
     *
     * =======================
     * |功能码     | 1个字节  |
     * |数据长度   | 1个字节  |
     * |寄存器数据 | n个字节  |
     * =======================
     * @return
     */
    @Override
    public int calculateLength() {
        return 1 + 1 + byteCount;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(byteCount);
        for (int i = 0; i < registers.length; i++) {
            buf.writeByte(registers[i]);
        }
        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        byteCount = data.readUnsignedByte();
        registers = new int[byteCount];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = data.readUnsignedByte();
        }
    }

    @Override
    public String toString() {
        StringBuilder registersStr = new StringBuilder();
        registersStr.append("{");
        for (int i = 0; i < registers.length; i++) {
            registersStr.append("register_")
                    .append(i)
                    .append("=")
                    .append(registers[i])
                    .append(", ");
        }
        registersStr.delete(registersStr.length() - 2, registersStr.length());
        registersStr.append("}");
        return "ReadNumericResponse{" + "byteCount=" + byteCount + ", inputRegisters=" + registersStr + '}';
    }
}