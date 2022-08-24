package com.mrgreenapps.laravelpusherechotest;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.util.HttpChannelAuthorizer;

import java.util.HashMap;

public class HostedPusherService {

    private Pusher pusher;

    public HostedPusherService(String host, int port, String apiKey, String authUrl, String authToken, boolean tls){
        PusherOptions pusherOptions;
        pusherOptions = new PusherOptions();
        pusherOptions.setHost(host);
        pusherOptions.setWsPort(port);
        pusherOptions.setUseTLS(tls);

        HttpChannelAuthorizer channelAuthorizer = new HttpChannelAuthorizer(authUrl);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + authToken);
        channelAuthorizer.setHeaders(headers);
        pusherOptions.setChannelAuthorizer(channelAuthorizer);

        pusher = new Pusher(apiKey, pusherOptions);
    }

    public HostedPusherService(String apiKey, String cluster, String authUrl, String authToken){
        PusherOptions pusherOptions;
        pusherOptions = new PusherOptions();
        pusherOptions.setCluster(cluster);

        HttpChannelAuthorizer channelAuthorizer = new HttpChannelAuthorizer(authUrl);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + authToken);
        channelAuthorizer.setHeaders(headers);
        pusherOptions.setChannelAuthorizer(channelAuthorizer);

        pusher = new Pusher(apiKey, pusherOptions);
    }

    public Pusher getPusher(){
        return pusher;
    }
}
