package org.dromara.hertzbeat.common.util.prometheus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    private String metricName;
    private List<Label> labelList;
    private Double value;
    private Long timestamp;
}
