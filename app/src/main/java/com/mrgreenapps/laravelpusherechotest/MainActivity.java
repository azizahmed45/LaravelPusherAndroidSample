package com.mrgreenapps.laravelpusherechotest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.pusher.client.AuthenticationFailureException;
import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.UserAuthenticator;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpChannelAuthorizer;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Pusher pusher;
    PrivateChannel privateChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HostedPusherService hostedPusherService = new HostedPusherService(
//                "10.0.2.2",
//                6001,
//                "bb519d71f47b79e7a2c2",
//                "http://10.0.2.2/laravel-live-chat/public/broadcasting/auth",
//                "1|WW91sJQP0q0ygY59rJT7sj9oLEAOsPWhRz0a4xjb",
//                false
//        );

        HostedPusherService hostedPusherService = new HostedPusherService(
                "e2f4a46d491e7da6c582",
                "ap1",
                "http://10.0.2.2/laravel-live-chat/public/broadcasting/auth",
                "1|PwUFON3CMYXCl0sVwWdcXu4RUa9vY2S4vL7PhAPZ"
        );


        pusher = hostedPusherService.getPusher();


        if (pusher.getConnection().getState().equals(ConnectionState.DISCONNECTED)) {
            pusher.connect(new ConnectionEventListener() {
                @Override
                public void onConnectionStateChange(ConnectionStateChange change) {

                    if (change.getCurrentState().equals(ConnectionState.CONNECTED)) {

                        if (privateChannel == null || !privateChannel.isSubscribed()) {

                            privateChannel = pusher.subscribePrivate("private-chat.user.1", new PrivateChannelEventListener() {
                                @Override
                                public void onAuthenticationFailure(String message, Exception e) {
                                    Log.d("TAG", "onAuthenticationFailure: ");
                                }

                                @Override
                                public void onSubscriptionSucceeded(String channelName) {
                                    Log.d("TAG", "onSubscriptionSucceeded: ");
                                }

                                @Override
                                public void onEvent(PusherEvent event) {
                                    Log.d("TAG", "onEvent: " + event.toString());
                                }
                            }, "message.received");
                        }
                    }


                }

                @Override
                public void onError(String message, String code, Exception e) {
                    Log.d("TAG", "onError: ");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        pusher.disconnect();
        super.onDestroy();
    }
}