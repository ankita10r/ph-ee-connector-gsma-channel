package org.mifos.connector.gsmachannel.api.definition;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.mifos.connector.gsmachannel.api.implementation.GSMA_API;
import org.mifos.connector.gsmachannel.util.Headers;
import org.mifos.connector.gsmachannel.util.SpringWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;

public class ChannelGSMAAPIController implements GSMA_API {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public String gsma(HttpServletResponse response) throws Exception {
        org.mifos.connector.gsmachannel.util.Headers headers = new Headers.HeaderBuilder()
                .build();
        Exchange exchange = SpringWrapperUtil.getDefaultWrappedExchange(producerTemplate.getCamelContext(),
                 headers,null);
        producerTemplate.send("direct:get-channel-gsma", exchange);
        response.setStatus(exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class));

        return exchange.getIn().getBody(String.class);
    }
}
