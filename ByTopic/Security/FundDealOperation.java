public enum FundDealOperation {
// 代码	交易名称
// 240020	认购申请
// 240021	预约认购
// 240022	申购申请
// 240023	预约申购
// 240024	赎回申请
// 240025	预约赎回
// 240026	转托管
// 240027	转托管入
// 240028	转托管出
// 240029	分红方式设置
// 240031	基金份额冻结
// 240032	基金份额解冻
// 240033	非交易过户
// 240036	基金转换
// 240037	基金转换入

    Subscribe,
    AppointSubscription,
    Buy,
    AppointBuy,
    Redeem,
    AppointRedeem,
    DesignationTransfer,
    TransferIn,
    TransferOut,
    AssignBonusPattern,
    FreezeFundShare,
    ResumeFundShare,
    NonDealTransfer,
    FundConvert,
    FundConvertIn;
}
