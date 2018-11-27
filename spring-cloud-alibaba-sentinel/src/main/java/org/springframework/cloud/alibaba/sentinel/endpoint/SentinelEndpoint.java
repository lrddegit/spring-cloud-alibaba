/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.alibaba.sentinel.endpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.cloud.alibaba.sentinel.SentinelProperties;
import org.springframework.cloud.alibaba.sentinel.custom.SentinelDataSourceHandler;
import org.springframework.context.ApplicationContext;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

/**
 * Endpoint for Sentinel, contains ans properties and rules
 * @author xiaojing
 */
public class SentinelEndpoint extends AbstractEndpoint<Map<String, Object>> {

	@Autowired
	private SentinelProperties sentinelProperties;

	@Autowired
	private SentinelDataSourceHandler dataSourceHandler;

	@Autowired
	private ApplicationContext applicationContext;

	public SentinelEndpoint() {
		super("sentinel");
	}

	@Override
	public Map<String, Object> invoke() {
		Map<String, Object> result = new HashMap<>();

		List<FlowRule> flowRules = FlowRuleManager.getRules();
		List<DegradeRule> degradeRules = DegradeRuleManager.getRules();
		List<SystemRule> systemRules = SystemRuleManager.getRules();
		result.put("properties", sentinelProperties);
		result.put("FlowRules", flowRules);
		result.put("DegradeRules", degradeRules);
		result.put("SystemRules", systemRules);
		result.put("datasources", new HashMap<String, Object>());

		for (String dataSourceBeanName : dataSourceHandler.getDataSourceBeanNameList()) {
			ReadableDataSource dataSource = applicationContext.getBean(dataSourceBeanName,
					ReadableDataSource.class);
			try {
				((HashMap) result.get("datasources")).put(dataSourceBeanName,
						dataSource.loadConfig());
			}
			catch (Exception e) {
				((HashMap) result.get("datasources")).put(dataSourceBeanName,
						"load error: " + e.getMessage());
			}
		}
		return result;
	}

}
