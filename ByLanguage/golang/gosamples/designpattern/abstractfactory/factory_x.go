package main

type ProductA_X struct {
}

func (r *ProductA_X) String() string {
	return "PRODUCT A FROM FACTORY X"
}

type ProductB_X struct {
}

func (r *ProductB_X) String() string {
	return "PRODUCT B FROM FACTORY X"
}

type FactoryX struct {
}

func (r *FactoryX) CreateProductA() ProductA {
	return &ProductA_X{}
}

func (r *FactoryX) CreateProductB() ProductB {
	return &ProductB_X{}
}
