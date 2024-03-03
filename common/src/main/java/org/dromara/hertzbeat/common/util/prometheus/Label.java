package org.dromara.hertzbeat.common.util.prometheus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    private String name;
    private String value;
}
