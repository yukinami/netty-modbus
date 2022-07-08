package com.lsh.msg;


import com.lsh.Modbus;
import com.lsh.base.ModbusUtils;
import com.lsh.code.FunctionCode;
import com.lsh.entity.exception.ModbusTransportException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ReadNumericRequest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 20:55
 * @Version
 */
public class ReadNumericRequest extends ModbusRequest{

    public ReadNumericRequest() {
        super(FunctionCode.READ_COILS);
    }

    public ReadNumericRequest(int startOffset, int numberOfRegisters) {
        super(FunctionCode.READ_COILS, startOffset, numberOfRegisters);
    }

    @Override
    public String toString() {
        return "ReadNumericRequest{" +
                "startOffset=" + startOffset +
                ", numberOfRegisters=" + numberOfRegisters +
                '}';
    }
}
