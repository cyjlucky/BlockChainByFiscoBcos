package org.example.HelloWorld;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@PropertySource(value = {"classpath:application.properties"})
public class HelloWorldTestByWeBASE_Front {
    @Value("${URL}")
    private String URL;
    @Value("${system.contract.helloWorldAddress}")
    private String CONTRACT_ADDRESS;
    private final String CONTRACT_NAME = "HelloWorld";
    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"_str\",\"type\":\"string\"}],\"name\":\"setStr\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getStr\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";
    public static final String FUNC_SETSTR = "setStr";

    public static final String FUNC_GETSTR = "getStr";

    public static final String TEST_USER_ADDRESS = "0xf6d9791f45c124ad528793aaa559b7bb2c93e190";


    @Test
    public void testSet(){
        // 创建一个RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();
        // 创建请求头，并设置为JSON格式
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 构造请求体
        JSONObject requestBody = new JSONObject();
        //设置接口参数
        JSONArray params = JSONArray.parseArray("[\"Hello FISCO-BCOS\"]");
        requestBody.put("contractName",CONTRACT_NAME);
        requestBody.put("contractAddress",CONTRACT_ADDRESS);
        requestBody.put("funcParam",params);
        //此处ABI原为json格式，因为JSONObject对象要序列化为json格式，避免重复序列化所以先将ABI转为java对象
        requestBody.put("contractAbi", JSONArray.parseArray(ABI));
        requestBody.put("user",TEST_USER_ADDRESS);
        requestBody.put("funcName",FUNC_SETSTR);
        // 创建HttpEntity对象，将请求体和请求头封装为一个请求实体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发送POST请求，并获取响应结果
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,HttpMethod.POST, requestEntity, String.class);
        System.out.println("responseEntity = " + responseEntity);
        System.out.println("responseEntity.getStatusCodeValue() = " + responseEntity.getStatusCodeValue());
        if(responseEntity.getStatusCodeValue()==200){
            System.out.println("调用合约setStr()成功，结果为：" + responseEntity.getBody());
        }
    }

    @Test
    public void testGet(){
        // 创建一个RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();
        // 创建请求头，并设置为JSON格式
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 构造请求体
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractName",CONTRACT_NAME);
        requestBody.put("contractAddress",CONTRACT_ADDRESS);
        //此处ABI原为json格式，因为JSONObject对象要序列化为json格式，避免重复序列化所以先将ABI转为java对象
        requestBody.put("contractAbi", JSONArray.parseArray(ABI));
        requestBody.put("user",TEST_USER_ADDRESS);
        requestBody.put("funcName",FUNC_GETSTR);
        // 创建HttpEntity对象，将请求体和请求头封装为一个请求实体
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(requestBody, headers);
        // 发送POST请求，并获取响应结果
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,HttpMethod.POST, requestEntity, String.class);
        System.out.println("responseEntity = " + responseEntity);
        System.out.println("responseEntity.getStatusCodeValue() = " + responseEntity.getStatusCodeValue());
        if(responseEntity.getStatusCodeValue()==200){
            System.out.println("调用合约getStr()成功，结果为：" + responseEntity.getBody());
        }
        // 发送Get请求，并获取响应结果
        String response = restTemplate.getForObject("http://47.122.19.138:5002/WeBASE-Front/version", String.class);
        System.out.println("version = " + response);
    }


}
