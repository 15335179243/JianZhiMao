package com.example.common_base.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlackBox {

//    private static ExecutorService sExecutorService;
//
//    @SuppressLint("StaticFieldLeak")
//    static Context sContext;
//
//    private static BoxConfig sBoxConfig;
//
//    private static Handler sHandler;
//
//    static {
//        sExecutorService = Executors.newSingleThreadExecutor();
//        sHandler = new Handler(Looper.getMainLooper());
//    }
//
//    public static void init(Application application, BoxConfig config){
//        sContext = application.getApplicationContext();
//        sBoxConfig = config;
//        if(isEnable()){
//            PageStack.init(application);
//            initDeviceInfo();
//        }
//        BoxFileManager.checkLogSurvivalTime();
//    }
//
//    static boolean isEnable(){
//        return sBoxConfig!=null && sBoxConfig.isEnable();
//    }
//
//    private static void initDeviceInfo() {
//        postRecord(new DataPackage("设备信息")
//                .recordType(DataPackage.RECORD_TYPE_DEVICE_INFO)
//                .addEnv(new DeviceEnv())
//                .appendRecord(false));
//    }
//
//    static BoxConfig getConfig(){
//        if (sBoxConfig==null)
//            sBoxConfig = new BoxConfig(
//                    new BoxConfig.Builder()
//                            .setRecordDirPath(sContext.getExternalFilesDir(null)));
//        return sBoxConfig;
//    }
//
//    static String getFileProviderAuthority(Context context){
//        BoxConfig config = BlackBox.getConfig();
//        if(config!=null && !TextUtils.isEmpty(config.getFileProviderAuthority()))
//            return config.getFileProviderAuthority();
//        return context.getPackageName() + ".fileProvider";
//    }
//
//    static String findRecordTypeName(int recordType){
//        BoxConfig config = getConfig();
//        if(config!=null){
//            SparseArray<String> recordFileTypeArrays = config.getRecordFileTypeArrays();
//            if(recordFileTypeArrays!=null)
//                return recordFileTypeArrays.get(recordType);
//        }
//        return null;
//    }
//
//    public static void postRecord(DataPackage dataPackage){
//        if(sBoxConfig!=null && sBoxConfig.isEnable()){
//            sExecutorService.submit(new SaveTask(dataPackage));
//        }
//    }
//
//    static void submit(Runnable runnable){
//        sExecutorService.submit(runnable);
//    }
//
//    static void post(Runnable runnable){
//        sHandler.post(runnable);
//    }
//
//    public static void postException(Throwable e){
//        postException(e, new ArrayList<IEnv>(){{add(new PageEnv());add(new NetStateEnv());}});
//    }
//
//    public static void postException(Throwable e, List<IEnv> envs){
//        if(!isEnable())
//            return;
//        StringWriter sw = new StringWriter();
//        try{
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            String errorInfo = sw.toString();
//            postRecord(new DataPackage("!!!Exception!!!")
//                    .recordType(DataPackage.RECORD_TYPE_EXCEPTION)
//                    .addEnvs(envs)
//                    .addExtra("Exception_Log", errorInfo));
//        }catch (Throwable err){
//            err.printStackTrace();
//        }
//    }
//
//    public static void postHTTPRecord(HTTPPackage httpPackage){
//        if(httpPackage!=null && isEnable()){
//            DataPackage dataPackage = httpPackage.packageData();
//            if(dataPackage!=null)
//                dataPackage.customSavePath = httpPackage;
//            postRecord(dataPackage);
//        }
//    }

}
