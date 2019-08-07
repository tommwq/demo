从DescriptorSet生成Service类的步骤：

1. 读取描述符集合，建立FileDescriptorSet对象。（建立描述符集合时需要增加--include_imports）
2. 建立服务表和消息表。
3. 遍历描述符集合中的文件，对每个文件：
4. 遍历文件中的消息，加入到消息表中。
5. 遍历文件中的服务，加入服务表中。
6. 为每个消息生产POJO类。
7. 为每个服务生成TransmitServer类。


