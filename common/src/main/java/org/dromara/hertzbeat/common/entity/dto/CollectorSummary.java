/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hertzbeat.common.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.hertzbeat.common.entity.manager.Collector;

/**
 * collector summary
 * @author tom
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "collector summary")
public class CollectorSummary {
    
    @Schema(description = "the collector info")
    private Collector collector;
    
    @Schema(description = "the number of monitors pinned in this collector")
    private int pinMonitorNum;
    
    @Schema(description = "the number of monitors dispatched in this collector")
    private int dispatchMonitorNum;
}
