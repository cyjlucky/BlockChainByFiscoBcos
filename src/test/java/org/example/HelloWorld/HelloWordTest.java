package org.example.HelloWorld;
import org.example.HelloWorld.model.bo.HelloWorldSetStrInputBO;
import org.example.HelloWorld.service.HelloWorldService;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class HelloWordTest {
    @Autowired
    @Qualifier("HelloWorldService")
    private Map<String, HelloWorldService> testServiceMap;

    @Test
    public void testHello() throws Exception {
        String user = "0xf6d9791f45c124ad528793aaa559b7bb2c93e190";
        HelloWorldService helloWorldService = testServiceMap.get(user);
        HelloWorldSetStrInputBO helloWorldSetStrInputBO = new HelloWorldSetStrInputBO();
        helloWorldSetStrInputBO.set_str("333");
        helloWorldService.setStr(helloWorldSetStrInputBO);
        CallResponse callResponse = helloWorldService.getStr();
        System.out.println("helloWorldService = " + helloWorldService);
        System.out.println("使用java——sdk脚手架通过channel_port:20200方式调用合约接口");
        System.out.println("callResponse.getValues() = " + callResponse.getValues());
    }
}
