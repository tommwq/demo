根据《事务处理》中对三工存储的介绍，编写的存储软件。

启动时，首先分配目录和空间，软件建立一个大的二进制文件，将内部划分为3个简单存储区。

初始化存储区。用户输入总空间大小、元数据空间大小和页面大小，程序建立3个数据文件，分别命名为replica1、replica2和replica3。
写入页。用户输入页号码，页内偏移量和数据，程序将数据写入指定的页上。页固定为4096大小。
检查和修复。用户启动检查和修复程序，程序逐个页执行读取和修复工作。

程序以库的方式提供，提供的接口有：
struct TriplexStorage;
// 建立或打开存储空间
struct TriplexStorage* open_triplex_storage(const char* directory, uint32 replica_storage_size);
// 写页
int write_data(struct TripleStorage* storage, uint32 page_number, uint32 offset, uint32 length, const char* data);
// 读页
int read_data(struct TripleStorage* storage, uint32 page_number, uint32 offset, uint32 length, char* buffer);
// 关闭存储空间
void close_triple_storage(struct TriplexStorage* storage);

