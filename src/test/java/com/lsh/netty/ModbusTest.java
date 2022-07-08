package com.lsh.netty;

import com.lsh.ModbusFactory;
import com.lsh.ModbusMaster;
import com.lsh.entity.exception.ModbusInitException;
import com.lsh.entity.exception.ModbusTransportException;
import com.lsh.ip.IpParameters;
import com.lsh.msg.ModbusResponse;
import com.lsh.msg.ReadHoldingRegistersRequest;
import com.lsh.msg.ReadHoldingRegistersResponse;
import com.lsh.msg.ReadNumericRequest;
import com.lsh.msg.ReadNumericResponse;

import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName ModbusTest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 19:58
 * @Version
 */
public class ModbusTest {

    ModbusMaster tcpMaster;

    @Before
    public void setUp() throws ModbusInitException {
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("127.0.0.1");
        ipParameters.setPort(9999);
        tcpMaster = new ModbusFactory().createTcpMaster(ipParameters, true);
        tcpMaster.init();
    }

    @Test
    public void testReadHoldingRegisters() {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(0, 1);
        try {
            // ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse)tcpMaster.send(request);
            // System.out.println(response.toString());
            ReadNumericResponse response2 =  (ReadNumericResponse) tcpMaster.send(new ReadNumericRequest(0, 1));
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadNumeric() {
        ReadNumericRequest request = new ReadNumericRequest(0, 1);
        try {
            ReadNumericResponse response = (ReadNumericResponse)tcpMaster.send(request);
            System.out.println(response.toString());
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }
}
