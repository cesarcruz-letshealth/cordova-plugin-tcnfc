
        var argscheck
            = require( 'cordova/argscheck' );
        var channel
            = require( 'cordova/channel' );
        var exec
            = require( 'cordova/exec' );
        var cordova
            = require( 'cordova' );

        channel
            .createSticky(
                'onCordovaInfoReady' );
        // Tell cordova channel to wait on the CordovaInfoReady event
        channel
            .waitForInitialization(
                'onCordovaInfoReady' );

        function Device()
        {
            this.deviceInfo
                = {
                available: false,
                platform: null,
                version: null,
                uuid: null,
                cordova: null,
                model: null,
                manufacturer: null,
                isVirtual: null,
                serial: null,
                isiOSAppOnMac: null,
            };

            this.testVideoIdData
                = {
                testData: ''
            };

            const getInfoAction =
                function( info )
                {
                    // ignoring info.cordova returning from native, we should use value from cordova.version defined in cordova.js
                    // TODO: CB-5105 native implementations should not return info.cordova
                    var buildLabel
                        = cordova.version;

                    me.deviceInfo
                        .available = true;
                    me.deviceInfo
                        .platform = info.platform;
                    me.deviceInfo
                        .version = info.version;
                    me.deviceInfo
                        .uuid = info.uuid;
                    me.deviceInfo
                        .cordova = buildLabel;
                    me.deviceInfo
                        .model = info.model;
                    me.deviceInfo
                        .isVirtual = info.isVirtual;
                    // isiOSAppOnMac is iOS specific. If defined, it will be appended.
                    if( info.isiOSAppOnMac !== undefined )
                    {
                        me.deviceInfo
                            .isiOSAppOnMac = info.isiOSAppOnMac;
                    }
                    me.deviceInfo
                        .manufacturer = info.manufacturer || 'unknown';
                    me.deviceInfo
                        .serial = info.serial || 'unknown';

                    // SDK Version is Android specific. If defined, it will be appended.
                    if( info.sdkVersion !== undefined )
                    {
                        me.deviceInfo
                            .sdkVersion = info.sdkVersion;
                    }

                    channel
                        .onCordovaInfoReady
                        .fire();
                }

            const me = this;

            const getVideoTestAction
                = function( testArguments )
                {
                    console.log( testArguments );

                    me.testVideoIdData
                        .testData = testArguments.testData;
                }

            const handleException
                = function( exception )
                {
                    me.deviceInfo
                        .available = false;

                    console.error(
                        '[ERROR] Error initializing cordova-plugin-tcnfc: '
                        + exception );
                }

            channel
                .onCordovaReady
                .subscribe(
                    function()
                    {
                        me.getInfo(
                            getInfoAction,
                            handleException
                        );
                        me.getVideoTest(
                            getVideoTestAction,
                            handleException
                        );
                    } );
        }

        /**
         * Get device info
         *
         * @param {Function} successCallback The function to call when the heading data is available
         * @param {Function} errorCallback The function to call when there is an error getting the heading data. (OPTIONAL)
         */
        Device
            .prototype
            .getInfo = function(
                successCallback
                , errorCallback )
            {
                argscheck
                    .checkArgs(
                        'fF'
                        , 'Device.getInfo'
                        , arguments );

                exec(
                    successCallback
                    , errorCallback
                    , 'Device'
                    , 'getDeviceInfo'
                    , [] );
            };

        Device
            .prototype
            .getVideoTest = function(
                successCallback
                , errorCallback )
                {
                    argscheck
                        .checkArgs(
                            'fF'
                            , 'Device.getVideoTest'
                            , arguments );

                    exec(
                        successCallback
                        , errorCallback
                        , 'Device'
                        , 'getVideoTest'
                        , [] );
                };

            Device
                .prototype
                .createNewActivity = function( )
                    {
                        exec(
                            function( arguments ){}
                            , function( exception )
                              {
                                  console.error(
                                      '[ERROR] Error Calling createNewActivity: '
                                      + exception );
                              }
                            , 'Device'
                            , 'createNewActivity'
                            , [] );
                    };

        module
            .exports = new Device();

