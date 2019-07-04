public enum OrderStatus {
// 代码	状态
// 0	未报
// 1	正报
// 2	已报
// 3	已报待撤
// 4	部成待撤
// 5	部撤
// 6	已撤
// 7	部成
// 8	已成
// 9	废单
// A	待报
// B	正报

    Unposted, // 未报
    Posting, // 正报
    Posted, // 已报
    PostedAndWithdrawing, // 已报待撤
    PartlyWithdrawing, // 部分待撤
    PartlyWithdrawn, // 部分撤销
    Withdrawn, // 已撤销
    PartlyDealed,
    Dealed,
    InvalidOrder,
    WaitToPost;
}
