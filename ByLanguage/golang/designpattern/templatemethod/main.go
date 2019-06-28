package main

type DataSourceProvider interface {
	Connect()
	Select()
	Process()
	Disconnect()
}

type DataSource struct {
	provider DataSourceProvider
}

func NewDataSource(provider DataSourceProvider) *DataSource {
	return &DataSource{
		provider: provider,
	}
}

func (r *DataSource) Run() {
	if r.provider == nil {
		return
	}

	r.provider.Connect()
	r.provider.Select()
	r.provider.Process()
	r.provider.Disconnect()
}

type MockDataSourceProvider struct{}

func (r *MockDataSourceProvider) Connect() {
	println("CONNECT")
}

func (r *MockDataSourceProvider) Select() {
	println("SELECT")
}

func (r *MockDataSourceProvider) Process() {
	println("PROCESS")
}

func (r *MockDataSourceProvider) Disconnect() {
	println("DISCONNECT")
}

func main() {
	dataSource := NewDataSource(&MockDataSourceProvider{})
	dataSource.Run()
}
