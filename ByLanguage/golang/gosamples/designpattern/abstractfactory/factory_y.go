package main

type ProductA_Y struct {
}

func (r *ProductA_Y) String() string {
	return "PRODUCT A FROM FACTORY Y"
}

type ProductB_Y struct {
}

func (r *ProductB_Y) String() string {
	return "PRODUCT B FROM FACTORY Y"
}

type FactoryY struct {
}

func (r *FactoryY) CreateProductA() ProductA {
	return &ProductA_Y{}
}

func (r *FactoryY) CreateProductB() ProductB {
	return &ProductB_Y{}
}
