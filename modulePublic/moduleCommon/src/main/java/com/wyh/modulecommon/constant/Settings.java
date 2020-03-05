package com.wyh.modulecommon.constant;

import android.support.v4.util.SimpleArrayMap;

/**
 * Created by 翁益亨 on 2020/1/19.
 */
public class Settings {
    /**点买项目是否调试(给测试包的时候调试需关掉)**/
    public static boolean YZTZ_DM_IS_DEBUG = false;
    /**点买项目设备编号**/
    public static final String YZTZ_DM_DEVICE_ID = "2";
    /**公司电话**/
    public static final String YZTZ_PHONE = "4008888566";
    /**省份名称传输标记**/
    public static final String YZTZ_PROVICE_TRANSNAME = "province_name";
    /**省份号码传输标记**/
    public static final String YZTZ_PROVICE_TRANSCODE = "province_code";
    /**城市名称传输标记**/
    public static final String YZTZ_CITY_TRANSNAME = "city_name";
    /**城市号码传输标记**/
    public static final String YZTZ_CITY_TRANSCODE = "city_code";
    /**银行卡信息传输标记**/
    public static final String YZTZ_BANK_TRANSNAME = "bank_name";
    /**支付银行卡传输标记**/
    public static final String YZTZ_BANK_TRANSFLAG = "bank_info";
    /**点买类型**/
    public static final String YZTZ_DM_TRANS_TYPE = "trans_type";
    /**intent传输code**/
    public static final int YZTZ_REQUEST_CODE_ZERO = 10000;
    /**intent传输code**/
    public static final int YZTZ_REQUEST_CODE_FIRST = 10001;
    /**intent传输code**/
    public static final int YZTZ_REQUEST_CODE_SECOND = 10002;
    /**intent传输code**/
    public static final int YZTZ_REQUEST_CODE_THIRD = 10003;
    /**需要登陆的按钮点击跳转**/
    public static final int YZTZ_REQUEST_CODE_LOGIN = 10010;
    /**点买项目数据库版本号**/
    public static final int YZTZ_DM_DATABASE_VERSION = 4;
    /**点买项目数据库名称**/
    public static final String YZTZ_DM_DATABASE_NAME = "dm.db";
    /**点买项目登录广播标记**/
    public static final String YZTZ_DM_LOGIN_ACTION = "com.yztz.login.action";
    /**点买项目心跳标记**/
    public static final String YZTZ_DM_HEART_ACTION = "com.yztz.heart.action";
    /**点买项目交易时间状态变更标记**/
    public static final String YZTZ_DM_TRADE_ACTION = "com.yztz.trade.action";
    /**点买项目获取系统信息标记**/
    public static final String YZTZ_DM_INDEX_TRADE_ACTION = "com.yztz.index.trade.action";
    /**点买项目定时任务传输码开始总编号**/
    public static int YZTZ_DM_TASK_CODE_START_NUMBER = 10000000;
    /**点买项目交易定时任务传输码开始编号(用于和上面的编号相加)**/
    public static int YZTZ_DM_TRADE_TASK_CODE_START_NUMBER = 5000;
    /**标题栏是否可以点击**/
    public static final String YZTZ_DM_TITLE_CLICK_SHOW = "show_title_click_show";
    /**手机号码传输标记**/
    public static final String YZTZ_MOBILE_TRANS = "yztz_mobile";
    /**返回home(被清理后)执行的跳转**/
    public static final String YZTZ_HOME_JUMP_ACTION = "yztz_home_jump";
    /**银行卡前缀**/
    public static final String YZTZ_BANK_CODE_PREFEX = "icon_bank_";
    /**银行Icon资源**/
    public static final SimpleArrayMap<String, Integer> BANK_ARRAY = new SimpleArrayMap<>();
    /**上证指数,深证指数,创业板指数代码**/
    public static final String[] YZTZ_BASE_STOCK_CODES  = new String[] {"SH000001", "SZ399001", "SZ399006"};
    /**股票行情查询时间**/
    public static final String[] YZTZ_STOCK_QUOTE_TIME = new String[] {"09:30", "11:30", "13:00", "15:00"};
    /**股票行情查询时间段**/
    public static final String YTZT_STOCK_QUOTE_PERIOD = "09:30,11:30|13:00,15:00";
    /**本地默认查询股票代码**/
    public static String YZTZ_LOCAL_DEFAULT_STOCK = "SH600570";
    /**本地默认查询股票代码，只用于发起策略跳转页面数据**/
    public static String YZTZ_LOCAL_DEFAULT_STOCK_TD = "SH600570";
    /**股票分时图总节点数**/
    public static final long YZTZ_STOCK_SLINE_NODES_NUM = 240;
    /**日K图时间间隔**/
    public static final long YZTZ_STOCK_KLINE_PERIOD = 7776000000L;
    /**跳转动作传输标记**/
    public static final String YZTZ_ACTION_TRANS_NAME = "action_name";
    /**点卖时间段**/
    public static final String YZTZ_DM_SELL_PERIOD = "09:30,15:30";
    /**点买下载路径**/
    public static final String YZTZ_DM_DOWNDIR = "yztzDown";
    /**原油黄金分时图总节点数**/
    public static final int YZTZ_CL_GC_SLINE_NODES_NUM = 240;
    /**intent 传输参数1**/
    public static final String YZTZ_TRANS_PARAMS_FIRST = "params_one";
    /**intent 传输参数2**/
    public static final String YZTZ_TRANS_PARAMS_SECOND = "params_two";
    /**intent 传输参数3**/
    public static final String YZTZ_TRANS_PARAMS_THIRD = "params_three";
    /**intent 传输参数4**/
    public static final String YZTZ_TRANS_PARAMS_FOURTH = "params_four";
    /**intent 传输参数5**/
    public static final String YZTZ_TRANS_PARAMS_FIFTH = "params_five";
    /***WebView缓存目的***/
    public static final String YZTZ_WEBVIEW_CACHE_DIR = "yztzWebCache";
    /***支付宝转账连接***/
    public static final String ALIPAY_URL = "alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2FFKX06144M5DZVYZIGM6SCA%3F_s%3Dweb-other";
    /***支付宝二维码连接***/
    public static final String ALIPAY_CODE_DOWNLOAD = "HTTPS://QR.ALIPAY.COM/FKX06144M5DZVYZIGM6SCA";

    public static final String TOKEN_KEY = "token";
    public static final String DEVICE_KEY = "device";
    public static final String DEVICE_TYPE = "20";
    public static final String PAY_URL = "http://toqfqze.sunlin1.com/jh-web-order/order/receiveOrder";//四方支付付款地址
    public static final String PAY_PARAMS = "payParams";
}
