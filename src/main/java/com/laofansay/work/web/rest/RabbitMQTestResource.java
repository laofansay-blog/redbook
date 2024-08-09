package com.laofansay.work.web.rest;

import com.laofansay.work.domain.CstJob;
import com.laofansay.work.mq.DelayedMessageSender;
import com.laofansay.work.repository.CstJobRepository;
import com.laofansay.work.service.CstJobService;
import com.laofansay.work.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link CstJob}.
 */
@RestController
@RequestMapping("/api/mq")
public class RabbitMQTestResource {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQTestResource.class);

    @Autowired
    private DelayedMessageSender delayedMessageSender;

    @Autowired
    public RabbitMQTestResource(DelayedMessageSender delayedMessageSender) {
        this.delayedMessageSender = delayedMessageSender;
    }

    @PostMapping("")
    public ResponseEntity<String> sendTestMq() {
        delayedMessageSender.sendDelayedMessage("Hello, this is a delayed message!", 5000); // 5秒延迟
        return ResponseEntity.ok("5秒延迟执行");
    }
}
