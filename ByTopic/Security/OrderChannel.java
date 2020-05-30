public enum OrderChannel {
// 代码	操作渠道
// 0	柜台系统
// 1	电话外线
// 2	外线停用
// 3	TDX委托
// 4	顶点委托
// 5	宏汇委托
// 6	小键盘  
// 7	自助打单
// 8	gst     
// 9	股神通  
// a	城市网  
// b	拨号远程
// c	总部通达
// d	通达信网
// e	可视委托
// f	融汇远程
// g	网页交易
// h	中国移动
// i	移动GPRS
// j	联通CDMA

    CounterSystem, // 柜台系统
    Telephone,
    Telephone2, // 外线停用（？）
    TongDaXin,
    DingDian,
    HongHui,
    NumberKeyboard, // 小键盘
    SelfOrder, // 自助打单
    GST,
    GuShenTong,
    LAN,
    RemoteDial,
    TongDaXin_HeadOffice,
    TongDaXin_Net,
    VisualOrder,
    RongHui,
    WebPage,
    ChinaMobile,
    ChinaMobile_GPRS,
    Unicom_CDMA;
}
