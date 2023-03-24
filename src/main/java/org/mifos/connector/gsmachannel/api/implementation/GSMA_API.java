package org.mifos.connector.gsmachannel.api.implementation;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

public interface GSMA_API {
    @GetMapping("/channel/gsma")
    String gsma(HttpServletResponse response) throws Exception;
}
