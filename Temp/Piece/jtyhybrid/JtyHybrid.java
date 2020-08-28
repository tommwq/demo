import android.webkit.WebView;
import android.webkit.WebSettings;

public class JtyHybrid {
    public class JtyHybridHandler extends Handler {
        public static final int UPDATE_ATTRIBUTE = 1;
        public static final int TERMINATE = 2;
        public static final String ATTRIBUTE_KEY = "ATTRIBUTE_KEY";
        public static final String ATTRIBUTE_VALUE = "ATTRIBUTE_VALUE";
        public void updateAttribute(String attribute, String value){
            Message message = mHandler.obtainMessage();
            message.what = UPDATE_ATTRIBUTE;
            Bundle bundle = new Bundle();
            bundle.putString(ATTRIBUTE_KEY, attribute);
            bundle.putString(ATTRIBUTE_VALUE, value);
            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }

    public JtyHybrid(JtyHybridHandler handler, WebView webView){
        mHandler = handler;
        mWebView = webView;
    }
    
    public void clearCache(){
        clearCache(false);
    }

    public void clearCache(boolean includeDiskFile){
        mWebView.clearCache(includeDiskFile);
    }

    public void terminateApp(){
        // todo
    }

    public int cacheMode(){
        return mWebView.getSettings().getCacheMode();
    }

    public void changeCacheMode(int mode){
        mWebView.getSettings().setCacheMode(mode);
    }

    public void updateAttribute(String attribute, String value){
        mHandler.updateAttribute(attribute, value);
    }
    
    public void sendDataGram(String host, int port, String text){
    }

    public void log(String level, String tag, String text){
    }

    public void log(String text){
    }

    public reportDeviceInformation(){
    }

    private WebView mWebView;
    private JtyHybridHandler mHandler;
}