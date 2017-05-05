package com.dolplay.commons.neo4j;

import com.dolplay.commons.neo4j.lang.Strings;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neo4jClientFactory extends BasePooledObjectFactory<Neo4jClient> {

    private static Logger logger = LoggerFactory.getLogger(Neo4jClientFactory.class);

    private String url;
    private String httpAuthUserName;
    private String httpAuthPassword;

    public Neo4jClientFactory(String url) {
        super();
        this.url = url;
    }

    public Neo4jClientFactory(String url, String httpAuthUserName, String httpAuthPassword) {
        super();
        this.url = url;
        this.httpAuthUserName = httpAuthUserName;
        this.httpAuthPassword = httpAuthPassword;
    }

    @Override
    public Neo4jClient create() throws Exception {
        if (!Strings.isEmpty(url) && !Strings.isEmpty(httpAuthUserName)
                && !Strings.isEmpty(httpAuthPassword)) {
            return new Neo4jClient(url, httpAuthUserName, httpAuthPassword);
        } else if (!Strings.isEmpty(url)) {
            return new Neo4jClient(url);
        } else {
            logger.error("url is empty!");
            throw new Exception("url is empty!");
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
