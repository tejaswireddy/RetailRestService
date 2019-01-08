package com.retail.marklogic;

import com.marklogic.xcc.*;
import static com.marklogic.developer.corb.util.IOUtils.closeQuietly;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.marklogic.developer.corb.ModuleExecutor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MLUtil {

    private static final Log LOG = LogFactory.getLog(MLUtil.class);

    @Autowired
    private MLConfig mlConfig;

    private SecureRandom random = new SecureRandom();
    private Map<String, ContentSource> connections = new ConcurrentHashMap<>();


    public MLUtil() {

    }

    //Creates connection to ML database
    public ContentSource getConnection(String scope) throws URISyntaxException,XccConfigException {
        ContentSource connection;
        try {
            String host = getRandomHost();
            String connectionKey = host + scope;
            if (this.connections.containsKey(connectionKey)) {
                connection = this.connections.get(connectionKey);
            } else {
                connection = createContentSource(scope, host, Integer.parseInt(mlConfig.getPort()),
                        mlConfig.getContentDatabase(), mlConfig.getPassword());
                connections.put(connectionKey, connection);
            }
        } catch (URISyntaxException|XccConfigException ex) {
            LOG.error("Caught Exception MarkLogicUtil getConnection" + ex);
            throw ex;
        }
        return connection;
    }

    //used to pick a host if there are bunch of hosts
    private String getRandomHost() {
        return mlConfig.getHosts().get(random.nextInt(this.mlConfig.getHosts().size())).replace('"', ' ')
                .replace(']', ' ').replace('[', ' ').trim();
    }

    //establishes connection to marklogic database
    private ContentSource createContentSource(String scope, String host, int port, String database, String pw)
            throws URISyntaxException,XccConfigException {

        ContentSource contentSource;

        try {
            URI connectionURI = createConnectionURI(scope, pw, host, database, port);
            contentSource = ContentSourceFactory.newContentSource(connectionURI);
            contentSource.setAuthenticationPreemptive(true);

        } catch (URISyntaxException|XccConfigException ex) {

            // Throw ML connection exception
            LOG.error("error creating ContentSource", ex);
            throw ex;
        }
        return contentSource;
    }

    //creates connection uri
    private URI createConnectionURI(String scope, String pw, String host, String database, int port) throws URISyntaxException   {
        String con = "xccs://" + scope + ":" + pw + "@" + host + ":" + port + "/" + database;
        return new URI(con);
    }

    //executes fetch-product-price query to fetch price from database for a particular product id
    public List<String> fetchPrice(String pid) throws XccConfigException, RequestException, URISyntaxException, IOException {
        Map<String, String> args = new HashMap<String, String>();
        args.put("id", pid);
        return executeQuery("fetch-product-price.xqy", args);
    }

    //executes update-product-price query to update price and currency of a particular product
    public List<String> updatePrice(String pid, String price, String currency) throws XccConfigException, RequestException, URISyntaxException, IOException {
        Map<String, String> args = new HashMap<String, String>();
        args.put("id", pid);
        args.put("price",price);
        args.put("currency",currency);
        return executeQuery("update-product-price.xqy", args);
    }

    //executes the marklogic xquerys
    public List<String> executeQuery(String query, Map<String, String> args) throws XccConfigException,URISyntaxException,RequestException,IOException {
        long startTime = System.currentTimeMillis();
        List<String> result = new ArrayList<String>();
        Session session = null;
        Request request;
        ResultSequence resultSequence = null;
        try {
            ContentSource contentSource = getConnection(mlConfig.getUsername());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.setCacheResult(false);
            session = contentSource.newSession();
            String adhocQuery = ModuleExecutor.getAdhocQuery(query);
            if (StringUtils.isBlank(adhocQuery)) {
                throw new IllegalStateException(
                        "Unable to read adhoc query " + query + " from classpath or filesystem");
            }
            LOG.debug("Invoking adhoc process module " + query);
            request = session.newAdhocQuery(adhocQuery);
            for (Map.Entry<String, String> e : args.entrySet()) {
                request.setNewStringVariable(e.getKey(), e.getValue());
            }
            request.setOptions(requestOptions);

            resultSequence = session.submitRequest(request);
            if (resultSequence == null || !resultSequence.hasNext()) {
                long endTime = System.currentTimeMillis();
                LOG.info("===== ML_QUERY=" + query + ",ML_DURATION=" + (endTime - startTime));
                return result;
            }
            Iterator<ResultItem> ss = resultSequence.iterator();
            while (ss.hasNext()) {
                ResultItem i = ss.next();
                result.add(IOUtils.toString(i.asInputStream(), Charset.defaultCharset()));
            }
            resultSequence.close();
        } catch (XccConfigException|URISyntaxException|RequestException| IOException exc) {
            LOG.error("Exception occurred in executeQuery for the query "+query+" with args "+ args, exc);
            throw exc;
        } finally {
            closeQuietly(session);
            if (null != resultSequence && !resultSequence.isClosed()) {
                resultSequence.close();
            }
        }
        long endTime = System.currentTimeMillis();
        LOG.info("===== ML_QUERY=" + query + ",ML_DURATION=" + (endTime - startTime));

        return result;

    }


}
