package com.lsh.nettymodbus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lsh.ip.tcp.TcpGateway;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class NettyModbusApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyModbusApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0)  {
            throw new IllegalArgumentException("请指定目标服务器IP以及端口");
        }

        String targetHost = args[0];
        Integer targetPort = 502;
        Integer localPort = 502;
        try {
            if (args.length > 1) {
                targetPort = Integer.parseInt(args[1]);
            }
            if (args.length > 2) {
                localPort = Integer.parseInt(args[2]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("端口格式错误", e);
        }

        log.info("开始转发Modbus请求: 0.0.0.0:{} -> {}:{}", localPort, targetHost, targetPort);
        new TcpGateway().connect(localPort, targetHost, targetPort);
    }

}
