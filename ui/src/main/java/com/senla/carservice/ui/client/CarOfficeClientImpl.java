package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.ui.util.ExceptionUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@NoArgsConstructor
@Slf4j
public class CarOfficeClientImpl implements CarOfficeClient {
    private static final String GET_FREE_PLACES_MASTERS_BY_DATE_PATH = "numberOfFreePlaces";
    private static final String GET_NEAREST_FREE_DATE_PATH = "nearestFreeDate";
    private static final String EXPORT_ENTITIES_PATH = "export";
    private static final String IMPORT_ENTITIES_PATH = "import";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public String getFreePlacesMastersByDate(String date) {
        log.debug("[getFreePlacesMastersByDate]");
        log.trace("[date: {}]", date);
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GET_FREE_PLACES_MASTERS_BY_DATE_PATH)
                    .queryParam("date", date);
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                builder.toUriString(), ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String getNearestFreeDate() {
        log.debug("[getNearestFreeDate]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                GET_NEAREST_FREE_DATE_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String exportEntities() {
        log.debug("[exportEntities]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                EXPORT_ENTITIES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String importEntities() {
        log.debug("[importEntities]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                IMPORT_ENTITIES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

}