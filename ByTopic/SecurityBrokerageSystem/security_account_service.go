package main

type SecurityAccountService struct{}

// 开立总账户
func (r SecurityAccountService) OpenAccount(investor Investor) {
	// 如果用户已经开立总账户，返回错误。
	// 如果用户关键信息确实，返回错误。
}

// 注销总账户
func (r SecurityAccountService) CloseAccount(account SecurityAccount) {
}

// 开立子账户
func (r SecurityAccountService) OpenLedger(investor Investor, accountType int) {
}

// 关闭子账户
func (r SecurityAccountService) CloseLedger(ledger SecurityLedger) {
}

// 更新资料
func (r SecurityAccountService) UpdateInvestorInformation(investor Investor) {
}

// 规范账户
func (r SecurityAccountService) NormalizeLedger(ledger SecurityLedger) {
}

// 解除账户限制
func (r SecurityAccountService) CancelRestriction(ledger SecurityLedger) {
}

// 解除卖出限制
func (r SecurityAccountService) CancelSellRestriction(ledger SecurityLedger) {
}

// 绑定子账户和总账户
func (r SecurityAccountService) AssociateLedger(account SecurityAccount, ledger SecurityLedger) {
}
