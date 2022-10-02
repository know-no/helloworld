/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.rest.action;

import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.*;

import java.io.IOException;
import java.util.List;

public class RestHelloWorldAction extends BaseRestHandler {

    private static final List<Route> routes = List.of(
        new Route(RestRequest.Method.GET, "/_plugins/hello_world"),
        new Route(RestRequest.Method.POST, "/_plugins/hello_world")
    );

    @Override
    public List<Route> routes() {
        return routes;
    }

    @Override
    public String getName() {
        return "rest_handler_hello_world";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient nodeClient) throws IOException {
        var name = restRequest.hasContent() ? restRequest.contentParser().mapStrings().get("name") : "";
        return restChannel -> {
            try {
                restChannel.sendResponse(HelloWorldService.buildResp(name));
            } catch (Exception e) {
                restChannel.sendResponse(new BytesRestResponse(restChannel, e));
            }
        };

    }
}
