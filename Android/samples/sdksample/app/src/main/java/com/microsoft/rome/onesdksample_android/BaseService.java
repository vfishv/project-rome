/*
 * Copyright (C) Microsoft Corporation. All rights reserved.
 */

package com.microsoft.rome.onesdksample_android;

import androidx.annotation.NonNull;
import android.util.Log;

import com.microsoft.connecteddevices.EventListener;
import com.microsoft.connecteddevices.remotesystems.commanding.AppServiceConnection;
import com.microsoft.connecteddevices.remotesystems.commanding.AppServiceRequestReceivedEventArgs;
import com.microsoft.connecteddevices.remotesystems.AppServiceInfo;
import com.microsoft.connecteddevices.remotesystems.commanding.AppServiceConnectionOpenedInfo;
import com.microsoft.connecteddevices.remotesystems.commanding.AppServiceProvider;

/**
 * Base class for app services.
 *
 * Subclasses must implement at least onEvent to handle incoming requests.
 */
public abstract class BaseService implements AppServiceProvider, EventListener<AppServiceConnection, AppServiceRequestReceivedEventArgs> {
    // region Member Variables
    private static final String TAG = BaseService.class.getName();
    //protected MainActivity mMainActivity;

    private AppServiceInfo mInfo;
    // This must be kept alive to receive AppService request messages
    private AppServiceConnection mConnection;
    // endregion

    // region Constructor
    public BaseService(/*MainActivity mainActivity, */AppServiceInfo appServiceInfo) {
        //mMainActivity = mainActivity;
        mInfo = appServiceInfo;
    }
    // endregion

    // region Overrides
    /**
     * Receive incoming connections and listen for subsequent requests.
     */
    @Override
    public void onConnectionOpened(final @NonNull AppServiceConnectionOpenedInfo args) {
        AppServiceConnection connection = args.getAppServiceConnection();
        Log.i(TAG, "Opened incoming connection to app service " + connection.getAppServiceInfo().getPackageId() + "/" +
                       connection.getAppServiceInfo().getName());
        connection.requestReceived().subscribe(this);

        // Capture the AppServiceConnection reference and hold on to it for a while to allow us to receive incoming requests
        mConnection = connection;
    }

    @Override
    public @NonNull AppServiceInfo getAppServiceInfo() {
        return mInfo;
    }
    // endregion
}
