package com.walixiwa.dlnaserver.tools;

import android.net.Uri;
import android.util.Log;


import com.walixiwa.model.DeviceModel;

import org.teleal.cling.android.AndroidUpnpService;
import org.teleal.cling.controlpoint.ActionCallback;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.model.types.ServiceId;
import org.teleal.cling.model.types.UDAServiceId;
import org.teleal.cling.support.avtransport.callback.Pause;
import org.teleal.cling.support.avtransport.callback.Play;
import org.teleal.cling.support.avtransport.callback.Seek;
import org.teleal.cling.support.avtransport.callback.SetAVTransportURI;
import org.teleal.cling.support.avtransport.callback.Stop;
import org.teleal.cling.support.connectionmanager.callback.GetProtocolInfo;
import org.teleal.cling.support.model.ProtocolInfos;

public class DlnaCtrlUtil {

    public static void executeAVTransportURI(Device device, String uri, AndroidUpnpService upnpService) {
        ServiceId AVTransportId = new UDAServiceId("AVTransport");
        Service service = device.findService(AVTransportId);
        if (service == null) {
            return;
        }
        ActionCallback callback = new SetAVTransportURI(service, uri) {
            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("SetAVTransportURI", "failed^^^^^^^");
            }

        };
        upnpService.getControlPoint().execute(callback);
    }

    public static void executePlay(Device device, AndroidUpnpService upnpService, final OnPlayCallBack onPlayCallBack) {
        ServiceId AVTransportId = new UDAServiceId("AVTransport");
        Service service = device.findService(AVTransportId);
        if (service == null) {
            return;
        }
        ActionCallback actionCallback = new Play(service) {
            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("Play", "failed^^^^^^^");
                onPlayCallBack.onPlayCallBack(false);
            }

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                Log.e("Play", "success^^^^^^^");
                onPlayCallBack.onPlayCallBack(true);
            }
        };
        upnpService.getControlPoint().execute(actionCallback);
    }

    public static void executePause(Device device, AndroidUpnpService upnpService) {
        ServiceId AVTransportId = new UDAServiceId("AVTransport");
        Service service = device.findService(AVTransportId);
        if (service == null) {
            return;
        }
        ActionCallback actionCallback = new Pause(service) {
            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("Play", "failed^^^^^^^");
            }

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                Log.e("Play", "success^^^^^^^");
            }
        };
        upnpService.getControlPoint().execute(actionCallback);
    }

    public static void executeStop(Device device, AndroidUpnpService upnpService) {
        ServiceId AVTransportId = new UDAServiceId("AVTransport");
        Service service = device.findService(AVTransportId);
        if (service == null) {
            return;
        }
        ActionCallback stopcallback = new Stop(service) {
            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("Play", "failed^^^^^^^");
            }

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                Log.e("Play", "success^^^^^^^");
            }
        };
        upnpService.getControlPoint().execute(stopcallback);
    }

    public void executeSeek(Device device, String mills, AndroidUpnpService upnpService) {
        ServiceId AVTransportId = new UDAServiceId("AVTransport");
        Service service = device.findService(AVTransportId);
        if (service == null) {
            return;
        }
        ActionCallback actionCallback = new Seek(service, mills) {
            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("Play", "failed^^^^^^^");
            }

        };
        upnpService.getControlPoint().execute(actionCallback);
    }


    public static void GetInfo(Device device, AndroidUpnpService upnpService) {
        ServiceId AVTransportId = new UDAServiceId("ConnectionManager");
        Service service = device.findService(AVTransportId);
        ActionCallback actionCallback = new GetProtocolInfo(service) {
            @Override
            public void received(ActionInvocation actionInvocation, final ProtocolInfos sinkProtocolInfos, final ProtocolInfos sourceProtocolInfos) {
                Log.v("sinkProtocolInfos", sinkProtocolInfos.toString());
                Log.v("sourceProtocolInfos", sourceProtocolInfos.toString());

                /*context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        视频获取成功(true);
                    }

                });*/
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, final String arg2) {
                // TODO Auto-generated method stub
                Log.v("GetProtocolInfo", "failed^^^^^^^");
             /*   context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        视频获取成功(false);
                    }

                });*/

            }

        };
        upnpService.getControlPoint().execute(actionCallback);
    }


    public static void startUrl(DeviceModel devicePlay, AndroidUpnpService upnpService, String url,OnPlayCallBack onPlayCallBack) {
        Device device = devicePlay.getDevice();
        Uri.parse(url);
        GetInfo(device, upnpService);
        executeAVTransportURI(device, url, upnpService);
        executePlay(device, upnpService,onPlayCallBack);
    }

    public interface OnPlayCallBack {
        void onPlayCallBack(boolean result);
    }
}
