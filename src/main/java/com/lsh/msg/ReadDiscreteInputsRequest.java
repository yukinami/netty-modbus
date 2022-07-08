package com.lsh.msg;

import com.lsh.code.FunctionCode;

/**
 * @ClassName ReadDiscreteInputsRequest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/23 15:50
 * @Version
 */
public class ReadDiscreteInputsRequest extends ModbusRequest {


    public ReadDiscreteInputsRequest(short functionCode) {
        super(FunctionCode.READ_DISCRETE_INPUTS);
    }

    public ReadDiscreteInputsRequest(short functionCode, int startOffset, int numberOfRegisters) {
        super(FunctionCode.READ_DISCRETE_INPUTS, startOffset, numberOfRegisters);

    }
}
