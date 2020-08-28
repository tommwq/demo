package util

import (
	"math"
)

// 计算股票市净率。
func PBRatio(netAssetsPerShare, price float64) float64 {
	return price / netAssetsPerShare
}

// 计算每股净资产。
func NetAssetsPerShare(netAssets, shares float64) float64 {
	return netAssets / shares
}

// 计算市值
func MarketValue(stockPrice, shares float64) float64 {
	return stockPrice * shares
}

// 计算公司总市值
// marketValues是各市场市值。注意需要换算成同一货币。
func MultiMarketValue(marketValues []float64) float64 {
	sum := 0.0
	for _, v := range marketValues {
		sum = sum + v
	}
	return sum
}

// TODO 货币换算

// 计算股票涨跌价格。
// price是前收盘价。
// delta是涨跌停服务，如-0.1表示跌停价为前收盘价的90%。
// uint是最小货币变动单位，如0.01表示最小货币变动单位为1分。
func StockPriceLimit(price, delta, unit float64) float64 {
	// 取保留小数位数。
	// 使用Ceil是为了处理最小货币变动单位为0.005这类情况。
	decimal := int(math.Ceil(float64(-1) * math.Log10(unit)))
	limit := RoundToNearest(price*(1.0+delta), decimal)

	if math.Abs(limit-price) >= unit {
		return limit
	}

	switch {
	case delta > 0.0:
		limit = price + unit
	case delta < 0.0:
		limit = price - unit
	default:
		limit = price
	}

	return limit
}

// 四舍五入。
// decimal保留的小数位数
func RoundToNearest(number float64, decimal int) float64 {
	sign := 1
	if number < 0 {
		sign = -1
	}

	scaled := int64(math.Abs(number) * math.Pow10(decimal+1))
	tail := scaled % 10
	scaled = scaled / 10
	if tail > 4 {
		scaled = scaled + 1
	}
	return float64(sign) * float64(scaled) / math.Pow10(decimal)
}


