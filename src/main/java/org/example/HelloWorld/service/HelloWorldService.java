package org.example.HelloWorld.service;

import java.lang.Exception;
import java.lang.String;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.HelloWorld.model.bo.HelloWorldSetStrInputBO;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class HelloWorldService {
  public static final String ABI = org.example.HelloWorld.utils.IOUtil.readResourceAsString("abi/HelloWorld.abi");

  public static final String BINARY = org.example.HelloWorld.utils.IOUtil.readResourceAsString("bin/ecc/HelloWorld.bin");

  public static final String SM_BINARY = org.example.HelloWorld.utils.IOUtil.readResourceAsString("bin/sm/HelloWorld.bin");

  @Value("${system.contract.helloWorldAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public TransactionResponse setStr(HelloWorldSetStrInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "setStr", input.toArgs());
  }

  public CallResponse getStr() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getStr", Arrays.asList());
  }
}
