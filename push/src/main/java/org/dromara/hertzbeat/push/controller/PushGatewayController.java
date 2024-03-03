package org.dromara.hertzbeat.push.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dromara.hertzbeat.common.entity.dto.Message;
import org.dromara.hertzbeat.common.util.prometheus.Label;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * push gateway controller
 *
 * @author vinci
 */
@Tag(name = "Metrics Push Gateway API | 监控数据推送网关API")
@RestController
@RequestMapping(value = "/api/push/abc", produces = {APPLICATION_JSON_VALUE})
public class PushGatewayController {

    @PostMapping()
    @Operation(summary = "Push metric data to hertzbeat pushgateway", description = "推送监控数据到hertzbeat推送网关")
    public ResponseEntity<Message<Void>> pushMetrics(HttpServletRequest request) throws IOException, IOException {
        InputStream inputStream = request.getInputStream();
        System.out.println(parseMetricName(inputStream));
//        int c;
//
//
//
//        while ((c = inputStream.read()) != -1) {
//            System.out.print((char)c);
//        }
        return ResponseEntity.ok(Message.success("Push success"));
    }

}
