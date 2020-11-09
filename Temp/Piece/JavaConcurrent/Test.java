import java.util.concurrent.ConcurrentSkipListMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.time.LocalTime;
import java.util.Random;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.util.concurrent.LinkedBlockingQueue;
import java.time.Duration;
import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.StringBuilder;

public class Test {
    public class MapParam {
        public int mEntryCount;
        public int mValueLength;
        public MapParam(int entryCount, int valueLength){
            mEntryCount = entryCount;
            mValueLength = valueLength;
        }
    }

    public static void main(String[] args){
        Test test = new Test();
        // init logger
        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter(){
                public String format(LogRecord record){
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    StringBuilder builder = new StringBuilder();
                    builder.append(format.format(new Date(record.getMillis())));
                    builder.append(" ");
                    builder.append(formatMessage(record));
                    builder.append("\n");
                    return builder.toString();
                }
                public String getHead(Handler h){
                    return super.getHead(h);
                }
                public String getTail(Handler h){
                    return super.getTail(h);
                }
            });
        logger.addHandler(handler);
        //test.test();
        test.testThread();
    }

    public void testThread(){
        // 假设有10000只股票，每秒钟行情更新一次。
        // 假设有20 * 10000用户，每秒钟更新订阅2次。
        LinkedBlockingQueue<SubscribeEvent> subscribeEvents = new LinkedBlockingQueue<SubscribeEvent>();
        LinkedBlockingQueue<QuoteEvent> quoteEvents = new LinkedBlockingQueue<QuoteEvent>();

        SubscribeThread subscribeThread = new SubscribeThread(subscribeEvents);
        QuoteThread quoteThread = new QuoteThread(quoteEvents);
        HandleThread handleThread = new HandleThread(quoteEvents, subscribeEvents);

        handleThread.start();
        subscribeThread.start();
        quoteThread.start();

        Logger logger = Logger.getLogger("test");
        try {
            quoteThread.join();
            subscribeThread.setExitFlag(true);
            subscribeThread.join();
            handleThread.setExitFlag(true);
            handleThread.join();
        } catch (InterruptedException e){
            logger.info(e.getMessage());
            // ignore
        }
    }

    public void test(MapParam p){
        Map<Integer, ArrayList<Integer>> m = new ConcurrentSkipListMap <Integer, ArrayList<Integer>>();
        for (int i = 0; i < p.mEntryCount; ++i){
            ArrayList<Integer> l = new ArrayList<Integer>();
            for (int j = 0; j < p.mValueLength; ++j){
                l.add(j);
            }
            m.put(i, l);
        }

        long memory = testMemory(m);
        System.out.println(String.format("key: %d value: %d -> size: %.2f MB", p.mEntryCount, p.mValueLength, memory / 1024.0 / 1024));

        double search = testSearch(m) / 1000.0 / 1000;
        System.out.println(String.format("search time: %.2f ms", search));

        double update = testUpdate(m) / 1000.0 / 1000;
        System.out.println(String.format("update time: %.2f ms", search));
                
        // clean
        m = null;
        System.gc();
    }

    public long testSearch(Map<Integer, ArrayList<Integer>> m){
        int count = 10000;
        int size = m.size();
        Random random = new Random(LocalTime.now().toNanoOfDay());
        LocalTime start = LocalTime.now();
        for (int i = 0; i < count; ++i){
            ArrayList l = (ArrayList)m.get(random.nextInt(size));
        }
        LocalTime stop = LocalTime.now();
        return (stop.toNanoOfDay() - start.toNanoOfDay());
    }

    public long testUpdate(Map<Integer, ArrayList<Integer>> m){
        int count = 10000;
        int size = m.size();
        Random random = new Random(LocalTime.now().toNanoOfDay());
        LocalTime start = LocalTime.now();
        for (int i = 0; i < count; ++i){
            int index = random.nextInt(size);
            ArrayList<Integer> l = m.get(index);
            switch (i % 2){
            case 0:
                l.add(i);
                break;
            case 1:
                l.remove(0);
                break;
            }
            m.put(index, l);
        }
        LocalTime stop = LocalTime.now();
        return (stop.toNanoOfDay() - start.toNanoOfDay());
    }

    public long testMemory(Map m){
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public void test(){
        // test memory usage.
        MapParam[] params = {
            new MapParam(200 * 10000, 10),
            new MapParam(10 * 10000, 200),
            new MapParam(50 * 10000, 40),
        };

        for (MapParam p : params){
            test(p);
        }
    }
}

class SubscribeThread extends Thread {
    private boolean mExit = false;
    public SubscribeThread(LinkedBlockingQueue<SubscribeEvent> events){
        mEvents = events;
    }
    public void setExitFlag(boolean exit){
        mExit = exit;
    }

    public void run(){
        // 50万用户，每0.1秒5万用户更新订阅。
        Ticker ticker = new Ticker(100);
        Random random = new Random(System.currentTimeMillis());
        int round = 0;
        int userId, secuId, subId;
        Logger logger = Logger.getLogger("test");

        int count = 0;
        logger.info("subscribe start");
        while (true){
            if (mExit && mEvents.size() == 0){
                break;
            }
            ticker.tick();
            for (int i = 0; i < 50000; ++i){
                userId = round * 50000 + i;
                secuId = random.nextInt(10000);
                subId = random.nextInt(16);
                SubscribeEvent e = new SubscribeEvent(userId, secuId, subId, null);
                // todo: log
                try {
                    mEvents.put(e);
                    ++count;
                } catch (InterruptedException ex){
                    logger.info(ex.getMessage());
                }
            }
            if (++round == 10){
                round = 0;
            }
        }
        logger.info(String.format("subscribe end. events: %d", count));
    }

    private LinkedBlockingQueue<SubscribeEvent> mEvents = null;
}

class QuoteThread extends Thread {
    public QuoteThread(LinkedBlockingQueue<QuoteEvent> events){
        mEvents = events;
    }
    public void run(){
        // 运行10分钟。
        // 每秒生成一次股票行情。一万只股票。股票id从0到9999。
        Logger logger = Logger.getLogger("test");
        Duration duration = Duration.ofMillis(500);
        LocalTime stop = LocalTime.now().plusSeconds(10); //.plusMinutes(1);
        Ticker ticker = new Ticker(500);
        Random random = new Random(System.currentTimeMillis());
        int count = 0;

        logger.info("quote start");
        while (true){
            if (LocalTime.now().compareTo(stop) >= 0){
                break;
            }
            ticker.tick();
            for (int i = 0; i < 10000; ++i){
                QuoteEvent e = new QuoteEvent(i, random.nextFloat());
                // todo: log
                try {
                    mEvents.put(e);
                    ++count;
                } catch (InterruptedException ex){
                    logger.info(ex.getMessage());
                }
            }
        }
        logger.info(String.format("quote end. events: %d", count));
    }
    private LinkedBlockingQueue<QuoteEvent> mEvents = null;
}

class HandleThread extends Thread {
    private boolean mExit = false;
    private TreeMap<Integer, TreeMap<Integer, ArrayList<Integer>>> mData
        = new TreeMap<Integer, TreeMap<Integer, ArrayList<Integer>>>();

    public HandleThread(LinkedBlockingQueue<QuoteEvent> quoteEvents, 
                        LinkedBlockingQueue<SubscribeEvent> subscribeEvents){
        mQuoteEvents = quoteEvents;
        mSubscribeEvents = subscribeEvents;
    }

    public void setExitFlag(boolean exit){
        mExit = exit;
    }

    public String getResponse(int secuId, int subId){
        StringBuilder builder = new StringBuilder();
        builder.append(secuId);
        builder.append(subId);
        return builder.toString();
    }

    public void sendResponse(String resp){
    }

    public void run(){
        Logger logger = Logger.getLogger("test");
        int quoteCount = 0;
        int subCount = 0;
        logger.info("handle start");
        while (true){
            if (mExit && mQuoteEvents.size() == 0 && mSubscribeEvents.size() == 0){
                break;
            }
            QuoteEvent qe = mQuoteEvents.poll();
            while (qe != null){
                ++quoteCount;
                TreeMap<Integer, ArrayList<Integer>> subscribes = mData.get(qe.securityId());
                if (subscribes != null){
                    for (Integer i: subscribes.keySet()){
                        ArrayList<Integer> carers = subscribes.get(i);
                        if (carers == null){
                            continue;
                        }
                        for (Integer userId: carers){
                            sendResponse(getResponse(userId, i));
                        }
                    }
                }
                qe = mQuoteEvents.poll();
            }
            SubscribeEvent se = mSubscribeEvents.poll();
            while (se != null){
                ++subCount;
                int secuId = se.securityId();
                int userId = se.userId();
                int subId = se.subscribeId();
                TreeMap<Integer, ArrayList<Integer>> sub = mData.get(secuId);
                if (sub != null){
                    ArrayList<Integer> users = sub.get(subId);
                    if (users != null){
                        users.add(userId);
                    }
                }
                // remove if conflicts.
                se = mSubscribeEvents.poll();
            }
        }
        logger.info(String.format("handle end. quote events: %d subscribe events: %d", 
                                  quoteCount, subCount));
    }
    
    private LinkedBlockingQueue<QuoteEvent> mQuoteEvents = null;
    private LinkedBlockingQueue<SubscribeEvent> mSubscribeEvents = null;
}

class SubscribeEvent {
    public SubscribeEvent(int userId, int securityId, int subscribeId, Object param){
        mSecurityId = securityId;
        mSubscribeId = subscribeId;
        mParam = param;
        mUserId = userId;
    }
    public int securityId(){
        return mSecurityId;
    }
    public int subscribeId(){
        return mSubscribeId;
    }
    public Object param(){
        return mParam;
    }
    public int userId(){
        return mUserId;
    }
    private int mUserId = -1;
    private int mSecurityId = -1;
    private int mSubscribeId = -1;
    private Object mParam = null;
}

class QuoteEvent {
    public QuoteEvent(int securityId, float price){
        mSecurityId = securityId;
        mPrice = price;
    }
    public float price(){
        return mPrice;
    }
    public int securityId(){
        return mSecurityId;
    }
    private int mSecurityId = 0;
    private float mPrice = 0.0f;
}

class Ticker {
    private int mDuration = 0;
    private long mLastTickMillis = 0;

    public Ticker(int duration){
        mDuration = duration;
    }

    public void tick(){
        long sleepMillis = 0;
        long passedMillis = 0;
        long now = System.currentTimeMillis();
        if (mLastTickMillis != 0){
            passedMillis = now - mLastTickMillis;
            if (passedMillis < mDuration){
                sleepMillis = mDuration - passedMillis;
            }
        }
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e){
            // ingore
        }
        mLastTickMillis = System.currentTimeMillis();
    }
}