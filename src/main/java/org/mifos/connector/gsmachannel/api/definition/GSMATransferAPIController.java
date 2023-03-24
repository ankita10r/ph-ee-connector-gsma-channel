package org.mifos.connector.gsmachannel.api.definition;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.camel.*;
import org.mifos.connector.gsmachannel.util.Headers;
import org.mifos.connector.common.channel.dto.PhErrorDTO;
import org.mifos.connector.common.gsma.dto.*;
import org.mifos.connector.gsmachannel.api.implementation.GSMATransferAPI;
import org.mifos.connector.gsmachannel.util.SpringWrapperUtil;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class GSMATransferAPIController implements GSMATransferAPI {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ResponseEntity<Object> gsmatransfer(String tenant, String correlationId, GSMATransaction requestBody) throws JsonProcessingException {
        org.mifos.connector.gsmachannel.util.Headers headers = new Headers.HeaderBuilder()
                .addHeader("Platform-TenantId", tenant)
                .addHeader("X-CorrelationID",correlationId)
                .addHeader("Content-Type","application/json")
                .build();
        Exchange exchange = SpringWrapperUtil.getDefaultWrappedExchange(producerTemplate.getCamelContext(),
                headers, objectMapper.writeValueAsString(requestBody));
        producerTemplate.send("direct:post-gsma-transfer", exchange);
        String body = exchange.getIn().getBody(String.class);
        try {
            if (body.contains("error")) {
                return new ResponseEntity<>(objectMapper.readValue(body, PhErrorDTO.class), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(objectMapper.readValue(body, GsmaP2PResponseDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
