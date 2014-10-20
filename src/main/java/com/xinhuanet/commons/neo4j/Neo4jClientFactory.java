package com.xinhuanet.commons.neo4j;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.util.StringUtils;

public class Neo4jClientFactory extends BasePooledObjectFactory<Neo4jClient> {

	private String url;
	private String httpAuthUserName;
	private String httpAuthPassword;

	@Override
	public Neo4jClient create() throws Exception {
		if (!StringUtils.isEmpty(url) && !StringUtils.isEmpty(httpAuthUserName)
				&& !StringUtils.isEmpty(httpAuthPassword)) {
			return new Neo4jClient(url, httpAuthUserName, httpAuthPassword);
		} else if (!StringUtils.isEmpty(url)) {
			return new Neo4jClient(url);
		} else {
			// TODO
			throw new Exception();
		}
	}

	@Override
	public PooledObject<Neo4jClient> wrap(Neo4jClient client) {
		return new DefaultPooledObject<Neo4jClient>(client);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpAuthUserName() {
		return httpAuthUserName;
	}

	public void setHttpAuthUserName(String httpAuthUserName) {
		this.httpAuthUserName = httpAuthUserName;
	}

	public String getHttpAuthPassword() {
		return httpAuthPassword;
	}

	public void setHttpAuthPassword(String httpAuthPassword) {
		this.httpAuthPassword = httpAuthPassword;
	}
}
