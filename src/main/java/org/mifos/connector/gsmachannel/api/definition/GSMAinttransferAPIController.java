package org.mifos.connector.gsmachannel.api.definition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.mifos.connector.common.gsma.dto.GSMATransaction;
import org.mifos.connector.gsmachannel.api.implementation.GSMAinttransferAPI;
import org.mifos.connector.gsmachannel.util.Headers;
import org.mifos.connector.gsmachannel.util.SpringWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class GSMAinttransferAPIController implements GSMAinttransferAPI {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public GsmaP2PResponseDto gsmaintransfer(String tenant, GSMATransaction requestBody) throws JsonProcessingException {
        org.mifos.connector.gsmachannel.util.Headers headers = new Headers.HeaderBuilder()
                .addHeader("Platform-TenantId", tenant)
                .build();
        Exchange exchange = SpringWrapperUtil.getDefaultWrappedExchange(producerTemplate.getCamelContext(),
                headers, objectMapper.writeValueAsString(requestBody));
        producerTemplate.send("direct:post-gsma-payer-int-transfer", exchange);

        String body = exchange.getIn().getBody(String.class);
        return objectMapper.readValue(body,GsmaP2PResponseDto.class);
    }
}
