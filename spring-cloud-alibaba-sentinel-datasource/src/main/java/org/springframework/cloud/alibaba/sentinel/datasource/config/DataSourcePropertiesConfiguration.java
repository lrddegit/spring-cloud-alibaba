package org.springframework.cloud.alibaba.sentinel.datasource.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

/**
 * Using By ConfigurationProperties.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 * @see NacosDataSourceProperties
 * @see ApolloDataSourceProperties
 * @see ZookeeperDataSourceProperties
 * @see FileDataSourceProperties
 */
public class DataSourcePropertiesConfiguration {

	private FileDataSourceProperties file;

	private NacosDataSourceProperties nacos;

	private ZookeeperDataSourceProperties zk;

	private ApolloDataSourceProperties apollo;

	public FileDataSourceProperties getFile() {
		return file;
	}

	public void setFile(FileDataSourceProperties file) {
		this.file = file;
	}

	public NacosDataSourceProperties getNacos() {
		return nacos;
	}

	public void setNacos(NacosDataSourceProperties nacos) {
		this.nacos = nacos;
	}

	public ZookeeperDataSourceProperties getZk() {
		return zk;
	}

	public void setZk(ZookeeperDataSourceProperties zk) {
		this.zk = zk;
	}

	public ApolloDataSourceProperties getApollo() {
		return apollo;
	}

	public void setApollo(ApolloDataSourceProperties apollo) {
		this.apollo = apollo;
	}

	public List<String> getInvalidField() {
		List<String> fieldList = new ArrayList<>();
		for (Field field : this.getClass().getDeclaredFields()) {
			try {
				if (!ObjectUtils.isEmpty(field.get(this))) {
					fieldList.add(field.getName());
				}
			}
			catch (IllegalAccessException e) {
				// won't happen
			}
		}
		return fieldList;
	}

}
